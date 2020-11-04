package com.pinduo.auto.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import cn.vove7.andro_accessibility_api.AccessibilityApi
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.Utils
import com.pinduo.auto.app.manager.AppLifeCycleCallBack
import com.pinduo.auto.app.manager.ForebackLifeObserver
import com.pinduo.auto.receiver.USBBroadcastReceiver
import com.pinduo.auto.service.MyAccessibilityService
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.UiHandler

class MyApplication : Application() {

    private lateinit var uiHandler: UiHandler
    private lateinit var jobManager: JobManager

    private var imei:String = ""

    private val usbBroadcastReceiver: USBBroadcastReceiver by lazy {
        USBBroadcastReceiver()
    }


    companion object {
        @JvmStatic
        lateinit var instance: MyApplication
            private set
    }

    fun getUiHandler(): UiHandler {
        return uiHandler
    }

    fun getJobManager(): JobManager {
        return jobManager
    }

    fun getIMEI():String{
        return imei
    }
    fun setIMEI(i:String){
        this.imei = i
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        this.uiHandler = UiHandler()
        configureJobManager()
        initConfig()
    }


    private fun configureJobManager() {
        jobManager = JobManager(Configuration.Builder(this).build())
    }

    private fun initConfig() {
        Utils.init(this)
        SPUtils.getInstance(AppUtils.getAppPackageName())
        registerActivityLifecycleCallbacks(AppLifeCycleCallBack())
        ProcessLifecycleOwner.get().lifecycle.addObserver(ForebackLifeObserver())
        AccessibilityApi.apply {
            BASE_SERVICE_CLS = MyAccessibilityService::class.java // 基础无障碍
            GESTURE_SERVICE_CLS = MyAccessibilityService::class.java // 高级无障碍
        }
        USBBroadcastReceiver.registerReceiver(this, usbBroadcastReceiver)
    }


    override fun onTerminate() {
        super.onTerminate()
        USBBroadcastReceiver.unregisterReceiver(this, usbBroadcastReceiver)
        LogUtils.logGGQ("app onTerminate")
    }

}
package com.pinduo.auto.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import cn.vove7.andro_accessibility_api.AccessibilityApi
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.lzy.okgo.OkGo
import com.pinduo.auto.app.manager.AppLifeCycleCallBack
import com.pinduo.auto.app.manager.ForebackLifeObserver
import com.pinduo.auto.receiver.USBBroadcastReceiver
import com.pinduo.auto.service.MyAccessibilityService
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.ToastUtil
import com.pinduo.auto.utils.UiHandler
import com.pinduo.auto.widget.update.OKHttpUpdateHttpService
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate.entity.UpdateError.ERROR.CHECK_NO_NEW_VERSION
import com.xuexiang.xupdate.utils.UpdateUtils


class MyApplication : Application() {

    //Main handler
    private lateinit var uiHandler: UiHandler
    // 工作队列管理器
    private lateinit var jobManager: JobManager

    private var imei:String = ""

    //屏幕锁屏唤醒 电量状态 广播
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
        OkGo.getInstance().init(this)
        registerActivityLifecycleCallbacks(AppLifeCycleCallBack())
        ProcessLifecycleOwner.get().lifecycle.addObserver(ForebackLifeObserver())
        AccessibilityApi.apply {
            BASE_SERVICE_CLS = MyAccessibilityService::class.java // 基础无障碍
            GESTURE_SERVICE_CLS = MyAccessibilityService::class.java // 高级无障碍
        }
        USBBroadcastReceiver.registerReceiver(this, usbBroadcastReceiver)


        XUpdate.get()
            .debug(true)
            .isWifiOnly(false) //默认设置只在wifi下检查版本更新
            .isGet(true) //默认设置使用get请求检查版本
            .isAutoMode(false) //默认设置非自动模式，可根据具体使用配置
//            .param("versionCode", UpdateUtils.getVersionCode(this)) //设置默认公共请求参数
//            .param("appKey", packageName)
            .setOnUpdateFailureListener { error ->
                //设置版本更新出错的监听
                if (error.code != CHECK_NO_NEW_VERSION) {          //对不同错误进行处理
                    ToastUtil.showToast(error.toString())
                }
            }
            .supportSilentInstall(true) //设置是否支持静默安装，默认是true
            .setIUpdateHttpService(OKHttpUpdateHttpService()) //这个必须设置！实现网络请求功能。
            .init(this)
    }


    override fun onTerminate() {
        super.onTerminate()
        USBBroadcastReceiver.unregisterReceiver(this, usbBroadcastReceiver)
        LogUtils.logGGQ("app onTerminate")
    }

}
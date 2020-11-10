package com.pinduo.auto.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.AppUtils
import com.lzy.okgo.OkGo
import com.pinduo.auto.R
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.app.global.EventCode
import com.pinduo.auto.base.BaseActivity
import com.pinduo.auto.utils.AccessibilityServiceUtils
import com.pinduo.auto.utils.Event
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.ToastUtil
import com.pinduo.auto.widget.download.DownLoadUtils
import com.yhao.floatwindow.FloatWindow
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : BaseActivity() {

    private val REQUESTCODE_ACCESSIBILITY: Int = 1001

    override fun getLayoutId(): Int = R.layout.activity_home

    override fun initData(savedInstanceState: Bundle?) {
        val douyinVer = AppUtils.getAppVersionName(Constants.GlobalValue.PACKAGE_DOUYIN)

        if(TextUtils.equals(Constants.Douyin.V1220,douyinVer) || TextUtils.equals(Constants.Douyin.V1270,douyinVer)){
            sw_float_window.isChecked = true
            sw_float_window.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    FloatWindow.get()?.let {
                        if (!it.isShowing) it.show()
                    }
                } else {
                    FloatWindow.get()?.let {
                        if (it.isShowing) it.hide()
                    }
                }
            }

            tv_douyin.setText("抖音版本:${douyinVer}")
//        checkVersion()
            checkAccessibilityPermission()
        }else{
            sw_float_window.visibility = View.GONE
            tv_douyin.setText("抖音版本：${douyinVer}")
            tv_douyin.textSize = 36f

            ToastUtil.showTipToast("抖音版本：${douyinVer}")
        }

        tv_apppver.setText("当前版本:${AppUtils.getAppVersionName()}")
        btn_update.setOnClickListener {
            downLoadAPK()
        }
    }

    override fun hasUsedEventBus(): Boolean = true
    
    private fun downLoadAPK() {
        val url:String = "http://cc.pinduocm.com/apkDownload"
        DownLoadUtils().downLoadAndInstallAPK(HomeActivity@this,url)
    }

    private fun checkVersion() {
        DownLoadUtils().checkVersion()
    }

    private fun checkAccessibilityPermission() {

        if (AccessibilityServiceUtils.isAccessibilitySettingsOn(MyApplication.instance)) {
            LogUtils.logGGQ("无障碍已开启")
            FloatWindow.get()?.let {
                if (!it.isShowing) it.show()
            }
        } else {
            LogUtils.logGGQ("无障碍未开启")
            startActivityForResult(
                Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS),
                REQUESTCODE_ACCESSIBILITY
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LogUtils.logGGQ("requestCode:${requestCode} -- resultCode:${resultCode}")
        if (requestCode == REQUESTCODE_ACCESSIBILITY) {
            checkAccessibilityPermission()
        }
    }

    override fun <T> onEventBusDispense(event: Event<T>) {
        super.onEventBusDispense(event)
        when(event.code){
            EventCode.EVENT_IPORTE ->{
                tv_iport?.setText(event.data.toString())
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.moveTaskToBack(true)
        }
        return super.onKeyDown(keyCode, event)
    }


    override fun onDestroy() {
        super.onDestroy()
        OkGo.getInstance().cancelAll()
    }
}
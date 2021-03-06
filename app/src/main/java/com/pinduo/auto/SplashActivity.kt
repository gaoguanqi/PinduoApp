package com.pinduo.auto

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.blankj.utilcode.util.FileIOUtils
import com.blankj.utilcode.util.ToastUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.base.BaseActivity
import com.pinduo.auto.ui.AccountActivity
import com.pinduo.auto.ui.HomeActivity
import com.pinduo.auto.ui.TestActivity
import com.pinduo.auto.utils.PermissionUtil
import com.pinduo.auto.utils.RequestPermission
import com.tbruyelle.rxpermissions2.RxPermissions

class SplashActivity : BaseActivity(R.layout.activity_splash) {

    val rxPermissions: RxPermissions = RxPermissions(this)


    override fun initData(savedInstanceState: Bundle?) {
        applyPermissions()
    }


    override fun onRestart() {
        super.onRestart()
        applyPermissions()
    }

    private fun applyPermissions() {
        PermissionUtil.applyPermissions(object : RequestPermission {
            override fun onRequestPermissionSuccess() {
                launchTarget()
//                launchTest()
            }

            override fun onRequestPermissionFailure(permissions: List<String>) {
                ToastUtils.showShort("权限未通过")
            }

            override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                ToastUtils.showShort("权限未通过")
            }
        }, rxPermissions)
    }

    private fun launchTarget() {
        val imei: String? = FileIOUtils.readFile2String(Constants.Path.IMEI_PATH)
        if(TextUtils.isEmpty(imei)){
            startActivity(Intent(SplashActivity@ this, AccountActivity::class.java))
            this.finish()
        }else{
            MyApplication.instance.setIMEI(imei!!)
            startActivity(Intent(SplashActivity@ this, HomeActivity::class.java))
            finish()
        }
    }

    private fun launchTest(){
        startActivity(Intent(SplashActivity@ this, TestActivity::class.java))
        this.finish()
    }
}
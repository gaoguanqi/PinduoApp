package com.pinduo.auto.ui

import android.os.Bundle
import com.blankj.utilcode.util.AppUtils
import com.pinduo.auto.R
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.base.BaseActivity
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.widget.download.DownLoadListener
import com.pinduo.auto.widget.download.DownLoadUtils
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity:BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_test

    override fun initData(savedInstanceState: Bundle?) {
        btn_test.setOnClickListener {
            onTestDownLoad()
        }
    }

    private val url:String = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"
    private fun onTestDownLoad() {
        val downLoadUtils = DownLoadUtils()
        downLoadUtils.downLoadAndInstallAPK(MyApplication.instance.applicationContext,url,object :DownLoadListener{
            override fun onSuccess(path: String) {
                LogUtils.logGGQ("完整路径：${path}")
                AppUtils.installApp(path)
            }
        })

    }
}
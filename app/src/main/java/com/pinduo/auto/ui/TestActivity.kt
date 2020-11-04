package com.pinduo.auto.ui

import android.os.Bundle
import com.pinduo.auto.R
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.base.BaseActivity
import com.pinduo.auto.widget.download.DownLoadUtils
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_test

    override fun initData(savedInstanceState: Bundle?) {
        btn_test.setOnClickListener {
            onTestDownLoad()
        }
    }

    private val url: String = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"
    private fun onTestDownLoad() {
        val downLoadUtils = DownLoadUtils()
        downLoadUtils.downLoadAndInstallAPK(MyApplication.instance.applicationContext, url)

    }
}
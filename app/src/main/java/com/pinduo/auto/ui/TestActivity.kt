package com.pinduo.auto.ui

import android.os.Bundle
import com.pinduo.auto.R
import com.pinduo.auto.base.BaseActivity
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_test

    override fun initData(savedInstanceState: Bundle?) {
        btn_test.setOnClickListener {
            xUpdate()
        }
    }

    private val url: String = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk"

    private fun xUpdate() {

    }

}
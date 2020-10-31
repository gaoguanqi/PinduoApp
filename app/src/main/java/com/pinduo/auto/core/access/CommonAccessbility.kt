package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import cn.vove7.andro_accessibility_api.api.withText
import com.pinduo.auto.app.MyApplication

class CommonAccessbility private constructor() : BaseAccessbility<CommonAccessbility>() {

    companion object {
        val INSTANCE: CommonAccessbility by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CommonAccessbility()
        }
    }


    override fun initService(service: AccessibilityService): CommonAccessbility {
        return super.initService(service)
    }

    fun douyin2Main() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("snssdk1128://feed?refer=web"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        MyApplication.instance.startActivity(intent)
        withText("推荐")?.globalClick()
    }


}
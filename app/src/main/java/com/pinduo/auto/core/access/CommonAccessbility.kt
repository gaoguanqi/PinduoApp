package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import cn.vove7.andro_accessibility_api.AppScope
import cn.vove7.andro_accessibility_api.api.waitForPage
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.utils.NodeUtils

class CommonAccessbility private constructor() : BaseAccessbility<CommonAccessbility>(){

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
    }

    fun douyin2MainWithText(s:String){
        douyin2Main()
        if(waitForPage(AppScope(Constants.GlobalValue.PACKAGE_DOUYIN,Constants.Douyin.PAGE_MAIN),3000L)){
            NodeUtils.tryWithText(s)
        }else{
            NodeUtils.tryWithText(s)
        }
    }


    fun ignorePage(className:String) {
        when(className){
            Constants.Douyin.PAGE_UPDATE_X,Constants.Douyin.PAGE_UPDATE_Y ->{
                NodeUtils.tryWithText("以后再说")
            }
            Constants.Douyin.PAGE_YANG ->{
                NodeUtils.tryWithText("我知道了")
            }
            Constants.Douyin.PAGE_DN ->{
                NodeUtils.tryWithText("取消")
            }
            Constants.Douyin.PAGE_DB,
            Constants.Douyin.PAGE_USERPROFIL ->{
                NodeUtils.tryWithText("好的")
            }
            Constants.Douyin.PAGE_CX ->{
                NodeUtils.tryWithText("同意")
            }
            Constants.Douyin.PAGE_RECOMMENDCONTACT ->{
                NodeUtils.tryWithText("稍后")
            }
            Constants.Douyin.PAGE_FLOATVIEW_D ->{
                NodeUtils.tryWithText("暂不使用")
            }
        }
    }

    // 6秒内等待抖音 首页 出现
    fun waitForMainPage(listener:MainPageListener){
        if(waitForPage(AppScope(Constants.GlobalValue.PACKAGE_DOUYIN,Constants.Douyin.PAGE_MAIN),6000L)){
            listener.call(true)
        }else{
            listener.call(false)
        }
    }




    interface MainPageListener{
        fun call(b:Boolean)
    }

}
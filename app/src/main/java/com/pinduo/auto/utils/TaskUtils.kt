package com.pinduo.auto.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.KEYGUARD_SERVICE
import android.os.PowerManager
import android.text.TextUtils
import com.blankj.utilcode.util.AppUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import org.w3c.dom.Text
import kotlin.random.Random


class TaskUtils{
    companion object{
        ///截取评论内容
        fun handContent(content:String):String{
            if(TextUtils.isEmpty(content)) return "~"
            if(content.contains(";")){
                content.split(";").let {
                    return it[Random.nextInt(it.size)]
                }
            }
            return content
        }

        fun randomTime(min:String,max:String):Long{
            if(TextUtils.isEmpty(min) || TextUtils.isEmpty(max) || TextUtils.equals(min,"0") || TextUtils.equals(max,"0")){
                return 1000L
            }
            val time:Int = Random.nextInt(min.toInt(),max.toInt())
            if(time <= 0){
                return 1000L
            }
            return time *1000L
        }

        fun isDouyin1270():Boolean{
            return TextUtils.equals("12.7.0",AppUtils.getAppVersionName(Constants.GlobalValue.PACKAGE_DOUYIN))
        }


        @SuppressLint("InvalidWakeLockTag")
        fun wakeUpAndUnlock(){
            // 获取电源管理器对象
            val pm:PowerManager? = MyApplication.instance.getSystemService(Context.POWER_SERVICE) as PowerManager
            pm?.let {
                if(!it.isInteractive){
                    val wl: PowerManager.WakeLock = it.newWakeLock((PowerManager.ACQUIRE_CAUSES_WAKEUP or PowerManager.SCREEN_BRIGHT_WAKE_LOCK),"bright")
                    wl.acquire(10*60*1000L /*10 minutes*/)
                    wl.release()
                    MyApplication.instance.getUiHandler().sendMessage("屏幕点亮")
                }
            }
        }
    }
}
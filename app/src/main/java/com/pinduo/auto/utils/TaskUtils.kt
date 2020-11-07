package com.pinduo.auto.utils

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.KEYGUARD_SERVICE
import android.os.PowerManager
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.scrollUp
import com.blankj.utilcode.util.AppUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import java.lang.Exception
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

        fun getContentList(content: String):ArrayList<String>{
            val array = arrayListOf<String>()
            if(content.contains(";")){
                content.split(";").let {
                    array.addAll(it)
                }
            }else{
                array.add(content)
            }
            return array
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
            return TextUtils.equals(Constants.Douyin.V1270,AppUtils.getAppVersionName(Constants.GlobalValue.PACKAGE_DOUYIN))
        }

        fun isDouyin1220():Boolean{
            return TextUtils.equals(Constants.Douyin.V1220,AppUtils.getAppVersionName(Constants.GlobalValue.PACKAGE_DOUYIN))
        }



        @SuppressLint("InvalidWakeLockTag")
        fun wakeUpAndUnlock(){
            try {
                // 获取电源管理器对象
                val pm:PowerManager? = MyApplication.instance.getSystemService(Context.POWER_SERVICE) as PowerManager
                pm?.let {
                    if(!it.isInteractive){
                        val wl: PowerManager.WakeLock = it.newWakeLock(
                            PowerManager.FULL_WAKE_LOCK
                                    or PowerManager.ACQUIRE_CAUSES_WAKEUP
                                    or PowerManager.ON_AFTER_RELEASE, "bright")
                        wl.acquire(1000L * 600)
                        wl.release()
                        MyApplication.instance.getUiHandler().sendMessage("点亮屏幕")
                    }
                }

                val km:KeyguardManager? = MyApplication.instance.getSystemService(KEYGUARD_SERVICE) as KeyguardManager
                val kLock = km?.newKeyguardLock("unLock")
                kLock?.let {
                    it.disableKeyguard()
                }
                upScreen()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun upScreen(){
            scrollUp()
        }
    }



}
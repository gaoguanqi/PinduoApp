package com.pinduo.auto.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.KeyguardManager
import android.content.Context
import android.content.Context.KEYGUARD_SERVICE
import android.content.Intent
import android.os.PowerManager
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.scrollUp
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.ui.HomeActivity
import java.lang.Exception
import kotlin.random.Random


class TaskUtils{

    companion object{
        private var loopIndex:Int = 0
        private var arrays:ArrayList<String> = arrayListOf()

        ///截取评论内容
        fun getContentRandom(content:String):String{
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

        fun initContent(content: String){
            arrays.clear()
            arrays.addAll(getContentList(content))
        }

        fun getContentIndex():String{
            if(arrays.isEmpty()){
                return "~"
            }
            loopIndex++
            if(loopIndex >= arrays.size){
                loopIndex = 0
            }
            return arrays[loopIndex]
        }


        fun getLoopCount():Int = loopIndex

        fun randomTime(min:String,max:String):Long{
            if(TextUtils.isEmpty(min) || TextUtils.isEmpty(max) || TextUtils.equals(min,"0") || TextUtils.equals(max,"0")){
                return 1000L
            }
            val tmin:Int = min.toInt()
            val tmax:Int = max.toInt()
            if(tmin >= tmax){
                return tmin *1000L
            }
            val time:Long = Random.nextLong(tmin*1000L,tmax*1000L)
            return time
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
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        fun upScreen(){
            scrollUp()
        }

        fun openHomeAndKill(packageName:String){
//            ActivityUtils.startHomeActivity()

            val homeActivity:Intent = Intent(MyApplication.instance.applicationContext,HomeActivity::class.java)
            homeActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            MyApplication.instance.applicationContext.startActivity(homeActivity)

            val am = MyApplication.instance.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            am.killBackgroundProcesses(packageName)
        }
    }






}
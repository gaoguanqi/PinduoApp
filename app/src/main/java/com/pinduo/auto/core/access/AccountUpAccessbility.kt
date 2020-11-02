package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.withId
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.core.ids.DouyinIds
import com.pinduo.auto.utils.NodeUtils
import com.pinduo.auto.utils.TaskUtils
import com.pinduo.auto.utils.WaitUtil

// 养号
class AccountUpAccessbility private constructor() :BaseAccessbility<AccountUpAccessbility>(){

    private var isSwiped:Boolean = false

    companion object {
        val INSTANCE: AccountUpAccessbility by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AccountUpAccessbility()
        }
    }

    override fun initService(service: AccessibilityService): AccountUpAccessbility {
        return super.initService(service)
    }

    fun getSwiped():Boolean = isSwiped
    fun setSwiped(b:Boolean){
        isSwiped = b
    }


    fun doSwipe(min:String,max:String){
        try {
            setSwiped(true)
            do {
                val m:Long = TaskUtils.randomTime(min,max)
                WaitUtil.sleep(m)
                MyApplication.instance.getUiHandler().sendMessage("浏览${(m/1000L).toInt()}秒")
                NodeUtils.onSwipe(service)
            }while (getSwiped())
        }catch (e:Exception){
            setSwiped(false)
        }

    }
}
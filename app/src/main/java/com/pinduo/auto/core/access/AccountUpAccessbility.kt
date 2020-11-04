package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.withId
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.core.ids.DouyinIds
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.NodeUtils
import com.pinduo.auto.utils.TaskUtils
import com.pinduo.auto.utils.WaitUtil
import java.lang.Exception

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


    private val mtxt:String = "的"
    fun doSwipe(min:String,max:String){
        setSwiped(true)
        do {
            val m:Long = TaskUtils.randomTime(min,max)
            WaitUtil.sleep(m)
            MyApplication.instance.getUiHandler().sendMessage("浏览${(m/1000L).toInt()}秒")
            NodeUtils.onSwipe(service)
            try {
                withId(DouyinIds.geta91())?.let {
                    val txt:String? = it.text?.toString()
                    if(!TextUtils.isEmpty(txt)){
                        MyApplication.instance.getUiHandler().sendMessage(txt!!)
                        if(txt.contains(mtxt)){
                            MyApplication.instance.getUiHandler().sendMessage("包含文本${mtxt}")
                            withId(DouyinIds.getayl())?.let { it1 ->
                                val likeTxt: String? = it1.text?.toString()
                                if (!TextUtils.isEmpty(likeTxt) && likeTxt!!.contains("未选中")) {
                                    it1.globalClick()
                                }
                            }
                        }else{
                            MyApplication.instance.getUiHandler().sendMessage("---不包含文本--")
                        }
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("未找到文本节点")
            }

        }while (getSwiped())

    }
}
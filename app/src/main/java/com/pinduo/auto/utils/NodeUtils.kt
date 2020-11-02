package com.pinduo.auto.utils

import android.text.TextUtils
import android.view.accessibility.AccessibilityNodeInfo
import cn.vove7.andro_accessibility_api.api.click
import cn.vove7.andro_accessibility_api.api.pressWithTime
import cn.vove7.andro_accessibility_api.api.withText
import com.pinduo.auto.app.MyApplication

class NodeUtils {
    companion object{

        fun onClickByNode(node:AccessibilityNodeInfo?):Boolean{
            node?.let {
                if(it.isClickable){
                    return it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                }else{
                    val parent = it.parent
                    val b = onClickByNode(parent)
                    parent?.recycle()
                    return b
                }
            }
            return false
        }


        fun onClickTextByNode(node:AccessibilityNodeInfo?){
            node?.let {
                for (i in 0 until it.childCount) {
                    if (TextUtils.equals(
                            "android.widget.TextView",
                            it.getChild(i).className
                        ) && !TextUtils.isEmpty(it.getChild(i).text)
                    ){
                        onClickByNode(it.getChild(i))
                    }else{
                        onClickTextByNode(it.getChild(i))
                    }
                }
            }
        }


         fun tryWithText(s:String,m:Long = 0L){
            try {
               val isClick:Boolean =  if(m > 0) withText(s)?.await(m)?.globalClick()?:false else withText(s)?.globalClick()
                MyApplication.instance.getUiHandler().sendMessage("点击-->>>${isClick}")
            }catch (e:Exception) {
                e.printStackTrace()
            }
        }
    }
}
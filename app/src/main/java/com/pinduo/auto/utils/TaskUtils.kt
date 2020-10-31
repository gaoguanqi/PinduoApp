package com.pinduo.auto.utils

import android.content.Context.INPUT_METHOD_SERVICE
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.blankj.utilcode.util.AppUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
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


        fun isDouyin1270():Boolean{
            return TextUtils.equals("12.7.0",AppUtils.getAppVersionName(Constants.GlobalValue.PACKAGE_DOUYIN))
        }
    }
}
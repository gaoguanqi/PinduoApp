package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.os.SystemClock
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.click
import cn.vove7.andro_accessibility_api.api.scrollUp
import cn.vove7.andro_accessibility_api.api.withId
import cn.vove7.andro_accessibility_api.api.withText
import com.blankj.utilcode.util.ScreenUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.core.ids.DouyinIds
import com.pinduo.auto.db.AppDatabase
import com.pinduo.auto.utils.*

// 养号
class AccountUpAccessbility private constructor() : BaseAccessbility<AccountUpAccessbility>() {

    private var isSwiped: Boolean = false
    private lateinit var userconfig: String
    private val listContains = arrayListOf<String>("服装","衣服","穿搭","上衣","裤子","的")

    companion object {
        val INSTANCE: AccountUpAccessbility by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AccountUpAccessbility()
        }
    }



    init {
        AppDatabase.getInstance(MyApplication.instance).infoDao()
            .getCurrentInfo()?.userconfig?.let {
            userconfig = it
            // todo 账号属性匹配字段 登录时记录到数据库，目前先写死

        }
        userconfig = "的"
//        userconfig = "服装,衣服,穿搭,上衣,裤子"

//        listContains.add("服装")
    }

    override fun initService(service: AccessibilityService): AccountUpAccessbility {
        return super.initService(service)
    }


    fun getSwiped(): Boolean = isSwiped
    fun setSwiped(b: Boolean) {
        isSwiped = b
    }


    private var isClose:Boolean = true
    private val x = ScreenUtils.getScreenWidth() - 50
    private val y = 10

    fun doSwipe(minTime:String,maxTime:String){
        do {
            isClose = true
            userconfig = ""
            try {
                WaitUtil.sleep(1000L)
                withId(DouyinIds.geta91())?.finder?.find()?.let {
                    if(it.isNotEmpty()){
                        val txt: String? = it.last()?.text?.toString()
                        if (!TextUtils.isEmpty(txt)) {
                            MyApplication.instance.getUiHandler().sendMessage(txt!!)
                            WaitUtil.sleep(2000L)
                            listContains.forEach {t ->
                                if(txt.contains(t)){
                                    userconfig = t
                                }
                            }
                            if (!TextUtils.isEmpty(userconfig)) {
                                MyApplication.instance.getUiHandler().sendMessage("--包含文本-->>${userconfig}")
                                withId(DouyinIds.getayl())?.finder?.find()?.last()?.let {it1 ->
                                    val desc:String? = it1.desc()
                                    if(!TextUtils.isEmpty(desc) && desc!!.contains("未选中")){
                                        val isClick:Boolean = it1.globalClick()
                                        if(isClick)MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")
                                        WaitUtil.sleep(2000L)
                                        withId(DouyinIds.getahl())?.globalClick()?.let { it2 ->
                                            if(it2){
                                                WaitUtil.sleep(2000L)
                                                withId(DouyinIds.getahq())?.trySetText(txt)?.let { it3 ->
                                                    if(it3){
                                                        val isSend:Boolean = withId(DouyinIds.getai_())?.globalClick()
                                                        if(isSend){
                                                            MyApplication.instance.getUiHandler().sendMessage("评论成功->>${txt}")
                                                        }
                                                        WaitUtil.sleep(2000L)
                                                        isClose = withId(DouyinIds.getl4())?.globalClick()
                                                    }
                                                }
                                                isClose = withId(DouyinIds.getl4())?.click()
                                            }
                                        }
                                    }
                                }
                            } else {
                                MyApplication.instance.getUiHandler().sendMessage("---不包含文本-->>${userconfig}")
                            }
                        }
                    }else{
                        MyApplication.instance.getUiHandler().sendMessage("文本节点->>empty")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("未找到文本节点")
                LogUtils.logGGQ("任务1异常：${e.message}")

            }finally {
                //容错处理 如果未点击关闭按钮,双击右上角
                if(!isClose){
                    click(x,y)
                    click(x,y)
                    MyApplication.instance.getUiHandler().sendMessage("任务1容错双击右上角")
                }
            }
            val delayTime:Long = TaskUtils.randomTime(minTime,maxTime)
            MyApplication.instance.getUiHandler().sendMessage("等待${delayTime/1000L}秒")
            WaitUtil.sleep(delayTime)
            NodeUtils.onSwipe(service)
        }while (getSwiped())
    }



    fun doSwipe10(minTime:String,maxTime:String){
        do {
            try {
                val delayTime:Long = TaskUtils.randomTime(minTime,maxTime)
                MyApplication.instance.getUiHandler().sendMessage("等待${delayTime/1000L}秒")
                WaitUtil.sleep(delayTime)
                NodeUtils.onSwipe(service)
            }catch (e:Exception){
                e.fillInStackTrace()
            }finally {

            }
        }while (getSwiped())
    }





    fun doSwipe2(minTime:String,maxTime:String){
        do {
           try {
               withId(DouyinIds.geta91())?.finder?.find()?.let {it1 ->
                   if(it1.isNotEmpty()){
                       val txt: String? = it1.last()?.text?.toString()
                       if (!TextUtils.isEmpty(txt)) {
                           MyApplication.instance.getUiHandler().sendMessage(txt!!)
                           listContains.forEach {t ->
                               if(txt.contains(t)){
                                   userconfig = t
                               }
                           }

                           if (!TextUtils.isEmpty(userconfig)) {
                               MyApplication.instance.getUiHandler().sendMessage("--包含文本-->>${userconfig}")
                               withId(DouyinIds.getayl())?.finder?.find()?.last()?.let {it2 ->
                                   val desc:String? = it2.desc()
                                   if(!TextUtils.isEmpty(desc) && desc!!.contains("未选中")) {
                                       val isClick:Boolean = it2.globalClick()
                                       if(isClick){
                                           MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")
                                           withId(DouyinIds.getahl())?.globalClick()?.let {it3 ->
                                               if(it3){
                                                   withId(DouyinIds.getahq())?.trySetText(txt)?.let {it4 ->
                                                       val isSend:Boolean = withId(DouyinIds.getai_())?.globalClick()
                                                       if(isSend){
                                                           MyApplication.instance.getUiHandler().sendMessage("评论成功->>${txt}")
                                                       }
                                                       withId(DouyinIds.getl4())?.globalClick()
                                                   }
                                               }else{
                                                   withId(DouyinIds.getl4())?.click()
                                               }
                                           }
                                       }
                                   }
                               }
                           }
                       }
                   }
               }
           }catch (e:Exception){
               e.printStackTrace()
               MyApplication.instance.getUiHandler().sendMessage("未找到文本节点")
               LogUtils.logGGQ("任务1异常：${e.message}")
               withId(DouyinIds.getl4())?.click()
           }finally {

           }
            val delayTime:Long = TaskUtils.randomTime(minTime,maxTime)
            MyApplication.instance.getUiHandler().sendMessage("等待${delayTime/1000L}秒")
            WaitUtil.sleep(delayTime)
            NodeUtils.onSwipe(service)
        }while (getSwiped())
    }




    fun doSwipe3(minTime:String,maxTime:String){
            try {
                val delayTime :Long = TaskUtils.randomTime(minTime,maxTime)
                WaitUtil.sleep(delayTime)
                NodeUtils.onSwipe(service)

                withId(DouyinIds.geta91())?.finder?.find()?.let {it1 ->
                    if(it1.isNotEmpty()) {
                        val txt: String? = it1.last()?.text?.toString()
                        if(!TextUtils.isEmpty(txt)) {
                            MyApplication.instance.getUiHandler().sendMessage(txt!!)
                            listContains.forEach {t ->
                                if(txt.contains(t)){
                                    userconfig = t
                                }
                            }

                            if (!TextUtils.isEmpty(userconfig)) {
                                withId(DouyinIds.getayl())?.finder?.find()?.last()?.let {it2 ->
                                    val desc:String? = it2.desc()
                                    if(!TextUtils.isEmpty(desc) && desc!!.contains("未选中")) {
                                        val isClick:Boolean = it2.globalClick()
                                        if(isClick){
                                            MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")

                                            withId("com.ss.android.ugc.aweme:id/adv")?.globalClick()?.let {
                                                if (it) {
                                                    MyApplication.instance.getUiHandler().sendMessage("点击ADV")
                                                } else {
                                                    MyApplication.instance.getUiHandler().sendMessage("未点击ADV")
                                                }
                                            }


                                            withText("留下你的精彩评论吧")?.globalClick()?.let {
                                                if (it) {
                                                    MyApplication.instance.getUiHandler().sendMessage("点击-留下你的精彩评论吧-")
                                                    withId("com.ss.android.ugc.aweme:id/adq")?.trySetText(txt)
                                                        ?.let {
                                                            if (it) {
                                                                LogUtils.logGGQ("评论success")
                                                            } else {
                                                                LogUtils.logGGQ("评论error")
                                                            }
                                                        }
                                                    withId("com.ss.android.ugc.aweme:id/ae9")?.globalClick()?.let {
                                                        if (it) {
                                                            LogUtils.logGGQ("ae9:success")
                                                        } else {
                                                            LogUtils.logGGQ("ae9error")
                                                        }
                                                    }
                                                    withId("com.ss.android.ugc.aweme:id/ks")?.globalClick()?.let {
                                                        if (it) {
                                                            LogUtils.logGGQ("ks:success")
                                                        } else {
                                                            LogUtils.logGGQ("ks:error")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }


                            }
                        }
                    }
                }
            }catch (e:Exception){
                e.printStackTrace()

            }finally {

            }
    }


    fun doSwipe4(){
        try {
            withId(DouyinIds.geta91())?.finder?.find()?.let {it1 ->
                if(it1.isNotEmpty()){
                    val txt: String? = it1.last()?.text?.toString()
                    if (!TextUtils.isEmpty(txt)) {
                        MyApplication.instance.getUiHandler().sendMessage(txt!!)
                        listContains.forEach {t ->
                            if(txt.contains(t)){
                                userconfig = t
                            }
                        }

                        if (!TextUtils.isEmpty(userconfig)) {
                            MyApplication.instance.getUiHandler().sendMessage("--包含文本-->>${userconfig}")
                            withId(DouyinIds.getayl())?.finder?.find()?.last()?.let {it2 ->
                                val desc:String? = it2.desc()
                                if(!TextUtils.isEmpty(desc) && desc!!.contains("未选中")) {
                                    val isClick:Boolean = it2.globalClick()
                                    if(isClick){
                                        MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")
                                        withId(DouyinIds.getahl())?.globalClick()?.let {it3 ->
                                            if(it3){
                                                withId(DouyinIds.getahq())?.trySetText(txt)?.let {it4 ->
                                                    val isSend:Boolean = withId(DouyinIds.getai_())?.globalClick()
                                                    if(isSend){
                                                        MyApplication.instance.getUiHandler().sendMessage("评论成功->>${txt}")
                                                    }
                                                    withId(DouyinIds.getl4())?.globalClick()
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("未找到文本节点")
            LogUtils.logGGQ("任务1异常：${e.message}")
            withId(DouyinIds.getl4())?.click()
        }finally {

        }
    }


    fun doSwipe5(){
        do {

            MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")

            withId("com.ss.android.ugc.aweme:id/adv")?.globalClick()?.let {
                if (it) {
                    MyApplication.instance.getUiHandler().sendMessage("点击ADV")
                } else {
                    MyApplication.instance.getUiHandler().sendMessage("未点击ADV")
                }
            }


            withText("留下你的精彩评论吧")?.globalClick()?.let {
                if (it) {
                    MyApplication.instance.getUiHandler().sendMessage("点击-留下你的精彩评论吧-")
                    withId("com.ss.android.ugc.aweme:id/adq")?.trySetText("txt")
                        ?.let {
                            if (it) {
                                LogUtils.logGGQ("评论success")
                            } else {
                                LogUtils.logGGQ("评论error")
                            }
                        }
                    withId("com.ss.android.ugc.aweme:id/ae9")?.globalClick()?.let {
                        if (it) {
                            LogUtils.logGGQ("ae9:success")
                        } else {
                            LogUtils.logGGQ("ae9error")
                        }
                    }
                    withId("com.ss.android.ugc.aweme:id/ks")?.globalClick()?.let {
                        if (it) {
                            LogUtils.logGGQ("ks:success")
                        } else {
                            LogUtils.logGGQ("ks:error")
                        }
                    }
                }
            }
        }while (getSwiped())
    }



    fun doSwipe6(minTime:String,maxTime:String){
        do {
            try {
                val delayTime :Long = TaskUtils.randomTime(minTime,maxTime)
                MyApplication.instance.getUiHandler().sendMessage("等待${delayTime/1000L}秒")
//                WaitUtil.sleep(delayTime)
                SystemClock.sleep(delayTime)
                NodeUtils.onSwipe(service)
                withId(DouyinIds.geta91())?.finder?.find()?.let {it1 ->
                    if(it1.isNotEmpty()){
                        MyApplication.instance.getUiHandler().sendMessage("---it1--${it1.size}---")
                        val txt: String? = it1.last()?.text?.toString()
                        if(!TextUtils.isEmpty(txt)){
                            MyApplication.instance.getUiHandler().sendMessage(txt!!)
                            listContains.forEach {t ->
                                if(txt.contains(t)){
                                    userconfig = t
                                }
                            }
                            if (!TextUtils.isEmpty(userconfig)) {
                                withId(DouyinIds.getayl())?.finder?.find()?.let {it2 ->
                                    if(it2.isNotEmpty()){
                                        MyApplication.instance.getUiHandler().sendMessage("---it2--${it2.size}---")

                                        it2.last()?.let {it3 ->
                                            val desc:String? = it3.desc()
                                            if(!TextUtils.isEmpty(desc) && desc!!.contains("未选中")) {
                                                val isClickLike:Boolean = it3.globalClick()
                                                if(isClickLike){
                                                    MyApplication.instance.getUiHandler().sendMessage(">>点赞了<<")
                                                    //评论
                                                    withId(DouyinIds.getahl())?.globalClick()?.let { it4 ->
                                                        if(it4){
                                                            withId(DouyinIds.getahq())?.trySetText(txt)?.let { it5 ->
                                                                WaitUtil.sleep(2000L)
                                                                val isClickSend:Boolean = withId(DouyinIds.getai_())?.globalClick()
                                                                if(isClickSend){
                                                                    MyApplication.instance.getUiHandler().sendMessage("评论成功->>${txt}")
                                                                }
                                                            }
                                                            withId(DouyinIds.getl4())?.globalClick()
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }
                }

            }catch (e:Exception){
                e.printStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("任务1失败：${e.localizedMessage}")
            }finally {
                userconfig = ""
                try {
                    withId(DouyinIds.getl4())?.globalClick()
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }
        }while (getSwiped())


    }



    fun doSwipe7(minTime:String,maxTime:String){

        do {
            try {
                val delayTime :Long = TaskUtils.randomTime(minTime,maxTime)
                WaitUtil.sleep(delayTime)
                MyApplication.instance.getUiHandler().sendMessage("等待${delayTime/1000L}秒")
                withId(DouyinIds.geta91())?.finder?.find()?.let { it1 ->
                    MyApplication.instance.getUiHandler().sendMessage("查找->${it1.size}--${it1.last()?.text}")
                }
                NodeUtils.onSwipe(service)

            }catch (e:Exception){
                e.printStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("异常->${e.printStackTrace()}")
            }finally {
            }
        }while (getSwiped())
    }





    fun doSwipe8(){
        try {
            withId(DouyinIds.geta91())?.finder?.find()?.let { it1 ->
                MyApplication.instance.getUiHandler().sendMessage("查找->${it1.size}--${it1.last()?.text}")
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("异常->${e.printStackTrace()}")
        }finally {
        }
    }



    fun doSwipe9(delayTime :Long){
        try {
//            (service as AccessibilityApi)?.rootViewNode?.finder()?.find()?.let {
//                if(it.isNotEmpty()){
//                    it.forEach { i ->
//                        if(!TextUtils.isEmpty(i.text)){
//                            LogUtils.logQ("-->>>>>>${i.text}")
//                        }
//                    }
//                }
//            }


            withId(DouyinIds.geta91())?.find()?.let {it1 ->
                if(it1.isNotEmpty()){
                    it1.last()?.let { it2 ->
                        val txt:String? = it2.text?.toString()
                        if(!TextUtils.isEmpty(txt)){
                            LogUtils.logGGQ("-->>${txt}")
                            if(txt!!.contains("的")){
                                MyApplication.instance.getUiHandler().sendMessage(txt)
                            }
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }finally {

        }
    }


}
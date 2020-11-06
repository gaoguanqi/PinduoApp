package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.api.click
import cn.vove7.andro_accessibility_api.api.withId
import com.blankj.utilcode.util.ScreenUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.core.ids.DouyinIds
import com.pinduo.auto.db.AppDatabase
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.NodeUtils
import com.pinduo.auto.utils.TaskUtils
import com.pinduo.auto.utils.WaitUtil

// 养号
class AccountUpAccessbility private constructor() : BaseAccessbility<AccountUpAccessbility>() {

    private var isSwiped: Boolean = false
    private lateinit var userconfig: String
    private val listContains = arrayListOf<String>("服装","衣服","穿搭","上衣","裤子")

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
}
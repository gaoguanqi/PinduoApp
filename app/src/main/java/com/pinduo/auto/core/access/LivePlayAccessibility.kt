package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import cn.vove7.andro_accessibility_api.AccessibilityApi
import cn.vove7.andro_accessibility_api.api.*
import com.blankj.utilcode.util.ScreenUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.core.ids.DouyinIds
import com.pinduo.auto.im.SocketClient
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.NodeUtils
import com.pinduo.auto.utils.TaskUtils
import com.pinduo.auto.utils.WaitUtil
import com.pinduo.auto.widget.observers.ObserverListener
import com.pinduo.auto.widget.observers.ObserverManager
import kotlin.random.Random

class LivePlayAccessibility private constructor() : BaseAccessbility<LivePlayAccessibility>(),
    ObserverListener {

    companion object {
        val INSTANCE: LivePlayAccessibility by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            LivePlayAccessibility()
        }
    }

    //子任务是否执行成功
    private var isSuccess:Boolean = false

    private var isInRoom: Boolean = false
    private var liveURI: String = ""

    private var isLoopSpeak:Boolean = false

    fun isInLiveRoom(): Boolean = isInRoom
    fun setInLiveRoom(b: Boolean) {
        isInRoom = b
    }


    fun getLiveURI(): String = liveURI
    fun setLiveURI(s: String) {
        liveURI = s
    }


    fun getLoopSpeak(): Boolean = isLoopSpeak
    fun setLoopSpeak(b: Boolean) {
        isLoopSpeak = b
    }

    override fun initService(service: AccessibilityService): LivePlayAccessibility {
        return super.initService(service)
    }

    private var socketClient: SocketClient? = null
    fun getSocketClient(): SocketClient? = socketClient
    fun setSocketClient(socket: SocketClient) {
        this.socketClient = socket
    }


    //进入直播间
    fun doLiveRoom(zhiboNum: String) {
        startLiveRoom(zhiboNum)
    }


    // 发评论
    fun doSpeak(type:String,content: String,delayTime:Long){
        setLoopSpeak(false)
        if(TextUtils.equals("3",type)){
            setLoopSpeak(true)
            doLoopSpeak(content,delayTime)
        }else{
            doSingleSpeak(content,delayTime)
        }
    }


   //普通评论
    fun doSingleSpeak(content: String,delayTime:Long){
        isSuccess = false
        try {
            val txt:String = TaskUtils.getContentRandom(content)
            if(withId(DouyinIds.getb82())?.tryClick()){
                WaitUtil.sleep(1000L)
                withId(DouyinIds.getb9q())?.childAt(0)?.trySetText(txt)?.let {
                    if(it){
                        withId(DouyinIds.getfvs())?.tryClick()?.let {it1 ->
                            if(it1){
                                isSuccess = true
                                MyApplication.instance.getUiHandler().sendMessage("评论成功:${txt}")
                            }else{
                                MyApplication.instance.getUiHandler().sendMessage("未点击发送")
                            }
                        }
                    }else{
                        MyApplication.instance.getUiHandler().sendMessage("未设置文本")
                    }
                }
            }else{
                withId(DouyinIds.getfvs())?.tryClick()
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("评论失败！！！")
            try {
                MyApplication.instance.getUiHandler().sendMessage("尝试评论~！")
                withId(DouyinIds.getb9q())?.childAt(0)?.trySetText("~")?.let {
                    if(it){
                        WaitUtil.sleep(2000L)
                        val isTryClick = withId(DouyinIds.getfvs())?.tryClick()
                        MyApplication.instance.getUiHandler().sendMessage("尝试评论->${isTryClick}")
                    }
                }
            }catch (e:Exception){
                e.fillInStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("尝试评论~失败！")
            }
        }finally {
            if(isSuccess){
                getSocketClient()?.sendSuccess()
            }else{
                getSocketClient()?.sendError()
            }
            MyApplication.instance.getUiHandler().sendMessage("间隔时间${delayTime}毫秒")
            WaitUtil.sleep(delayTime)
        }
    }


    // 循环评论
    fun doLoopSpeak(content: String,delayTime:Long){
        TaskUtils.initContent(content)
        val fromTime:Long = delayTime
        val untilTime:Long = delayTime*2
        do {
            val delay:Long = Random.nextLong(fromTime,untilTime)
            try {
                val txt:String = TaskUtils.getContentIndex()
                if(withId(DouyinIds.getb82())?.tryClick()){
                    WaitUtil.sleep(1000L)
                    withId(DouyinIds.getb9q())?.childAt(0)?.trySetText(txt)?.let {
                        if(it){
                            withId(DouyinIds.getfvs())?.tryClick()?.let {it1 ->
                                if(it1){
                                    MyApplication.instance.getUiHandler().sendMessage("评论成功:${txt}")
                                }else{
                                    MyApplication.instance.getUiHandler().sendMessage("未点击发送")
                                }
                            }
                        }else{
                            MyApplication.instance.getUiHandler().sendMessage("未设置文本")
                        }
                    }
                }else{
                    withId(DouyinIds.getfvs())?.tryClick()
                }
            }catch (e:Exception){
                e.printStackTrace()
                MyApplication.instance.getUiHandler().sendMessage("评论失败！！！")
                try {
                    MyApplication.instance.getUiHandler().sendMessage("尝试评论~！")
                    withId(DouyinIds.getb9q())?.childAt(0)?.trySetText("~")?.let {
                        if(it){
                            WaitUtil.sleep(2000L)
                            val isTryClick = withId(DouyinIds.getfvs())?.tryClick()
                            MyApplication.instance.getUiHandler().sendMessage("尝试评论->${isTryClick}")
                        }
                    }
                }catch (e:Exception){
                    e.fillInStackTrace()
                    MyApplication.instance.getUiHandler().sendMessage("尝试评论~失败！")
                }
            }finally {
                MyApplication.instance.getUiHandler().sendMessage("间隔时间${delay}毫秒")
                WaitUtil.sleep(delay)
            }
        }while (getLoopSpeak())
    }




   private fun startLiveRoom(zhiboNum: String) {
        setLiveURI(zhiboNum)
        ObserverManager.instance.add(Constants.Task.task3,this)
        if (!isInLiveRoom() && !TextUtils.isEmpty(getLiveURI())) {
            inLiveRoom()
        }
    }


    override fun observer(content: String) {
        LogUtils.logGGQ("监听到页面：${content}")
        when (content) {
            Constants.Douyin.PAGE_MAIN -> {
                MyApplication.instance.getUiHandler().sendMessage("回到首页")
                setInLiveRoom(false)
                //如果在任务内回到首页,进入直播间
                startLiveRoom(getLiveURI())
            }

            Constants.Douyin.PAGE_LIVE_ROOM -> {
                MyApplication.instance.getUiHandler().sendMessage("进入直播间")
                setInLiveRoom(true)
            }

            Constants.Douyin.PAGE_LIVE_ANCHOR,
            Constants.Douyin.PAGE_LIVE_GIFT,
            Constants.Douyin.PAGE_LIVE_Follow,
            Constants.Douyin.PAGE_LIVE_MORE ->{
                if(isInLiveRoom()) {
                    //被遮挡点击操作
                    MyApplication.instance.getUiHandler().sendMessage("被遮挡")
                    LogUtils.logGGQ("被遮挡...")
                    val x = ScreenUtils.getScreenWidth() / 2
                    val y = ScreenUtils.getScreenHeight() / 2
                    val isClick: Boolean = click(x, y)
                    LogUtils.logGGQ(if (isClick) "点击" else "未点击")
                }
            }
        }
    }


    //-----------------
    // 点赞
    private val x = ScreenUtils.getScreenWidth() - 50
    private val y = 10
    private val delay = 1
    private val period = 500
    private var clickCount = 0
    fun doGiveLike(s:Long){
        clickCount = 0
        val count = (s / period)
        try {
            for (index in 1..count){
                if(isInLiveRoom() && AccessibilityApi.isGestureServiceEnable && pressWithTime(x,y,delay)){
                    clickCount++
                }
                MyApplication.instance.getUiHandler().sendMessage("${count}-->>>${index}次-->${clickCount}次成功")
                WaitUtil.sleep(500L)
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("点赞失败！！！")
        }finally {
            if(clickCount >= count){
                getSocketClient()?.sendSuccess()
            }else{
                getSocketClient()?.sendError()
            }
        }
    }

    //---------------购物车-----

    fun doShopCart() {
        isSuccess = false
        try {
            MyApplication.instance.getUiHandler().sendMessage("--开始刷购物车--")
            val isClickCart:Boolean = withId(DouyinIds.getfhq())?.waitFor(9000L)?.globalClick()?:false
            if(isClickCart){
                MyApplication.instance.getUiHandler().sendMessage("点击了购物车")
                val isClickShopItem = withId(DouyinIds.getfh9())?.waitFor(9000L)?.tryClick()?:false
                if(isClickShopItem){
                    MyApplication.instance.getUiHandler().sendMessage("点击了商品")
                    val isClickBuyNow = withText("立即购买")?.waitFor(9000L)?.tryClick()?:false
                    if(isClickBuyNow){
                        MyApplication.instance.getUiHandler().sendMessage("点击了立即购买")
                        WaitUtil.sleep(3000L)
                        NodeUtils.onClickTextByNode(service.rootInActiveWindow)
                        MyApplication.instance.getUiHandler().sendMessage("下单等待。。。")
                        WaitUtil.sleep(10000L)
                        isSuccess = withText("提交订单")?.waitFor(4000L)?.childAt(0)?.childAt(0)?.childAt(0)?.click()?:false
                        MyApplication.instance.getUiHandler().sendMessage("提交订单返回->${isSuccess}")
                        WaitUtil.sleep(2000L)
                        val isClickGiveUp:Boolean = withText("放弃")?.waitFor(3000L)?.globalClick()?:false
                        if(isClickGiveUp){
                            MyApplication.instance.getUiHandler().sendMessage("放弃->点击")
                        }else{
                            back()
                            MyApplication.instance.getUiHandler().sendMessage("放弃->返回")
                        }
                        WaitUtil.sleep(2000L)
                        val isClickBuyBack = withId(DouyinIds.getd_e())?.waitFor(3000L)?.click()?:false
                        if(isClickBuyBack){
                            MyApplication.instance.getUiHandler().sendMessage("立即购买back-点击")
                        }else{
                            back()
                            MyApplication.instance.getUiHandler().sendMessage("立即购买back->返回")
                        }
                        WaitUtil.sleep(2000L)
                        val isClickback = back()
                        MyApplication.instance.getUiHandler().sendMessage("返回->${isClickback}")
                    }else{
                        MyApplication.instance.getUiHandler().sendMessage("没有立即购买")
                        val isClickback1:Boolean = back()
                        MyApplication.instance.getUiHandler().sendMessage("返回1->${isClickback1}")
                        WaitUtil.sleep(2000L)
                        val isClickback2:Boolean = back()
                        MyApplication.instance.getUiHandler().sendMessage("返回2->${isClickback2}")
                    }
                }else{
                    val isClickback:Boolean = back()
                    MyApplication.instance.getUiHandler().sendMessage("没有商品->放回:${isClickback}")
                }
            }else{
                MyApplication.instance.getUiHandler().sendMessage("没有购物车")
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("购物车失败！！！")
        }finally {
            if(isSuccess){
                getSocketClient()?.sendSuccess()
            }else {
                getSocketClient()?.sendError()
            }
        }
    }


    private fun inLiveRoom(){
        setInLiveRoom(true)
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getLiveURI()))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        MyApplication.instance.startActivity(intent)
        MyApplication.instance.getUiHandler().sendMessage("<<<直播间>>>")
    }
}




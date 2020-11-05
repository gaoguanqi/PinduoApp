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
import com.pinduo.auto.utils.WaitUtil
import com.pinduo.auto.widget.observers.ObserverListener
import com.pinduo.auto.widget.observers.ObserverManager

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

    fun isInLiveRoom(): Boolean = isInRoom
    fun setInLiveRoom(b: Boolean) {
        isInRoom = b
    }


    fun getLiveURI(): String = liveURI
    fun setLiveURI(s: String) {
        liveURI = s
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
    fun doSpeak(content: String){
        isSuccess = false
        try {
            if(withId(DouyinIds.getb82())?.globalClick()){
                withId(DouyinIds.getb9q())?.childAt(0)?.trySetText(content)?.let {
                    if(it){
                        withId(DouyinIds.getfvs())?.globalClick()?.let {it1 ->
                            if(it1){
                                isSuccess = true
                                MyApplication.instance.getUiHandler().sendMessage("评论成功:${content}")
                            }else{
                                MyApplication.instance.getUiHandler().sendMessage("未点击发送")
                            }
                        }
                    }else{
                        MyApplication.instance.getUiHandler().sendMessage("未设置文本")
                    }
                }
            }else{
                withId(DouyinIds.getfvs())?.globalClick()
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("评论异常！！！")
            withId(DouyinIds.getfvs())?.globalClick()
        }finally {
            if(isSuccess){
                getSocketClient()?.sendSuccess()
            }else{
                getSocketClient()?.sendError()
            }
        }
    }


    private fun startLiveRoom(zhiboNum: String) {
        setLiveURI(zhiboNum)
        ObserverManager.instance.add(Constants.Task.task3,this)
        if (!isInLiveRoom() && !TextUtils.isEmpty(getLiveURI())) {
            setInLiveRoom(true)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getLiveURI()))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            MyApplication.instance.startActivity(intent)

            MyApplication.instance.getUiHandler().sendMessage("<<<直播间>>>")
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
            MyApplication.instance.getUiHandler().sendMessage("点赞异常！！！")
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
            withId(DouyinIds.getfhq())?.globalClick()?.let {
                if (it) {
                    withId(DouyinIds.getfh9())?.globalClick()?.let { it1 ->
                        if (it1) {
                            WaitUtil.sleep(2000L)
                            withText("立即购买")?.globalClick()?.let { it2 ->
                                if (it2) {
                                    WaitUtil.sleep(2000L)
                                    NodeUtils.onClickTextByNode(service.rootInActiveWindow)
                                    MyApplication.instance.getUiHandler().sendMessage("等待。。。")
                                    isSuccess = true
                                    WaitUtil.sleep(10000L)
                                    withType("WebView")?.find()?.let { it3 ->
                                        if (it3.size >= 2) {
                                            it3[1]?.childAt(0)?.childAt(0)?.childAt(0)?.globalClick()
                                        }
                                    }
                                    WaitUtil.sleep(2000L)
                                    back()
                                    WaitUtil.sleep(2000L)
                                    back()
                                    WaitUtil.sleep(1000L)
                                    back()
                                }
                            }
                        }else{
                            WaitUtil.sleep(2000L)
                            back()
                        }
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
            MyApplication.instance.getUiHandler().sendMessage("购物车异常！！！")
        }finally {
            if(isSuccess){
                getSocketClient()?.sendSuccess()
            }else {
                getSocketClient()?.sendError()
            }
        }
    }
}
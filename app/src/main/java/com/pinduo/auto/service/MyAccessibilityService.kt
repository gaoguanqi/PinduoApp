package com.pinduo.auto.service

import android.content.Context
import android.os.PowerManager
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import cn.vove7.andro_accessibility_api.AccessibilityApi
import cn.vove7.andro_accessibility_api.AppScope
import com.birbit.android.jobqueue.CancelResult
import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.TagConstraint
import com.birbit.android.jobqueue.callback.JobManagerCallback
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.core.access.CommonAccessbility
import com.pinduo.auto.core.access.LivePlayAccessibility
import com.pinduo.auto.core.data.TaskData
import com.pinduo.auto.core.job.BaseJob
import com.pinduo.auto.core.job.LiveTaskJob
import com.pinduo.auto.im.OnSocketListener
import com.pinduo.auto.im.SocketClient
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.widget.observers.ObserverManager
import com.pinduo.auto.widget.timer.MyScheduledExecutor
import com.pinduo.auto.widget.timer.TimerTickListener
import com.pinduo.auto.http.entity.TaskEntity
import com.pinduo.auto.utils.TaskUtils
import com.yhao.floatwindow.FloatWindow
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MyAccessibilityService :AccessibilityApi(){

    private val socketClient by lazy { SocketClient.instance }
    private val runnable by lazy { MyScheduledExecutor.INSTANCE }

    private val initialDelay:Long = 1L
    private val period:Long = 1L
    private val max:Long = Long.MAX_VALUE

    private val service by lazy {  Executors.newScheduledThreadPool(4) }
    private val uiHandler by lazy { MyApplication.instance.getUiHandler() }


    override val enableListenAppScope: Boolean = true

    private val haList = mutableListOf<String>()

    init {
        val s:String = "欢迎来到抖音直播间请大家注意财产安全谨防网络诈骗"
        s.toCharArray().asList().forEach {
            haList.add(it.toString())
        }
    }

    override fun onPageUpdate(currentScope: AppScope) {
        super.onPageUpdate(currentScope)
        currentScope?.let {
            LogUtils.logQ("className：${it.pageName}")
            if(TextUtils.equals(Constants.GlobalValue.PACKAGE_DOUYIN,it.packageName)){
                CommonAccessbility.INSTANCE.ignorePage(it.pageName)
                when(it.pageName){
                    Constants.Douyin.PAGE_MAIN,
                    Constants.Douyin.PAGE_LIVE_ROOM,
                    Constants.Douyin.PAGE_LIVE_ANCHOR,
                    Constants.Douyin.PAGE_LIVE_GIFT,
                    Constants.Douyin.PAGE_LIVE_Follow,
                    Constants.Douyin.PAGE_LIVE_MORE ->{
                        ObserverManager.instance.notifyObserver(Constants.Task.task3,it.pageName)
                    }
                }
            }
        }
    }

    override fun onCreate() {
        //must 基础无障碍
        baseService = this
        super.onCreate()
        //must 高级无障碍
        gestureService = this

        CommonAccessbility.INSTANCE.initService(this)
        LivePlayAccessibility.INSTANCE.initService(this).setSocketClient(socketClient)
        MyApplication.instance.getJobManager().addCallback(object :
            JobManagerCallback{
            override fun onJobRun(job: Job, resultCode: Int) {
                LogUtils.logGGQ("onJobRun：${(job as BaseJob).data.task}---${resultCode}")
            }

            override fun onDone(job: Job) {
                LogUtils.logGGQ("onDone：${(job as BaseJob).data.task}")
            }

            override fun onAfterJobRun(job: Job, resultCode: Int) {
                LogUtils.logGGQ("onAfterJobRun：${(job as BaseJob).data.task}---${resultCode}")
            }

            override fun onJobCancelled(
                job: Job,
                byCancelRequest: Boolean,
                throwable: Throwable?) {
                LogUtils.logGGQ("onJobCancelled：${(job as BaseJob).data.task}---${byCancelRequest}---${throwable}")

            }

            override fun onJobAdded(job: Job) {
                LogUtils.logGGQ("onJobAdded：${(job as BaseJob).data.task}")
            }
        })

        (getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).addAccessibilityStateChangeListener {
            LogUtils.logGGQ("AccessibilityManager：${it}")
            if(it){
                FloatWindow.get()?.run {
                    if(!isShowing)show()
                }
            }else{
                FloatWindow.get()?.run {
                    if(isShowing)hide()
                }
            }
        }

        runnable.setListener(object : TimerTickListener {
            override fun onStart(name: String, job: String) {
                LogUtils.logGGQ("开始任务：${name} --- ${job}")
                uiHandler.sendMessage("开始任务：${name} --- ${job}")
            }

            override fun onTick(tick: Long) {
                LogUtils.logGGQ("tick：${tick}")
                uiHandler.sendMessage("tick：${tick}")
            }

            override fun onMark(mark: Long) {
                LogUtils.logGGQ("onMark：${mark}")
                uiHandler.clearMessage()
                uiHandler.sendMessage("onMark：${mark}")
                TaskUtils.wakeUpAndUnlock()

            }

            override fun onStop(name: String, job: String) {
                LogUtils.logGGQ("结束任务：${name} --- ${job}")
                uiHandler.sendMessage("结束任务：${name} --- ${job}")
                uiHandler.clearMessage()
                if(TextUtils.equals(Constants.Task.douyin,name)){
                    when(job){
                        Constants.Task.task3 ->{
                            if(LivePlayAccessibility.INSTANCE.isInLiveRoom()){
                                stopTask(job,false)
                            }
                        }
                    }
                }
            }
        })

        socketClient.setListener(object : OnSocketListener {
            override fun call(entity: TaskEntity) {
                LogUtils.logGGQ("收到数据：${entity}")
                val software:String = entity.software
                val task:String = entity.task
                val message:String = entity.message

                if(!TextUtils.isEmpty(message)){
                    uiHandler.sendMessage(message)
                }

                if(!TextUtils.isEmpty(task) && !TextUtils.isEmpty(message) && TextUtils.equals(message,"stop")){
                    stopTask(task,true)
                    return
                }

                if(TextUtils.equals(software,Constants.Task.douyin)){
                    when(task){
                        Constants.Task.task1 ->{
                            // 任务1 接收到数据 要回馈
                            socketClient.onReceiveStatus()

                        }


                        Constants.Task.task3 -> {
                            // 任务3 接收到数据 要回馈
                            socketClient.onReceiveStatus()

                            val zxTime:String = entity.zx_time
                            val zhiboNum:String = entity.zhibo_num
                            if(!TextUtils.isEmpty(zxTime) && !TextUtils.isEmpty(zhiboNum)){
                                runnable.onReStart(software,task,zxTime.toLong() + Constants.GlobalValue.plusTime)
                                LivePlayAccessibility.INSTANCE.doLiveRoom(zhiboNum)
                            }
                        }

                        Constants.Task.task4 -> {
                            MyApplication.instance.getJobManager().addJobInBackground(LiveTaskJob(TaskData(task = task,content = entity.fayan))){
                                //回调
                            }

//                            MyApplication.instance.getJobManager().run {
//                                haList.forEach {
//                                    this.addJobInBackground(LiveTaskJob(TaskData(task = task,content = it))){
//
//                                    }
//                                }
//                            }
                        }

                        Constants.Task.task6 ->{
                            val zxTime:String = entity.zhixing_time
                            if(!TextUtils.isEmpty(zxTime) && zxTime.toLong() > 0){
                                MyApplication.instance.getJobManager().addJobInBackground(LiveTaskJob(TaskData(task = task,zxTime = (zxTime.toLong() * 1000L)))){
                                    //回调
                                }
                            }
                        }

                        Constants.Task.task9 ->{
                            MyApplication.instance.getJobManager().addJobInBackground(LiveTaskJob(TaskData(task = task))){
                                //回调
                            }
                        }
                    }
                }
            }
        })
    }



    private fun stopTask(task:String,isNormal:Boolean = true) {
        //任务结束 停止所有job
        MyApplication.instance.getJobManager().cancelJobsInBackground(CancelResult.AsyncCancelCallback {cancelResult ->
            LogUtils.logGGQ("任务结束停止所有job")
        }, TagConstraint.ALL,"job")

        when(task){
            Constants.Task.task1 ->{
            }

            Constants.Task.task3 -> {
                if (isNormal && LivePlayAccessibility.INSTANCE.isInLiveRoom()) {
                    socketClient.sendParentSuccess()
                } else {
                    socketClient.sendParentError()
                }
                LivePlayAccessibility.INSTANCE.setInLiveRoom(false)
                LivePlayAccessibility.INSTANCE.setLiveURI("")
                ObserverManager.instance.remove(task)
                CommonAccessbility.INSTANCE.douyin2Main()
                if(isNormal){
                    uiHandler.sendMessage("正常结束")
                }else{
                    uiHandler.sendMessage("延时结束")
                }
            }
        }
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        socketClient.onConnect()
        runnable.isRuning().let {
            if(!it){
                service.scheduleAtFixedRate(runnable,initialDelay,period, TimeUnit.SECONDS)
                runnable.onReStart("app","task",max)
            }
        }
    }


    override fun onInterrupt() {
        super.onInterrupt()
        FloatWindow.destroy()
    }

    override fun onDestroy() {
        //must 基础无障碍
        baseService = null
        super.onDestroy()
        //must 高级无障碍
        gestureService = null
        FloatWindow.destroy()
    }
}
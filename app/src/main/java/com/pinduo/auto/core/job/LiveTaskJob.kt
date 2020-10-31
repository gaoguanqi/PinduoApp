package com.pinduo.auto.core.job

import com.birbit.android.jobqueue.RetryConstraint
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.core.access.LivePlayAccessibility
import com.pinduo.auto.core.data.TaskData
import com.pinduo.auto.utils.LogUtils

class LiveTaskJob(data: TaskData) :BaseJob(data){


    @Throws(Throwable::class)
    override fun onRun() {

        LogUtils.logGGQ("job onRun:${runGroupId}--${tags}")
        LogUtils.logGGQ("data:${data.content}")

        when(data.task){
            Constants.Task.task4 ->{
                LivePlayAccessibility.INSTANCE.doSpeak(data.content)
            }

            Constants.Task.task6 ->{
                LivePlayAccessibility.INSTANCE.doGiveLike(data.zxTime)
            }

            Constants.Task.task9 ->{
                LivePlayAccessibility.INSTANCE.doShopCart()
            }
        }
    }

    override fun shouldReRunOnThrowable(
        throwable: Throwable,
        runCount: Int,
        maxRunCount: Int
    ): RetryConstraint {
        LogUtils.logGGQ("job shouldReRunOnThrowable")
        return RetryConstraint.createExponentialBackoff(runCount, 1000)
    }

    override fun onAdded() {
        LogUtils.logGGQ("job onAdded")
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        LogUtils.logGGQ("job onCancel")
    }

    override fun getRetryLimit(): Int {
       // return super.getRetryLimit()
        //仅仅重启3次次，超过3次则放弃任务。
        return 0
    }
}
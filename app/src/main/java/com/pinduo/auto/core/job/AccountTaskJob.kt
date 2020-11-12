package com.pinduo.auto.core.job

import com.birbit.android.jobqueue.RetryConstraint
import com.pinduo.auto.app.global.Constants
import com.pinduo.auto.core.access.AccountUpAccessbility
import com.pinduo.auto.core.data.TaskData
import com.pinduo.auto.utils.LogUtils

class AccountTaskJob(data: TaskData) :BaseJob(data){
    @Throws(Throwable::class)
    override fun onRun() {
        LogUtils.logGGQ("job onRun:${runGroupId}--${tags}")
        LogUtils.logGGQ("data:${data.content}")

        when(data.task){
            Constants.Task.task1 ->{
//                AccountUpAccessbility.INSTANCE.slide(data.minTime,data.maxTime,"的")
                AccountUpAccessbility.INSTANCE.doSwipe10(data.minTime,data.maxTime)
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
}
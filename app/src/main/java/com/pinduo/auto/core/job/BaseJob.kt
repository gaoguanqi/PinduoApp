package com.pinduo.auto.core.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.pinduo.auto.core.data.TaskData

//job 抽象类
abstract class BaseJob(val data: TaskData): Job(Params(PRIORITY).requireNetwork().persist().groupBy("job").addTags("job")) {

    companion object {
        const val PRIORITY = 1
    }

    override fun getRetryLimit(): Int {
//        return super.getRetryLimit()
        return 1
    }
}
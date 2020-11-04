package com.pinduo.auto.core.job

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.pinduo.auto.core.data.TaskData

abstract class BaseJob(val data: TaskData): Job(Params(PRIORITY).requireNetwork().persist().groupBy("job").addTags("task")) {

    companion object {
        const val PRIORITY = 1
    }
}
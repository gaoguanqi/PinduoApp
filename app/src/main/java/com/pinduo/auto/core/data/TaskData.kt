package com.pinduo.auto.core.data

import java.io.Serializable


class TaskData(var isExecute:Boolean = false, val delayTime:Long = 1000L,val task:String = "",val zxTime:Long = 0,val minTime:String = "",
               val maxTime:String = "", val content:String = "",val type:String=""): Serializable
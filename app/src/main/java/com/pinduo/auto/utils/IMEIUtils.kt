package com.pinduo.auto.utils

import com.blankj.utilcode.util.DeviceUtils

class IMEIUtils {

    companion object{

        fun getDeviceId():String?{
            return DeviceUtils.getAndroidID()
        }
    }
}
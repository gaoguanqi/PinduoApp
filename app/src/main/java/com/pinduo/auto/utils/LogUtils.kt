package com.pinduo.auto.utils

import android.util.Log

class LogUtils {
    companion object {

        private val isShow:Boolean = true

        @JvmStatic
        fun logGGQ(s: String?) {
            if (isShow) {
                Log.i("GGQ", "->>>${s}")
            }
        }

        @JvmStatic
        fun logQ(s: String?) {
            if (isShow) {
                Log.i("TAGQ", "->>>${s}")
            }
        }
    }
}
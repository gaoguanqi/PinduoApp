package com.pinduo.auto.extensions

import com.pinduo.auto.app.config.Config

// 自定义 Int 的 扩展方法 http 的 响应 code
internal inline fun Int.isResultSuccess():Boolean{
    return Config.SUCCESS_CODE == this || Config.REBIND_CODE == this
}

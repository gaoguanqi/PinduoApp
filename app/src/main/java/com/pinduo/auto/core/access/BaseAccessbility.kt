package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService

/**
 * 无障碍中执行任务的抽象类,所有实现类使用之前都必须先进行初始化
 */
abstract class BaseAccessbility<out T> {

    lateinit var service: AccessibilityService

    open fun initService(service: AccessibilityService): T {
        this.service = service
        return this as T
    }


}
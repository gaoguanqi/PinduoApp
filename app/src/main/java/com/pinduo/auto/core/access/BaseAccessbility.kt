package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService

abstract class BaseAccessbility<out T> {

    lateinit var service: AccessibilityService

    open fun initService(service: AccessibilityService): T {
        this.service = service
        return this as T
    }


}
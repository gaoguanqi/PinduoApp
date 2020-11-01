package com.pinduo.auto.core.access

import android.accessibilityservice.AccessibilityService

// 养号
class AccountUpAccessbility private constructor() :BaseAccessbility<AccountUpAccessbility>(){

    companion object {
        val INSTANCE: AccountUpAccessbility by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            AccountUpAccessbility()
        }
    }

    override fun initService(service: AccessibilityService): AccountUpAccessbility {
        return super.initService(service)
    }

}
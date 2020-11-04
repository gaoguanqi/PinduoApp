package com.pinduo.auto.http.api

import com.pinduo.auto.app.config.Config

class ApiService {
    companion object{
        //绑定设备
        const val URL_BINDDEVICE:String = Config.BASE_URL + "/api/task/bind_device"
        //版本更新
        const val URL_VERINFO:String = Config.BASE_URL + "/api/app/appVersionInfo"

    }
}
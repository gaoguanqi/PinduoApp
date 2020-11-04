package com.pinduo.auto.http.entity

data class VersionInfoEntity(val code:Int,val message:String = "未知错误",val data:VersionDataEntity? = null)
data class VersionDataEntity(val version_code:Int = 0,val apk_url:String = "")
package com.pinduo.auto.widget.download

import android.content.Context
import android.text.TextUtils
import android.text.format.Formatter
import com.blankj.utilcode.util.AppUtils
import com.google.gson.Gson
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.callback.StringCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.extensions.isResultSuccess
import com.pinduo.auto.http.api.ApiService
import com.pinduo.auto.http.entity.VersionInfoEntity
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.ToastUtil
import java.io.File


class DownLoadUtils {

    fun checkVersion() {
        OkGo.get<String>(ApiService.URL_VERINFO)
            .tag(this)
            .execute(object : StringCallback() {
                override fun onSuccess(response: Response<String>?) {
                    try {
                        response?.let { // 非空
                            val result: String? = it.body()
                            result?.let { it1 ->
                                val entity: VersionInfoEntity? = Gson().fromJson<VersionInfoEntity>(
                                    it1,
                                    VersionInfoEntity::class.java
                                )
                                entity?.let { it2 ->
                                    if (it2.code.isResultSuccess()) {
                                        it2.data?.let { it3 ->
                                            if (!TextUtils.isEmpty(it3.apk_url) && it3.version_code > AppUtils.getAppVersionCode()) {
                                                DownLoadUtils().downLoadAndInstallAPK(
                                                    MyApplication.instance,
                                                    it3.apk_url
                                                )
                                            }
                                        }
                                    } else {
                                        LogUtils.logGGQ(it2.message)
                                    }
                                }
                            }
                        }
                    } catch (e: Exception) {
                        e.stackTrace
                        LogUtils.logGGQ("检查版本出错！")
                    }
                }

                override fun onError(response: Response<String>?) {
                    super.onError(response)
                    LogUtils.logGGQ("检查版本错误！")
                }
            })
    }

    fun downLoadAndInstallAPK(context: Context, url: String) {
        val name = AppUtils.getAppName()+".apk"
        OkGo.get<File>(url)
            .tag(this)
//            .params("key","value")
            .execute(object : FileCallback(name) {
                override fun onStart(request: Request<File, out Request<Any, Request<*, *>>>?) {
                    super.onStart(request)
                    LogUtils.logGGQ("开始下载")
                    ToastUtil.showTipToast("正在下载新版本")
                }

                override fun downloadProgress(progress: Progress?) {
                    super.downloadProgress(progress)
                    progress?.let {
                        val currentSize = Formatter.formatFileSize(context, progress.currentSize)
                        val totalSize = Formatter.formatFileSize(context, progress.totalSize)
                        LogUtils.logGGQ("下载进度->${currentSize} -- ${totalSize}")
                    }
                }

                override fun onSuccess(response: Response<File>?) {
                    LogUtils.logGGQ("下载成功")
                    response?.body()?.let {
                        if (!TextUtils.isEmpty(it.path)) {
                            AppUtils.installApp(it.path)
                            LogUtils.logGGQ("下载成功-->>>${it.path}")
                        }
                    }
                }

                override fun onError(response: Response<File>?) {
                    super.onError(response)
                    LogUtils.logGGQ("下载错误")

                }

                override fun onFinish() {
                    super.onFinish()
                    LogUtils.logGGQ("下载结束")
                }
            })
    }
}



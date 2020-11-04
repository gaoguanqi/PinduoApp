package com.pinduo.auto.widget.download

import android.content.Context
import android.text.TextUtils
import android.text.format.Formatter
import com.blankj.utilcode.util.AppUtils
import com.lzy.okgo.OkGo
import com.lzy.okgo.callback.FileCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request
import com.pinduo.auto.utils.LogUtils
import java.io.File


class DownLoadUtils {

    fun downLoadAndInstallAPK(context: Context,url:String,listener: DownLoadListener){

        OkGo.get<File>(url)
            .tag(this)
//            .params("key","value")
            .execute(object :FileCallback("app.apk"){
                override fun onStart(request: Request<File, out Request<Any, Request<*, *>>>?) {
                    super.onStart(request)
                    LogUtils.logGGQ("开始下载")
                }

                override fun downloadProgress(progress: Progress?) {
                    super.downloadProgress(progress)

                    progress?.let {
                        val currentSize = Formatter.formatFileSize(context,progress.currentSize)
                        val totalSize = Formatter.formatFileSize(context,progress.totalSize)
                        LogUtils.logGGQ("下载进度->${currentSize} -- ${totalSize}")
                    }
                }

                override fun onSuccess(response: Response<File>?) {
                    LogUtils.logGGQ("下载成功")
//                    AppUtils.installApp()
                    response?.body()?.let {
                        if(!TextUtils.isEmpty(it.path)){
                            listener.onSuccess(it.path)
                            LogUtils.logGGQ("下载成功:${it.path}")
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

interface DownLoadListener{
    fun onSuccess(path:String)
}
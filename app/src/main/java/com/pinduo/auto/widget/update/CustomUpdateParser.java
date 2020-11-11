package com.pinduo.auto.widget.update;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.pinduo.auto.utils.LogUtils;
import com.xuexiang.xupdate.entity.UpdateEntity;
import com.xuexiang.xupdate.listener.IUpdateParseCallback;
import com.xuexiang.xupdate.proxy.IUpdateParser;

/**
 * 自定义更新解析器
 *
 * @author xuexiang
 * @since 2018/7/12 下午3:46
 */
public class CustomUpdateParser implements IUpdateParser {
    @Override
    public UpdateEntity parseJson(String json) throws Exception {
        return getParseResult(json);
    }

    private UpdateEntity getParseResult(String json) {
        LogUtils.logGGQ("版本更新："+json);
        VersionInfo result = GsonUtils.fromJson(json, VersionInfo.class);
        if (result != null && result.getCode() == 200 && result.getData() != null) {
            boolean hasUpdate = true; //是否有新版本
            boolean isIgnorable = false;//是否可忽略该版本
            int versionCode = result.getData().getVersion_code(); //版本号
            String versionName = AppUtils.getAppVersionName(); //版本名
            String updateContent = "新版本"; //更新内容
//            String apkUrl = result.getData().getApk_url();
            String apkUrl = "http://cc.pinduocm.com/apkDownload";
//            String apkUrl = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
            Long apkSize = 0L;
            return new UpdateEntity()
                    .setHasUpdate(hasUpdate)
                    .setIsIgnorable(isIgnorable)
                    .setVersionCode(versionCode)
                    .setVersionName(versionName)
                    .setUpdateContent(updateContent)
                    .setDownloadUrl(apkUrl)
                    .setSize(apkSize);
        }
        return null;
    }

    @Override
    public void parseJson(String json, @NonNull IUpdateParseCallback callback) throws Exception {
        //当isAsyncParser为 true时调用该方法, 所以当isAsyncParser为false可以不实现
        callback.onParseResult(getParseResult(json));
    }


    @Override
    public boolean isAsyncParser() {
        return false;
    }
}

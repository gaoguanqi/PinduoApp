package com.pinduo.auto.widget.update;

import java.io.Serializable;

/**
 * 自定义版本检查的结果
 *
 * @author xuexiang
 * @since 2018/7/11 上午1:03
 */
public class VersionInfo implements Serializable {

    /**
     * code : 200
     * message : 成功
     * data : {"version_code":1224,"apk_url":"http://ceshi.pinduocm.com/apkDownload"}
     */

    private int code;
    private String message;
    private VersionInfoData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VersionInfoData getData() {
        return data;
    }

    public void setData(VersionInfoData data) {
        this.data = data;
    }

    public static class VersionInfoData {
        /**
         * version_code : 1224
         * apk_url : http://ceshi.pinduocm.com/apkDownload
         */

        private int version_code;
        private String apk_url;

        public int getVersion_code() {
            return version_code;
        }

        public void setVersion_code(int version_code) {
            this.version_code = version_code;
        }

        public String getApk_url() {
            return apk_url;
        }

        public void setApk_url(String apk_url) {
            this.apk_url = apk_url;
        }
    }
}
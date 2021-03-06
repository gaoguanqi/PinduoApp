package com.pinduo.auto.app.global

import android.os.Environment


class Constants {
    object Path {
        val IMEI_PATH: String = Environment.getExternalStorageDirectory().toString() + "/android/imei.text"
    }

    object ApiParams {
        const val USERNAME = "username"
        const val IMEI = "imei"
    }

    object SaveInfoKey {

    }


    object GlobalValue {
        const val PACKAGE_DOUYIN = "com.ss.android.ugc.aweme"
        const val PACKAGE_KUAISHOU = "com.smile.gifmaker"
        const val PACKAGE_SOGOUOEM = "com.sohu.inputmethod.sogouoem" // 搜狗输入法

        //启动有抖音10秒的启动时间+10秒的延迟时间
        const val plusTime: Long = 20L // 追加时间10秒
    }

    object BundleKey {

    }


    object Task {
        const val douyin = "1"
        const val kuaishou = "2"

        const val task0 = "0"



        const val task1 = "1"
        const val task2 = "2"
        const val task3 = "3"
        const val task4 = "4"
        const val task5 = "5"
        const val task6 = "6"
        const val task7 = "7"
        const val task8 = "8"
        const val task9 = "9"

    }


    object Douyin {
        const val V1220 = "12.2.0"
        const val V1270 = "12.7.0"
        //抖音首页
        const val PAGE_MAIN = "com.ss.android.ugc.aweme.main.MainActivity"
        //抖音直播页
        const val PAGE_LIVE_ROOM = "com.ss.android.ugc.aweme.live.LivePlayActivity"
        //主播头像弹出框
        const val PAGE_LIVE_ANCHOR = "com.bytedance.android.livesdk.widget.LiveBottomSheetDialog"
        //直播页忽略页面
        const val PAGE_LIVE_GIFT = "com.bytedance.android.livesdk.gift.c.a"
        const val PAGE_LIVE_Follow = "com.bytedance.android.livesdk.chatroom.viewmodule.FollowGuideWidget${'$'}a"
        const val PAGE_LIVE_MORE = "com.bytedance.android.livesdk.chatroom.viewmodule.toolbar.LiveToolbarMoreDialog"
        //直播间购物车
        const val PAGE_LIVE_CART = "com.bytedance.android.livesdk.livecommerce.dialog.ECBottomDialog"
        //购物车内立即购买
        const val PAGE_CART_BUY_NOW = "com.ss.android.ugc.aweme.commerce.sdk.anchorv3.AnchorV3Activity"

        //抖音版本更新 以后再说
        const val PAGE_UPDATE_X = "com.ss.android.ugc.aweme.update.x"
        const val PAGE_UPDATE_Y = "com.ss.android.ugc.aweme.update.y"

        //进入儿童/青少年模式 我知道了
        const val PAGE_YANG = "android.app.AlertDialog"
        //弹出通讯录  取消
        const val PAGE_DN = "com.ss.android.ugc.aweme.main.dn"
        //个人信息 好的
        const val PAGE_DB = "com.ss.android.ugc.aweme.main.db"
        //个人信息 好的
        const val PAGE_USERPROFIL = "com.ss.android.ugc.aweme.profile.ui.UserProfileActivity"
        //个人信息保护指引 同意
        const val PAGE_CX = "com.ss.android.ugc.aweme.main.cx"
        //通知,是件很重要的事情  稍后
        const val PAGE_RECOMMENDCONTACT = "com.ss.android.ugc.aweme.friends.ui.RecommendContactActivity"
        //悬浮窗播放功能上线啦 暂不使用
        const val PAGE_FLOATVIEW_D = "com.bytedance.android.livesdk.floatview.d"
    }

    object Sougou{
        //声明与协议
        const val PAGE_AGREE = "android.inputmethodservice.SoftInputWindow"

        // 定制版声明
        const val PAGE_CUSTOM = "eu"

        //提示升级
        const val PAGE_UPDATE = "oq"

        // 声明与条款
        const val PAGE_PROVISION = "color.support.v7.app.AlertDialog"
    }

}
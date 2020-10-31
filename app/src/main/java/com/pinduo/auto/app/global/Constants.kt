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

        const val plusTime: Long = 10L // 追加时间10秒
    }

    object BundleKey {

    }


    object Task {
        const val douyin = "1"
        const val kuaishou = "2"

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

        const val PAGE_MAIN = "com.ss.android.ugc.aweme.main.MainActivity"
        const val PAGE_LIVE_ROOM = "com.ss.android.ugc.aweme.live.LivePlayActivity"

        //主播头像弹出框
        const val PAGE_LIVE_ANCHOR = "com.bytedance.android.livesdk.widget.LiveBottomSheetDialog"

        //
        const val PAGE_LIVE_GIFT = "com.bytedance.android.livesdk.gift.c.a"
        const val PAGE_LIVE_Follow = "com.bytedance.android.livesdk.chatroom.viewmodule.FollowGuideWidget${'$'}a"
        const val PAGE_LIVE_MORE = "com.bytedance.android.livesdk.chatroom.viewmodule.toolbar.LiveToolbarMoreDialog"

        //直播间购物车
        const val PAGE_LIVE_CART = "com.bytedance.android.livesdk.livecommerce.dialog.ECBottomDialog"

        //评论
        const val PAGE_LIVE_INPUT = "android.app.Dialog"
    }
}
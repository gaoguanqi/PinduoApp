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
        //

    }


    object KEYWORD{
        const val KEY_L = "篮"
        const val KEY_RE="人生"
        const val KEY_WL="未来"
        const val KEY_MB="目标"
        const val KEY_JL="焦虑"
        const val KEY_QN="的"
        const val KEY_QT="前途"
        const val KEY_DR="大人"
        const val KEY_YG="月光"
        const val KEY_PY="朋友"
        const val KEY_ZB="自卑"
        const val KEY_YL="眼泪"
        const val KEY_SY="岁月"
        const val KEY_TY="太阳"
        const val KEY_Z="醉"

        const val VALUE_L="篮球运动是一项直接对抗的运动项目，身体接触频繁且强烈，这是篮球运动的魅力所在"
        const val VALUE_RE="有三种类型的能力可以决定你的未来，第一种是让自己变得牛逼的能力，第二种是有让周围人都愿意来帮助自己的能力，第三种是混不好也无所谓，想得开的能力。这三种能力，练成任何一种，人生就有奔头"
        const val VALUE_WL="有三种类型的能力可以决定你的未来，第一种是让自己变得牛逼的能力，第二种是有让周围人都愿意来帮助自己的能力，第三种是混不好也无所谓，想得开的能力。这三种能力，练成任何一种，人生就有奔头"
        const val VALUE_MB="无法达成的目标才是我的目标，迂回曲折的路才是我想走的路，而每次的歇息，总是带来新的向往。"
        const val VALUE_JL="如果想征服生命中的焦虑，活在当下，活在每一个呼吸里"
        const val VALUE_QN="愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光，就令萤火一般，也可以在黑暗里发一点光。不必等候炬火。此后如竟没有炬火：我便是唯一的光"
        const val VALUE_QT="纵有千古，横有八荒；前途似海，来日方长"
        const val VALUE_DR="我将融入剧烈争斗的大人世界，要在那里边孤军奋战必须变得比任何人都坚不可摧"
        const val VALUE_YG="月光还是少年的月光，九州一色还是李白的霜"
        const val VALUE_PY="知交零落实是人生常态，能够偶尔话起，而心中仍然温柔，就是好朋友"
        const val VALUE_ZB="无论人生上到哪一层台阶，阶下有人在仰望你，阶上亦有人在俯视你。你抬头自卑，低头自得，唯有平视，才能看见真实的自己"
        const val VALUE_YL="我不要听到你嘴里的积极口号，不要看到你在受伤之后的眼泪。我要看到你平地一声雷的蛰伏，看到你特立独行却不被孤立的魅力，看到你与世无争却有迹可循的野心，看到你说堕落，却又不自甘堕落的自制力"
        const val VALUE_SY="岁月不饶人，我亦未曾饶过岁月"
        const val VALUE_Z="应是天仙狂醉，乱把白云揉碎"
        const val VALUE_TY="活在这珍贵的人间，太阳强烈，水波温柔"





        val map = mutableMapOf<String,String>()
        init {
            map.put("篮","篮球运动是一项直接对抗的运动项目，身体接触频繁且强烈，这是篮球运动的魅力所在")
            map.put("人生","有三种类型的能力可以决定你的未来，第一种是让自己变得牛逼的能力，第二种是有让周围人都愿意来帮助自己的能力，第三种是混不好也无所谓，想得开的能力。这三种能力，练成任何一种，人生就有奔头")
            map.put("未来","有三种类型的能力可以决定你的未来，第一种是让自己变得牛逼的能力，第二种是有让周围人都愿意来帮助自己的能力，第三种是混不好也无所谓，想得开的能力。这三种能力，练成任何一种，人生就有奔头")
            map.put("目标","无法达成的目标才是我的目标，迂回曲折的路才是我想走的路，而每次的歇息，总是带来新的向往。")
            map.put("焦虑","如果想征服生命中的焦虑，活在当下，活在每一个呼吸里。")
            map.put("青年","愿中国青年都摆脱冷气，只是向上走，不必听自暴自弃者流的话。能做事的做事，能发声的发声。有一分热，发一分光，就令萤火一般，也可以在黑暗里发一点光。不必等候炬火。此后如竟没有炬火：我便是唯一的光")
            map.put("我","借我一个暮年，借我碎片，借我瞻前与顾后，借我执拗如少年")
            map.put("前途","纵有千古，横有八荒；前途似海，来日方长")
            map.put("大人","我将融入剧烈争斗的大人世界，要在那里边孤军奋战必须变得比任何人都坚不可摧")
            map.put("月光","月光还是少年的月光，九州一色还是李白的霜")
            map.put("朋友","知交零落实是人生常态，能够偶尔话起，而心中仍然温柔，就是好朋友")
            map.put("自卑","无论人生上到哪一层台阶，阶下有人在仰望你，阶上亦有人在俯视你。你抬头自卑，低头自得，唯有平视，才能看见真实的自己")
            map.put("眼泪","我不要听到你嘴里的积极口号，不要看到你在受伤之后的眼泪。我要看到你平地一声雷的蛰伏，看到你特立独行却不被孤立的魅力，看到你与世无争却有迹可循的野心，看到你说堕落，却又不自甘堕落的自制力")
            map.put("岁月","岁月不饶人，我亦未曾饶过岁月")
            map.put("太阳","活在这珍贵的人间，太阳强烈，水波温柔")
            map.put("醉","应是天仙狂醉，乱把白云揉碎")
        }
    }
}
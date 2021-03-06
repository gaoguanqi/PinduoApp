package com.pinduo.auto.core.ids

import com.pinduo.auto.utils.TaskUtils

//抖音 版本区分的id 不是最优方案
class DouyinIds {

    companion object{

        private const val baseId = "com.ss.android.ugc.aweme:id/"

        fun getb82():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"b2c"
            }
            return baseId+"b82"
        }

        fun getb9q():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"b40"
            }
            return baseId+"b9q"
        }


        fun getfvs():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"fga"
            }
            return baseId+"fvs"
        }

        fun getfhq():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"f3t"
            }
            return baseId+"fhq"
        }

        fun getfh9():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"f3b"
            }
            return baseId+"fh9"
        }

        fun getd_e():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"d0q"
            }
            return baseId+"d_e"
        }
        //todo Main 描述文字
        fun geta91():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"a92"
            }
            return return baseId+"a91"
        }

        // todo Main 评论按钮
        fun getahl():String{
            //com.ss.android.ugc.aweme:id/ahl  12.7
            //com.ss.android.ugc.aweme:id/adk  12.2
            if(!TaskUtils.isDouyin1270()){
                return baseId+"adk"
            }
            return return baseId+"ahl"
        }

        // todo Main 点赞按钮
        fun getayl():String{
            //com.ss.android.ugc.aweme:id/ayl 12.7
            //com.ss.android.ugc.aweme:id/atq  12.2
            if(!TaskUtils.isDouyin1270()){
                return baseId+"atq"
            }
            return baseId+"ayl"
        }

        //todo Main 评论EditText
        fun getahq():String{
            //com.ss.android.ugc.aweme:id/ahq 12.7
            //com.ss.android.ugc.aweme:id/adq 12.2
            if(!TaskUtils.isDouyin1270()){
                return baseId+"adq"
            }
            return baseId+"ahq"
        }

        // todo Main 发送按钮
        fun getai_():String{
            //com.ss.android.ugc.aweme:id/ai_ 12.7
            //com.ss.android.ugc.aweme:id/ae9 12.2
            if(!TaskUtils.isDouyin1270()){
                return baseId+"ae9"
            }
            return baseId+"ai_"
        }

        // todo Main 关闭编辑X按钮
        fun getl4():String{
            //com.ss.android.ugc.aweme:id/l4 12.7
            //com.ss.android.ugc.aweme:id/ks 12.2
            if(!TaskUtils.isDouyin1270()){
                return baseId+"ks"
            }
            return baseId+"l4"
        }
    }


}
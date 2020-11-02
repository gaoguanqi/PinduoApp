package com.pinduo.auto.core.ids

import com.pinduo.auto.utils.TaskUtils


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

        //todo Main 描述文字
        fun geta91():String{
            if(!TaskUtils.isDouyin1270()){
                return baseId+"a91"
            }
            return return baseId+"a91"
        }

        // todo Main 评论按钮
        fun getahl():String{
            //com.ss.android.ugc.aweme:id/ahl
            if(!TaskUtils.isDouyin1270()){
                return baseId+"ahl"
            }
            return return baseId+"ahl"
        }

    }


}
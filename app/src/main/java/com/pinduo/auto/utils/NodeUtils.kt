package com.pinduo.auto.utils

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.text.TextUtils
import android.util.Pair
import android.view.accessibility.AccessibilityNodeInfo
import cn.vove7.andro_accessibility_api.api.withText
import com.blankj.utilcode.util.ScreenUtils
import com.pinduo.auto.app.MyApplication
import kotlin.random.Random

class NodeUtils {



    companion object {

        fun onClickById(service: AccessibilityService,id:String):Boolean{
            val rootNode = service.rootInActiveWindow
            if(rootNode == null){
                return false
            }else{
                val nodes = rootNode.findAccessibilityNodeInfosByViewId(id)
                if(nodes.isNullOrEmpty()){
                    return false
                }else{
                    rootNode.recycle()
                    for (i in nodes.indices.reversed()) {
                        if (onClickByNode(nodes[i])) return true
                    }
                }
            }
            return false
        }

        fun onClickByNode(node: AccessibilityNodeInfo?): Boolean {
            node?.let {
                if (it.isClickable) {
                    return it.performAction(AccessibilityNodeInfo.ACTION_CLICK)
                } else {
                    val parent = it.parent
                    val b = onClickByNode(parent)
                    parent?.recycle()
                    return b
                }
            }
            return false
        }


        fun onClickTextByNode(node: AccessibilityNodeInfo?) {
            node?.let {
                for (i in 0 until it.childCount) {
                    if (TextUtils.equals(
                            "android.widget.TextView", it.getChild(i).className) && !TextUtils.isEmpty(it.getChild(i).text)) {
                        onClickByNode(it.getChild(i))
                    } else {
                        onClickTextByNode(it.getChild(i))
                    }
                }
            }
        }



        fun tryWithText(s: String) {
            try {
                WaitUtil.sleep(1000L)
                val isClick: Boolean = withText(s)?.globalClick()
                MyApplication.instance.getUiHandler().sendMessage("点击--<${s}>--${isClick}")
            } catch (e: Exception) {
                e.printStackTrace()
                LogUtils.logGGQ("tryWithText 异常：${e.fillInStackTrace()}")
                MyApplication.instance.getUiHandler().sendMessage("点击-<${s}>-异常")
            }
        }


        private val duration: Long = 500L
        private val width = ScreenUtils.getScreenWidth() //1080
        private val height = ScreenUtils.getScreenHeight()//1920

        private fun description(path: android.graphics.Path) = GestureDescription.Builder().addStroke(
            GestureDescription.StrokeDescription(
                path,
                0L,
                duration
            )
        ).build()

        fun onSwipe(service: AccessibilityService) {
            val path = getSwipePath(
                width / 2 - Random.nextInt(1, 100), 1500 + Random.nextInt(1, 100),
                Random.nextInt(width / 2, width - 100), height / 2,
                Random.nextInt(50, width / 3), 300 - Random.nextInt(1, 100)
            )
            service.dispatchGesture(
                description(path),
                MyGestureResultCallback(),
                null
            )
        }

        private fun getSwipePath(
            fromX: Int,
            fromY: Int,
            cX: Int,
            cY: Int,
            toX: Int,
            toY: Int
        ): android.graphics.Path {
            val path = android.graphics.Path()
            path.moveTo(fromX.toFloat(), fromY.toFloat()) //滑动起点we
            path.quadTo(cX.toFloat(), cY.toFloat(), toX.toFloat(), toY.toFloat())
            return path
        }

    }
}
class MyGestureResultCallback:AccessibilityService.GestureResultCallback(){
    override fun onCompleted(gestureDescription: GestureDescription?) {
        super.onCompleted(gestureDescription)
    }

    override fun onCancelled(gestureDescription: GestureDescription?) {
        super.onCancelled(gestureDescription)
    }
}
infix fun <A, B> A.t(that: B): Pair<A, B> = Pair(this, that)

package com.pinduo.auto.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbManager
import android.os.BatteryManager
import android.text.TextUtils
import com.blankj.utilcode.util.ScreenUtils
import com.pinduo.auto.app.MyApplication
import com.pinduo.auto.utils.LogUtils
import com.pinduo.auto.utils.TaskUtils


class USBBroadcastReceiver : BroadcastReceiver() {

    companion object {
//        private val ACTION_USB_PERMISSION = "com.demo.otgusb.USB_PERMISSION"

        fun registerReceiver(context: Context, receiver: BroadcastReceiver) {
            val filter = IntentFilter()
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED)
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED)
            filter.addAction(Intent.ACTION_BATTERY_CHANGED)
            filter.addAction(Intent.ACTION_BATTERY_LOW)
            filter.addAction(Intent.ACTION_BATTERY_OKAY)
            filter.addAction(Intent.ACTION_POWER_CONNECTED)
            filter.addAction(Intent.ACTION_POWER_DISCONNECTED)

            /* 注册屏幕唤醒时的广播 */
            filter.addAction(Intent.ACTION_SCREEN_ON)
            /* 注册机器锁屏时的广播 */
            filter.addAction(Intent.ACTION_SCREEN_OFF)

            context.registerReceiver(receiver, filter)


//            val mfilter = IntentFilter(ACTION_USB_PERMISSION)
//            mfilter.addAction("android.hardware.usb.action.USB_STATE")
//            mfilter.addAction("android.hardware.usb.action.USB_DEVICE_ATTACHED")
//            mfilter.addAction("android.hardware.usb.action.USB_DEVICE_DETACHED")
//            context.registerReceiver(receiver, mfilter)
        }

        fun unregisterReceiver(context: Context, receiver: BroadcastReceiver) {
            context.unregisterReceiver(receiver)
        }
    }


    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            val action: String? = it.action
            if (!TextUtils.isEmpty(action)) {
                when (action) {
//                    ACTION_USB_PERMISSION ->{
//                        if(it.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED,false)){
//                            MyApplication.instance.getUiHandler().sendMessage("同意USB设备访问权限")
//                        }else{
//                            MyApplication.instance.getUiHandler().sendMessage("拒绝USB设备访问权限")
//                        }
//                    }
//
//                    UsbManager.ACTION_USB_DEVICE_ATTACHED -> {
//                        //插入USB设备
//                        MyApplication.instance.getUiHandler().sendMessage("插入USB设备")
//                    }
//
//                    UsbManager.ACTION_USB_DEVICE_DETACHED -> {
//                        //拔出USB设备
//                        MyApplication.instance.getUiHandler().sendMessage("拔出USB设备")
//                    }

                    Intent.ACTION_BATTERY_CHANGED -> {
                        val isCharging: Boolean =
                            it.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1) != 0
                        if (isCharging) {
                            //剩余电量
                            val level: Int = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                            //电量最大值
                            val scale: Int = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                            //电量百分比
                            val batteryPct = level / scale
                            MyApplication.instance.getUiHandler()
                                .sendMessage("充电：${level}--${batteryPct}--${scale}")
                        }
                    }

                    Intent.ACTION_BATTERY_LOW -> {
                        //电量过低
                        MyApplication.instance.getUiHandler().sendMessage("电量过低")
                    }

                    Intent.ACTION_BATTERY_OKAY -> {
                        //电量满
                        MyApplication.instance.getUiHandler().sendMessage("电量满")
                    }

                    Intent.ACTION_POWER_CONNECTED -> {
                        //电源接通
                        MyApplication.instance.getUiHandler().sendMessage("电源接通")
                        val x = ScreenUtils.getScreenWidth() / 2
                        val y = ScreenUtils.getScreenHeight() - 100
                    }

                    Intent.ACTION_POWER_DISCONNECTED -> {
                        //电源断开
                        MyApplication.instance.getUiHandler().sendMessage("电源断开")
                    }


                    Intent.ACTION_SCREEN_ON -> {
                        //屏幕唤醒
                        MyApplication.instance.getUiHandler().sendMessage("屏幕已唤醒")
                        LogUtils.logGGQ("屏幕唤醒")
                    }

                    Intent.ACTION_SCREEN_OFF -> {
                        //屏幕锁屏
                        MyApplication.instance.getUiHandler().sendMessage("屏幕已锁屏")
                        LogUtils.logGGQ("屏幕锁屏")
                        TaskUtils.wakeUpAndUnlock()
                    }
                }
            }
        }
    }
}
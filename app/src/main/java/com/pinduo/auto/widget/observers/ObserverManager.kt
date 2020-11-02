package com.pinduo.auto.widget.observers

import com.pinduo.auto.app.global.Constants


class ObserverManager : SubjectListener {

    private val task1List = hashSetOf<ObserverListener>()
    private val task3List = hashSetOf<ObserverListener>()

    companion object {
        val instance: ObserverManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ObserverManager()
        }
    }



    /**
     * 观察者集合
     */
    override fun add(key:String,observerListener: ObserverListener) {
        // 加入队列
        when(key){
            Constants.Task.task1 ->{
                task1List.add(observerListener)
            }

            Constants.Task.task3 ->{
                task3List.add(observerListener)
            }
        }

    }

    override fun notifyObserver(key:String,content: String) {
        // 通知观察者刷新数据
        try {
            when(key){
                Constants.Task.task1 ->{
                    task1List.forEach {
                        it.observer(content)
                    }
                }

                Constants.Task.task3 ->{
                    task3List.forEach {
                        it.observer(content)
                    }
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun remove(key:String) {
        // 从监听队列删除
        when(key){
            Constants.Task.task1 ->{
                task1List.clear()
            }

            Constants.Task.task3 ->{
                task3List.clear()
            }
        }
    }

}
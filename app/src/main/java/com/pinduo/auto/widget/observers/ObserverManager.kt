package com.pinduo.auto.widget.observers


class ObserverManager : SubjectListener {

    companion object {
        val instance: ObserverManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ObserverManager()
        }
    }


    /**
     * 观察者集合
     */
    private val list = mutableListOf<ObserverListener>()

    override fun add(observerListener: ObserverListener) {
        // 加入队列
        list.add(observerListener)
    }

    override fun notifyObserver(content: String) {
        // 通知观察者刷新数据
        try {
            list.forEach {
                it.observer(content)
            }
        }catch (e:ConcurrentModificationException){
            e.printStackTrace()
        }
    }

    override fun remove() {
        // 从监听队列删除
//        map.remove(key)
        list.clear()
    }

}
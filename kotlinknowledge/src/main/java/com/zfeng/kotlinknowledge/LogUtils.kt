package com.zfeng.kotlinknowledge

import android.util.Log

class LogUtils private constructor() {
    /**
     *  LazyThreadSafetyMode.SYNCHRONIZED -> SynchronizedLazyImpl(initializer)
     *  LazyThreadSafetyMode.PUBLICATION -> SafePublicationLazyImpl(initializer)
     *  LazyThreadSafetyMode.NONE -> UnsafeLazyImpl(initializer)
     */
    companion object {
        val INSTANCE by lazy {
            Log.e("LogUtils", "${Thread.currentThread().name}：lazy init")
            LogUtils()
        }
        val INSTANCE2 by lazy(LazyThreadSafetyMode.PUBLICATION) {
            Log.e("LogUtils", "${Thread.currentThread().name}：lazy init")
            LogUtils()
        }
    }

    var count = 0
    var initTag: String? = null
        set(value) {
            if (field == null) {
                field = value
            }
        }

    fun log(tag: String = "LogUtils", msg: String) {
        Log.e("$tag $initTag", msg)
    }

}
package com.zfeng.kotlinknowledge

import android.view.View

class InlineExample {

    fun main1() {
        high1({ var0, var1 ->
            var0 + var1
        }, { var0, var1 ->
            var0 + var1
        })
    }

    fun main2() {
        high2({ var0, var1 ->
            var0 + var1
        }, { var0, var1 ->
            var0 + var1
        })
    }

    fun main3() {
        high3({ var0, var1 ->
            var0 + var1
        }, { var0, var1 ->
            var0 + var1
        })
    }

    fun main5() {
        high5 {
            LogUtils.INSTANCE.log(msg = "hello world!")
        }
    }

    inline fun high1(block: (Int, Int) -> Int, block2: (Int, Int) -> Int) {
        block.invoke(5, 6)
        block2.invoke(4, 5)
    }

    fun high2(block: (Int, Int) -> Int, block2: (Int, Int) -> Int) {
        block.invoke(5, 6)
        block2.invoke(4, 5)
    }

    inline fun high3(block: (Int, Int) -> Int, noinline block2: (Int, Int) -> Int) {
        block.invoke(5, 6)
        block2.invoke(4, 5)
    }

    inline fun high4(block: (Int) -> Unit) {
        val a = Runnable {
//            block.invoke(2)
        }
    }

    inline fun high5(crossinline block: (Int) -> Unit) {
        val a = Runnable {
            block.invoke(2)
        }

    }

    fun postDelay(view: View, block: () -> Unit) {
        view.postDelayed(block, 5000)
        view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
            override fun onViewDetachedFromWindow(v: View?) {
                view.removeCallbacks(block)
                view.removeOnAttachStateChangeListener(this)
            }

            override fun onViewAttachedToWindow(v: View?) {

            }
        })

    }


}
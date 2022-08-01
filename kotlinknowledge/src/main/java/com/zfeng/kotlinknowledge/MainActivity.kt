package com.zfeng.kotlinknowledge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        KotlinUtils.runPlus()
//        KotlinUtils.runMap()
//        KotlinUtils.runStandard()
        KotlinUtils.runInfix()

        InnerExample.Shanghai().population
        InnerExample().Hangzhou().population

        thread {
//            LogUtils.INSTANCE.log(msg = "${LogUtils.INSTANCE.count++}")
            LogUtils.INSTANCE2.initTag = "thread"
            LogUtils.INSTANCE2.log(msg = "${Thread.currentThread().name}：${LogUtils.INSTANCE2.count++}")
            LogUtils.INSTANCE2.log(msg = "${Thread.currentThread().name}：${LogUtils.INSTANCE2.count++}")
        }
//        LogUtils.INSTANCE.log(msg = "${LogUtils.INSTANCE.count++}")
        LogUtils.INSTANCE2.initTag = "main"
        LogUtils.INSTANCE2.log(msg = "${Thread.currentThread().name}：${LogUtils.INSTANCE2.count++}")
        LogUtils.INSTANCE2.log(msg = "${Thread.currentThread().name}：${LogUtils.INSTANCE2.count++}")

        thread {
            Thread.sleep(1000)
            LogUtils.INSTANCE2.log(msg = "${Thread.currentThread().name}：${LogUtils.INSTANCE2.count++}")
        }

//        InlineExample().postDelay(textView) {
//            LogUtils.INSTANCE.log(msg = "delay")
//        }


    }

    fun main() {
//        println(apply(::foo))

//        foo { returnsInt() } // this was the only way to do it  before 1.4
//        foo(::returnsInt) // starting from 1.4, this also works

//        use0(::foo)
//        use1(::foo)
//        use2(::foo)

        takeSuspend { call() } // OK before 1.4
//        takeSuspend(::call) // In Kotlin 1.4, it also works
    }

    fun call() {}
    fun takeSuspend(f: suspend () -> Unit) {}

    // ================================================================

    fun foo(x: Int, vararg y: String) {}

    fun use0(f: (Int) -> Unit) {
        f.invoke(1)
    }

    fun use1(f: (Int, String) -> Unit) {
        f.invoke(1, "aaa")
    }

    fun use2(f: (Int, String, String) -> Unit) {}

// ================================================================

    fun foo(f: () -> Unit) {}
    fun returnsInt(): Int = 42

    // ================================================================
    fun foo(i: Int = 0): String = "$i!"

    fun apply(func: () -> String): String = func()

    fun apply(func: (Int) -> String): String = func(0)
}

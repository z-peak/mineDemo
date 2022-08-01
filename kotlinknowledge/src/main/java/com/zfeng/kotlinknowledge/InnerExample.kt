package com.zfeng.kotlinknowledge

import android.util.Log

class InnerExample {
    private val TAG = "InnerExample"

    class Shanghai {
        val population: Int = 1300000

        fun log() {
//            Log.e(TAG)
        }
    }

    inner class Hangzhou {
        val population: Int = 1100000
        fun log() {
            Log.e(TAG, "")
        }
    }
}
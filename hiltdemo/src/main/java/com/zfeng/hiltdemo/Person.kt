package com.zfeng.hiltdemo

import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class Person constructor(var name: String, var sex: String) {

    @Inject
    constructor() : this("张三", "男")

    fun showPersonInfo() {
        Log.e("============", "===>我是的名字叫：${name}，性别：${sex}")
    }
}
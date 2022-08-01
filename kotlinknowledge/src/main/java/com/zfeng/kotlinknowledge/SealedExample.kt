package com.zfeng.kotlinknowledge

import java.lang.Exception

class SealedExample {
//
//    fun getResult(result: Result): = when (result) {
//        is Success -> result.msg
//        is Fail -> result.error.message
//        else -> throw IllegalArgumentException()
//    }
}

//class Success(val msg: String) : kotlin.Result
//
//class Fail(val error: Throwable) : kotlin.Result
//
//class Result() {
//    var msg: String = ""
//    var error: Throwable = Throwable()
//}
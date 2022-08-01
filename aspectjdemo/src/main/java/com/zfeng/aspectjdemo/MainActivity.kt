package com.zfeng.aspectjdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        Log.e("MainActivity", "------ onResume ------")
    }

    @Before(POINTCUT_ONMETHOD)
    fun beforeOnMethod(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature
        val classname = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name
        Log.e("MainActivity", "before ------ $methodName ------$classname")
    }

    @After(POINTCUT_ONMETHOD)
    fun afterOnMethod(joinPoint: JoinPoint) {
        val methodSignature = joinPoint.signature
        val classname = methodSignature.declaringType.simpleName
        val methodName = methodSignature.name
        Log.e("MainActivity", "after ------ $methodName ------$classname")
    }

    companion object{
        const val POINTCUT_ONMETHOD = "execution(* android.app.Activity.on**(...))"//execution(* android.app.Activity.on**(..))
    }
}
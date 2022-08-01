package com.zfeng.hiltdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// 为了性能，加一个注解提示
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var person: Person

    @Inject
    lateinit var person2: Person

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        person.showPersonInfo()
//        person.name = "李四"
//        person.showPersonInfo()
//        person2.showPersonInfo()
//
//        val person3 = Person()
//        person3.showPersonInfo()
    }
}

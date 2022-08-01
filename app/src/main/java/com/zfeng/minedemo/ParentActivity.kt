package com.zfeng.minedemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parent)

        findViewById<TextView>(R.id.tvJumpOtherActivity).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
}
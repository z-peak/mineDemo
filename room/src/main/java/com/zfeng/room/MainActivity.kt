package com.zfeng.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.room.Room
import com.zfeng.room.data.AppDatabase
import com.zfeng.room.data.User
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun insert(view: View) {
        thread {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).build()
            db.userDao().insertAll(
                User(1000, "周", "晓阳"),
                User(1001, "曹", "伟"),
                User(1002, "李", "洋")
            )
        }
    }

    fun appDatabase(view: View) {

        runBlocking {
            launch(Dispatchers.IO){
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
                ).build()
                val users = db.userDao().getAll()
                Log.e("AppDatabase", "${users?.size}")
                for (user in users) {
                    Log.e("AppDatabase", "${user.firstName} ${user.lastName}")
                }

                db.userDao().deleteAll(users)

                val users1 = db.userDao().getAll()
                Log.e("AppDatabase", "${users1?.size}")
                for (user in users1) {
                    Log.e("AppDatabase", "${user.firstName} ${user.lastName}")
                }
            }
        }


    }



}

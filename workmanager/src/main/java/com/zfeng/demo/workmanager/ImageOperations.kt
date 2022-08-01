package com.zfeng.demo.workmanager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

class ImageOperations(context: Context) {


    private val constraints = Constraints.Builder()
        .setRequiresCharging(true)// 设备处于充电
        .setRequiredNetworkType(NetworkType.CONNECTED)// 网络已连接
        .setRequiresBatteryNotLow(true)// 电池电量充足
        .build()

    /**
     * WorkRequest（及其子类）则定义工作运行方式和时间。
     * WorkRequest是一个抽象类，它有两种实现，OneTimeWorkRequest和PeriodicWorkRequest，分别对应的是一次性任务和周期性任务。
     */
    private val uploadWorkRequest: WorkRequest = OneTimeWorkRequestBuilder<UploadWorker>()
        .setConstraints(constraints)
        .setInitialDelay(10, TimeUnit.SECONDS)// 延迟10秒执行
        .setBackoffCriteria(
            BackoffPolicy.LINEAR,
            OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
            TimeUnit.MILLISECONDS
        )// 设置指数退避算法
        .addTag("testUploadTag")
        .build()

    init {
        // 将任务提交给系统。WorkManager.enqueue()方法会将你配置好的WorkRequest交给系统来执行。
        val enqueue = WorkManager.getInstance(context).enqueue(uploadWorkRequest)

        WorkManager.getInstance(context).getWorkInfoByIdLiveData(uploadWorkRequest.id)
            .observeForever {

            }
    }
}
package com.zfeng.demo.workmanager

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {

    /**
     * Result.success()：工作成功完成。
     * Result.failure()：工作失败。
     * Result.retry()：工作失败，应根据其重试政策在其他时间尝试。
     * @return 返回的 Result 会通知 WorkManager 服务工作是否成功，以及工作失败时是否应重试工作。
     */
    override fun doWork(): Result {
        uploadImage()
        return Result.success()
    }

    private fun uploadImage() {
        Thread.sleep(1000)
    }

    private fun toastAndLog(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
        Log.e(TAG, message)
    }

    companion object {
        private const val TAG = "UploadWorker"
    }

}
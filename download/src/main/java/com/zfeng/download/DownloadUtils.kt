package com.zfeng.download

import android.content.Context
import java.io.File

class DownloadUtils {

    fun download(
        context: Context,
        downloadUrl: String,
        filePath: String,
        downloadListener: DownloadListener
    ) {

        val dFile = File(filePath)
        val configFile = File(context.filesDir.path + "/temp/" + dFile.name + ".properties")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }

        Thread(Runnable {


        }).start()

    }
}
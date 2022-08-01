package com.zfeng.download

import java.io.RandomAccessFile
import java.net.HttpURLConnection
import java.net.URL

class DownloadTask(val downloadEntity: DownloadEntity) : Runnable {

    private val TIME_OUT = 10000
    private var isCancel = false
    private var isStop = false

    override fun run() {
        val url = URL(downloadEntity.downloadUrl)
        val conn = url.openConnection() as HttpURLConnection
        // 设置在请求头里的下载开始和结束位置
        conn.setRequestProperty(
            "Range",
            "bytes=" + downloadEntity.startLocation + "-" + downloadEntity.endLocation
        )
        conn.requestMethod = "GET"
        conn.setRequestProperty("Charset", "UTF-8")
        conn.connectTimeout = TIME_OUT
        conn.setRequestProperty(
            "User-Agent",
            "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"
        )
        conn.setRequestProperty(
            "Accept",
            "image/gif, image/jpeg, image/pjpeg, image/pjpeg, application/x-shockwave-flash, application/xaml+xml, application/vnd.ms-xpsdocument, application/x-ms-xbap, application/x-ms-application, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, */*"
        )
        conn.readTimeout = 2000  //设置读取流的等待时间,必须设置该参数

        val inputStream = conn.inputStream
        //创建可设置位置的文件
        val file = RandomAccessFile(downloadEntity.tempFile, "rwd")
        //设置每条线程写入文件的位置
        file.seek(downloadEntity.startLocation)
        val buffer = ByteArray(1024)
        var len = 0
        var currentLocation = downloadEntity.startLocation
        while (len != -1) {

            if (isCancel) {

                break
            }

            if (isStop) {
                break
            }

            file.write(buffer, 0, len)
            synchronized(this) {

            }
            currentLocation += len
            len = inputStream.read(buffer)
        }

        file.close()
        inputStream.close()

        if (isCancel) {
            synchronized(this) {

            }

            return
        }

        if (isStop) {
            synchronized(this) {

            }
            return
        }



    }

}
package com.zfeng.download

import java.net.HttpURLConnection

interface IDownloadListener {

    /**
     * 开始
     */
    fun onStart()

    /**
     * 重新开始
     */
    fun onResume()

    /**
     * 停止
     */
    fun onStop()

    /**
     * 下载完成
     */
    fun onComplete()

    /**
     * 下载进度
     */
    fun onProgress(currentLocation: Long)

    /**
     * 下载失败
     */
    fun onFail(e: Throwable)

    /**
     * 取消下载
     */
    fun onCancel()

    /**
     * 预处理
     */
    fun onPreDownload(connection: HttpURLConnection)

    /**
     * 单一线程恢复
     */
    fun onChildResume()

    /**
     * 单一线程下载完成
     */
    fun onChildComplete()

}
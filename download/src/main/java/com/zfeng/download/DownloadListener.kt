package com.zfeng.download

import java.net.HttpURLConnection

class DownloadListener : IDownloadListener {
    override fun onStart() {
    }

    override fun onResume() {
    }

    override fun onStop() {
    }

    override fun onComplete() {
    }

    override fun onProgress(currentLocation: Long) {

    }

    override fun onFail(e: Throwable) {

    }

    override fun onCancel() {

    }

    override fun onPreDownload(connection: HttpURLConnection) {

    }

    override fun onChildResume() {

    }

    override fun onChildComplete() {

    }

}
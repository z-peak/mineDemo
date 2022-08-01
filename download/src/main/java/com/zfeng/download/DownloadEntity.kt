package com.zfeng.download

import android.content.Context
import java.io.File

/**
 * 子线程下载信息类
 */
class DownloadEntity(
    var context: Context?,
    var fileSize: Long?,
    var downloadUrl: String?,
    var threadId: Int?,
    var startLocation: Long = 0L,
    var endLocation: Long = 0L,
    var tempFile: File?
) {

}
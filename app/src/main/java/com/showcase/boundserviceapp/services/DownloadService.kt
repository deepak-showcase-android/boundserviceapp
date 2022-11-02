package com.showcase.boundserviceapp.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.Random

class DownloadService: Service() {

    private val binder = LocalBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun startDownload(callBack: IProgressProvider) {
        callBack.onStartedDownload()
        for(progress: Int in 0..100) {
            callBack.onDownloadProgress(progress)
            Thread.sleep(100)
            if (progress == 100) {
                callBack.onDownloadCompleted()
            }
        }
    }

    inner class LocalBinder: Binder() {
        fun getService(): DownloadService = this@DownloadService
    }
}

interface IProgressProvider {
    fun onStartedDownload()
    fun onDownloadProgress(value: Int)
    fun onDownloadCompleted()
}
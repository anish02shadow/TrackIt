package com.example69.projectx

import android.app.Application
import com.example69.projectx.data.AppContainer
import com.example69.projectx.data.AppDataContainer

class TransactionApplicatiom: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }

}

package com.creator.exception

import android.content.Context

//定义CrashHandler
class CrashHandler private constructor(): Thread.UncaughtExceptionHandler {
    private var context: Context? = null
    fun init(context: Context?) {
        this.context = context
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    override fun uncaughtException(t: Thread, e: Throwable) {}

    companion object {
        val instance: CrashHandler by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            CrashHandler() }
    }
}
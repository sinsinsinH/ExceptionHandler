package com.creator.exception

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.creator.exception.support.CrashLog
import com.creator.exception.support.DebugSafeModeTipActivity
import com.creator.exception.support.DebugSafeModeUI
import com.wanjian.cockroach.Cockroach
import com.wanjian.cockroach.ExceptionHandler

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        install(this)
    }

    private fun install(context: Context) {
        Cockroach.install(context, object : ExceptionHandler() {
            override fun onUncaughtExceptionHappened(thread: Thread, throwable: Throwable?) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", throwable)
                CrashLog.saveCrashLog(applicationContext, throwable)
//                Handler(Looper.getMainLooper()).post {
//                    toast.setText(R.string.safe_mode_excep_tips)
//                    toast.show()
//                }
            }

            override fun onBandageExceptionHappened(throwable: Throwable) {
                throwable.printStackTrace() //打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
//                toast.setText("Cockroach Worked")
//                toast.show()
            }

            override fun onEnterSafeMode() {
                Toast.makeText(context, "已经进入安全模式", Toast.LENGTH_LONG).show()
                DebugSafeModeUI.showSafeModeUI()
                if (BuildConfig.DEBUG) {
                    val intent = Intent(context, DebugSafeModeTipActivity::class.java)
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }

            override fun onMayBeBlackScreen(e: Throwable?) {
                val thread = Looper.getMainLooper().thread
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", e)
                //黑屏时建议直接杀死app
            }

        })

    }
}
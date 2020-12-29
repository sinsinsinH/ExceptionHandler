package com.creator.exception

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.creator.exception.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener {

    var i = 0

    private val TAG = "MainActivity"
    private var mainBinding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding!!.root)
        mainBinding!!.onClickListener = this
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                Log.d(TAG, "handleMessage: " + i++)
                sendEmptyMessageDelayed(100, 1000)

            }
        }
        handler.sendEmptyMessage(100)
    }

    override fun onClick(v: View?) {
        if (v!!.id == R.id.bt_one) {
            throw  RuntimeException("主线程异常")
        } else if (v!!.id == R.id.bt_two) {
            thread {
                throw  RuntimeException("子线程异常")
            }
        }
    }
}

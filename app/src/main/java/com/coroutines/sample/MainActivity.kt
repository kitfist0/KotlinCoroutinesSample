package com.coroutines.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val LOG_TAG = "MainActivity"
        val resource = NetworkResource()
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    private val coinAdapter by lazy {
        CoinAdapter { Toast.makeText(this, it.name, Toast.LENGTH_SHORT).show() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = coinAdapter

        scope.launch {
            resource.refreshListOfCoins(
                    onSuccess = {
                        Log.d(LOG_TAG, it.toString())
                        coinAdapter.submitList(it)
                    },
                    onError = {
                        Log.e(LOG_TAG, "ERROR: ${it.message}")
                    }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.coroutineContext.cancelChildren()
    }
}

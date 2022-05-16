package cn.hx.activityresultapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.hx.activityresultapi.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartActivity.setOnClickListener {
            Log.d(TAG, "start with $this")
            startActivityForResult(Intent(this, SecondActivity::class.java)) { resultCode, data, caller ->
                (caller as? MainActivity)?.receiveResult(resultCode, data)
            }
        }
    }

    private fun receiveResult(resultCode: Int, data: Intent?) {
        Log.d(TAG, "receiveResult() called with: resultCode = $resultCode, data = $data")
    }
}
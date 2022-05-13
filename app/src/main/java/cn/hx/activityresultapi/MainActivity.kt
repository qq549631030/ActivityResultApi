package cn.hx.activityresultapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import cn.hx.activityresultapi.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartActivity.setOnClickListener {
            Log.d(TAG, "start with $this")
            startActivityForResult(Intent(this, SecondActivity::class.java)) { caller, result ->
                (caller as? MainActivity)?.receiveResult(result)
            }
        }
    }

    private fun receiveResult(activityResult: ActivityResult) {
        Log.d(TAG, "receiveResult() called with: this = $this, activityResult = $activityResult")
    }
}
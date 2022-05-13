package cn.hx.activityresultapi

import android.app.Activity
import android.os.Bundle
import cn.hx.activityresultapi.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity() {
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSuccess.setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}
package cn.hx.activityresultapi

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.FileProvider
import cn.hx.activityresultapi.databinding.ActivityMainBinding
import cn.hx.ara.VideoConfig
import java.io.File

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnStartActivity.setOnClickListener {
            startActivityForResult(Intent(this, SecondActivity::class.java)) {
                (it.caller as? MainActivity)?.receiveResult(it.resultCode, it.data)
            }
        }
        binding.btnTakePicture.setOnClickListener {
            val file = File(externalCacheDir, "${System.currentTimeMillis()}.jpg")
            val outputUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, "$packageName.provider", file)
            } else {
                Uri.fromFile(file)
            }
            takePicture(outputUri) {
                (it.caller as? MainActivity)?.takePictureResult(it.success, it.outputUri)
            }
        }
        binding.btnTakeVideo.setOnClickListener {
            val file = File(externalCacheDir, "${System.currentTimeMillis()}.mp4")
            val outputUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, "$packageName.provider", file)
            } else {
                Uri.fromFile(file)
            }
            takeVideo(outputUri) {
                (it.caller as? MainActivity)?.takeVideoResult(it.success, it.outputUri)
            }
        }
        binding.btnTakeVideoConfig.setOnClickListener {
            val file = File(externalCacheDir, "${System.currentTimeMillis()}.mp4")
            val outputUri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                FileProvider.getUriForFile(this, "$packageName.provider", file)
            } else {
                Uri.fromFile(file)
            }
            takeVideo(VideoConfig(outputUri, 1, 10, 10 * 1024 * 1024L)) {
                (it.caller as? MainActivity)?.takeVideoResult(it.success, it.outputUri)
            }
        }
    }

    private fun receiveResult(resultCode: Int, data: Intent?) {
        Log.d(TAG, "receiveResult() called with: resultCode = $resultCode, data = $data")
    }

    private fun takePictureResult(success: Boolean, outputUri: Uri) {
        Log.d(TAG, "takePictureResult() called with: success = $success, outputUri = $outputUri")
    }

    private fun takeVideoResult(success: Boolean, outputUri: Uri) {
        Log.d(TAG, "takeVideoResult() called with: success = $success, outputUri = $outputUri")
    }
}
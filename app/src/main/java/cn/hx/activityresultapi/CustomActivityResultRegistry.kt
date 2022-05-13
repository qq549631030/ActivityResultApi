package cn.hx.activityresultapi

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.core.app.ActivityOptionsCompat

class CustomActivityResultRegistry : ActivityResultRegistry() {
    private var lastRequestCode = -1

    override fun <I : Any?, O : Any?> onLaunch(requestCode: Int, contract: ActivityResultContract<I, O>, input: I, options: ActivityOptionsCompat?) {
        lastRequestCode = requestCode
    }

    fun dispatchForLastRequest(resultCode: Int, data: Intent? = null) {
        if (lastRequestCode != -1) {
            dispatchResult(lastRequestCode, resultCode, data)
            lastRequestCode = -1
        }
    }

    fun onCustomSaveInstanceState(outState: Bundle) {
        onSaveInstanceState(outState)
        if (lastRequestCode != -1) {
            outState.putInt(LAST_REQUEST_CODE, lastRequestCode)
        }
    }

    fun onCustomRestoreInstanceState(savedInstanceState: Bundle?) {
        onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.run {
            lastRequestCode = getInt(LAST_REQUEST_CODE, -1)
        }
    }

    companion object {
        const val LAST_REQUEST_CODE = "cn.hx.base.lastRequestCode"
    }
}
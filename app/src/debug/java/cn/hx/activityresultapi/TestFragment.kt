package cn.hx.activityresultapi

import android.os.Bundle
import androidx.activity.result.ActivityResultRegistry

class TestFragment : BaseFragment() {

    val registry = CustomActivityResultRegistry()

    override fun customRegistry(): ActivityResultRegistry? {
        return registry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registry.onCustomRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        registry.onCustomSaveInstanceState(outState)
    }
}
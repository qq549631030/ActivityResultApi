package cn.hx.activityresultapi

import android.os.Bundle
import androidx.activity.result.ActivityResultRegistry

class TestActivity : BaseActivity() {

    val registry = CustomActivityResultRegistry()

    override fun customRegistry(): ActivityResultRegistry? {
        return registry
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registry.onCustomRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.activity_test)
        savedInstanceState
                ?: supportFragmentManager.beginTransaction().add(R.id.container, TestFragment()).commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        registry.onCustomSaveInstanceState(outState)
    }
}
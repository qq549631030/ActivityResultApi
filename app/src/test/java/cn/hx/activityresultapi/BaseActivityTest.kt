package cn.hx.activityresultapi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class BaseActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TestActivity::class.java)

    @Test
    fun startActivityForResult() {
        var resultCode: Int = -2
        activityRule.scenario.onActivity {
            it.startActivityForResult(Intent(it, SecondActivity::class.java)) {
                resultCode = it.resultCode
            }
            assert(resultCode == -2)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(resultCode == Activity.RESULT_OK)
        }
    }

    @Test
    fun startActivityForResult_recreate() {
        var activityHash = 0
        var resultCode: Int = -2
        activityRule.scenario.onActivity {
            activityHash = it.hashCode()
            it.startActivityForResult(Intent(it, SecondActivity::class.java)) {
                activityHash = it.caller.hashCode()
                resultCode = it.resultCode
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            assert(it.hashCode() != activityHash)
            assert(resultCode == -2)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(it.hashCode() == activityHash)
            assert(resultCode == Activity.RESULT_OK)
        }
    }

    @Test
    fun takePicture() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            it.takePicture(outputUri) {
                result = it.success
            }
            assert(result == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(result == true)
        }
    }

    @Test
    fun takePicture_recreate() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var activityHash = 0
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            activityHash = it.hashCode()
            it.takePicture(outputUri) {
                activityHash = it.caller.hashCode()
                result = it.success
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            assert(it.hashCode() != activityHash)
            assert(result == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_CANCELED)
            assert(it.hashCode() == activityHash)
            assert(result == false)
        }
    }


    @Test
    fun takeVideo() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            it.takeVideo(outputUri) {
                result = it.success
            }
            assert(result == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK, null)
            assert(result != null)
        }
    }

    @Test
    fun takeVideo_recreate() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var activityHash = 0
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            activityHash = it.hashCode()
            it.takeVideo(outputUri) {
                activityHash = it.caller.hashCode()
                result = it.success
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            assert(it.hashCode() != activityHash)
            assert(result == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_CANCELED, null)
            assert(it.hashCode() == activityHash)
            assert(result == false)
        }
    }

    @Test
    fun requestPermission() {
        var grantState: Boolean? = null

        activityRule.scenario.onActivity {
            it.requestPermission(Manifest.permission.CAMERA) { result ->
                grantState = result.grantState
            }
            assert(grantState == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK, Intent().apply {
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(Manifest.permission.CAMERA))
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS, intArrayOf(PackageManager.PERMISSION_GRANTED))
            })
            assert(grantState == true)
        }
    }

    @Test
    fun requestPermission_recreate() {
        var grantState: Boolean? = null
        activityRule.scenario.onActivity {
            it.requestPermission(Manifest.permission.CAMERA) { result ->
                grantState = result.grantState
            }
            assert(grantState == null)
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            assert(grantState == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK, Intent().apply {
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(Manifest.permission.CAMERA))
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS, intArrayOf(PackageManager.PERMISSION_DENIED))
            })
            assert(grantState == false)
        }
    }

    @Test
    fun requestMultiplePermissions() {
        var grantState: Map<String, Boolean>? = null
        activityRule.scenario.onActivity {
            it.requestMultiplePermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)) { result ->
                grantState = result.grantState
            }
            assert(grantState == null)
            it.registry.dispatchForLastRequest(Activity.RESULT_OK, Intent().apply {
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS, intArrayOf(PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED))
            })
            assert(grantState != null)
            assert(grantState?.get(Manifest.permission.CAMERA) == true)
            assert(grantState?.get(Manifest.permission.RECORD_AUDIO) == true)
        }
    }

    @Test
    fun requestMultiplePermissions_recreate() {
        var grantState: Map<String, Boolean>? = null
        activityRule.scenario.onActivity {
            it.requestMultiplePermissions(arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)) { result ->
                grantState = result.grantState
            }
            assert(grantState == null)
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            it.registry.dispatchForLastRequest(Activity.RESULT_OK, Intent().apply {
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSIONS, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO))
                putExtra(ActivityResultContracts.RequestMultiplePermissions.EXTRA_PERMISSION_GRANT_RESULTS, intArrayOf(PackageManager.PERMISSION_DENIED, PackageManager.PERMISSION_GRANTED))
            })
            assert(grantState != null)
            assert(grantState?.get(Manifest.permission.CAMERA) == false)
            assert(grantState?.get(Manifest.permission.RECORD_AUDIO) == true)
        }
    }
}
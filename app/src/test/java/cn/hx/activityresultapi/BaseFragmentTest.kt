package cn.hx.activityresultapi

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class BaseFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(TestActivity::class.java)

    @Test
    fun startActivityForResult() {
        var resultCode: Int = -2
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragment.startActivityForResult(Intent(it, SecondActivity::class.java)) {
                resultCode = it.resultCode
            }
            assert(resultCode == -2)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(resultCode == Activity.RESULT_OK)
        }
    }

    @Test
    fun startActivityForResult_recreate() {
        var fragmentHash = 0
        var resultCode: Int = -2
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragmentHash = fragment.hashCode()
            fragment.startActivityForResult(Intent(it, SecondActivity::class.java)) {
                fragmentHash = it.caller.hashCode()
                resultCode = it.resultCode
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            assert(fragment.hashCode() != fragmentHash)
            assert(resultCode == -2)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(fragment.hashCode() == fragmentHash)
            assert(resultCode == Activity.RESULT_OK)
        }
    }

    @Test
    fun takePicture() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragment.takePicture(outputUri) {
                result = it.success
            }
            assert(result == null)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(result == true)
        }
    }

    @Test
    fun takePicture_recreate() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var fragmentHash = 0
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragmentHash = fragment.hashCode()
            fragment.takePicture(outputUri) {
                fragmentHash = it.caller.hashCode()
                result = it.success
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            assert(fragment.hashCode() != fragmentHash)
            assert(result == null)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_OK)
            assert(fragment.hashCode() == fragmentHash)
            assert(result == true)
        }
    }

    @Test
    fun takeVideo() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragment.takeVideo(outputUri) {
                result = it.success
            }
            assert(result == null)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_OK, null)
            assert(result == true)
        }
    }

    @Test
    fun takeVideo_recreate() {
        val outputUri = Uri.parse(File(ApplicationProvider.getApplicationContext<Context>().cacheDir, "test.jpg").absolutePath)
        var fragmentHash = 0
        var result: Boolean? = null
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            fragmentHash = fragment.hashCode()
            fragment.takeVideo(outputUri) {
                fragmentHash = it.caller.hashCode()
                result = it.success
            }
        }
        activityRule.scenario.recreate()
        activityRule.scenario.onActivity {
            val fragment = it.supportFragmentManager.findFragmentById(R.id.container) as TestFragment
            assert(fragment.hashCode() != fragmentHash)
            assert(result == null)
            fragment.registry.dispatchForLastRequest(Activity.RESULT_CANCELED, null)
            assert(fragment.hashCode() == fragmentHash)
            assert(result == false)
        }
    }
}
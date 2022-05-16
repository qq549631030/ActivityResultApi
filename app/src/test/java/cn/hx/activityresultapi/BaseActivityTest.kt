package cn.hx.activityresultapi

import android.app.Activity
import android.content.Intent
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

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
        var activityHash: Int = 0
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
}
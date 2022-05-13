package cn.hx.activityresultapi

import androidx.appcompat.app.AppCompatActivity
import cn.hx.ara.ActivityResultSource
import cn.hx.ara.ActivityResultSourceImpl

open class BaseActivity : AppCompatActivity(), ActivityResultSource by ActivityResultSourceImpl() {

    val TAG = this::class.java.simpleName

}
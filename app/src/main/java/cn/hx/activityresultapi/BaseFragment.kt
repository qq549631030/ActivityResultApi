package cn.hx.activityresultapi

import androidx.fragment.app.Fragment
import cn.hx.ara.ActivityResultSource
import cn.hx.ara.ActivityResultSourceImpl

open class BaseFragment : Fragment(), ActivityResultSource by ActivityResultSourceImpl() {

    val TAG = this::class.java.simpleName
}
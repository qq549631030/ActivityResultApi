package cn.hx.ara;

import android.annotation.SuppressLint;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;

public interface ActivityResultCallback<O> {
    void onActivityResult(@NonNull ActivityResultCaller activityResultCaller, @SuppressLint("UnknownNullness") O result);
}

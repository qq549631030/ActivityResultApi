package cn.hx.ara;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ActivityResultCallback {
    void onActivityResult(int resultCode, @Nullable Intent data, @NonNull ActivityResultSource activityResultSource);
}

package cn.hx.ara;

import androidx.annotation.NonNull;

public interface RequestPermissionCallback {
    void onRequestPermissionResult(@NonNull RequestPermissionResultInfo resultInfo);
}

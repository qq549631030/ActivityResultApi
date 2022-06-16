package cn.hx.ara;

import androidx.annotation.NonNull;

public class RequestPermissionInfo {

    @NonNull
    final String permission;

    @NonNull
    final RequestPermissionCallback callback;

    public RequestPermissionInfo(@NonNull String permission, @NonNull RequestPermissionCallback callback) {
        this.permission = permission;
        this.callback = callback;
    }
}

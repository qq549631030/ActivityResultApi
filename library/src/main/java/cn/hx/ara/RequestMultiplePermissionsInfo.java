package cn.hx.ara;

import androidx.annotation.NonNull;

public class RequestMultiplePermissionsInfo {

    @NonNull
    final String[] permissions;

    @NonNull
    final RequestMultiplePermissionsCallback callback;

    public RequestMultiplePermissionsInfo(@NonNull String[] permissions, @NonNull RequestMultiplePermissionsCallback callback) {
        this.permissions = permissions;
        this.callback = callback;
    }
}

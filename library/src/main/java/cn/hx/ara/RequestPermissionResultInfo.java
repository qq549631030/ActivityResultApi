package cn.hx.ara;

import androidx.annotation.NonNull;

public class RequestPermissionResultInfo extends ActivityResultInfo {

    @NonNull
    public final String permission;

    @NonNull
    public final Boolean grantState;

    @NonNull
    public final Boolean shouldRationale;

    public RequestPermissionResultInfo(@NonNull String sourceUuid, @NonNull String permission, @NonNull Boolean grantState, @NonNull Boolean shouldRationale) {
        super(sourceUuid);
        this.permission = permission;
        this.grantState = grantState;
        this.shouldRationale = shouldRationale;
    }
}

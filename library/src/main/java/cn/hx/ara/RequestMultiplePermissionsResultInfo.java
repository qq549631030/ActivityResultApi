package cn.hx.ara;

import androidx.annotation.NonNull;

import java.util.Map;

public class RequestMultiplePermissionsResultInfo extends ActivityResultInfo {

    @NonNull
    public final String[] permissions;
    @NonNull
    public final Map<String, Boolean> grantState;
    @NonNull
    public final Map<String, Boolean> shouldRationale;

    public RequestMultiplePermissionsResultInfo(@NonNull String sourceUuid, @NonNull String[] permissions, @NonNull Map<String, Boolean> grantState, @NonNull Map<String, Boolean> shouldRationale) {
        super(sourceUuid);
        this.permissions = permissions;
        this.grantState = grantState;
        this.shouldRationale = shouldRationale;
    }
}

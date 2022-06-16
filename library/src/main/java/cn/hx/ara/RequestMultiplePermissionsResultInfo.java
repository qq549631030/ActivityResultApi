package cn.hx.ara;

import androidx.annotation.NonNull;

import java.util.Map;

public class RequestMultiplePermissionsResultInfo extends ActivityResultInfo {

    @NonNull
    public final String[] permissions;
    @NonNull
    public final Map<String, Boolean> grantState;

    public RequestMultiplePermissionsResultInfo(@NonNull String sourceUuid, @NonNull String[] permissions, @NonNull Map<String, Boolean> grantState) {
        super(sourceUuid);
        this.permissions = permissions;
        this.grantState = grantState;
    }
}

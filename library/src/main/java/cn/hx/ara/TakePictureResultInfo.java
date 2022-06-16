package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;

public class TakePictureResultInfo extends ActivityResultInfo {

    public final boolean success;

    @NonNull
    public final Uri outputUri;

    public TakePictureResultInfo(@NonNull String sourceUuid, boolean success, @NonNull Uri outputUri) {
        super(sourceUuid);
        this.success = success;
        this.outputUri = outputUri;
    }
}
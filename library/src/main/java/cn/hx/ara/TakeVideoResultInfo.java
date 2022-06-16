package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;

public class TakeVideoResultInfo extends ActivityResultInfo {

    public final boolean success;

    @NonNull
    public final Uri outputUri;

    public TakeVideoResultInfo(@NonNull String sourceUuid, boolean success, @NonNull Uri outputUri) {
        super(sourceUuid);
        this.success = success;
        this.outputUri = outputUri;
    }
}
package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TakePictureResultInfo {

    public final boolean success;

    @NonNull
    public final String sourceUuid;

    @NonNull
    public final Uri outputUri;

    public TakePictureResultInfo(boolean success, @NonNull String sourceUuid, @NonNull Uri outputUri) {
        this.success = success;
        this.sourceUuid = sourceUuid;
        this.outputUri = outputUri;
    }

    @NonNull
    public ActivityResultSource getCaller() {
        ActivityResultSource caller = getSafeCaller();
        if (caller == null) throw new IllegalStateException("must call after source onCreate");
        return caller;
    }

    @Nullable
    public ActivityResultSource getSafeCaller() {
        return ActivityResultManager.findSourceByUuid(sourceUuid);
    }
}
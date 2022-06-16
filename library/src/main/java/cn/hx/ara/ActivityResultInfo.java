package cn.hx.ara;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityResultInfo {

    @NonNull
    public final String sourceUuid;

    public ActivityResultInfo(@NonNull String sourceUuid) {
        this.sourceUuid = sourceUuid;
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

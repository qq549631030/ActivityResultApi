package cn.hx.ara;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityResultInfo {
    public final int resultCode;
    @Nullable
    public final Intent data;
    @NonNull
    public final String sourceUuid;
    @NonNull
    public final Intent startIntent;

    public ActivityResultInfo(int resultCode, @Nullable Intent data, @NonNull String sourceUuid, @NonNull Intent startIntent) {
        this.resultCode = resultCode;
        this.data = data;
        this.sourceUuid = sourceUuid;
        this.startIntent = startIntent;
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

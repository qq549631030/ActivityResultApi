package cn.hx.ara;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class StartActivityResultInfo extends ActivityResultInfo {
    public final int resultCode;
    @Nullable
    public final Intent data;
    @NonNull
    public final Intent startIntent;

    public StartActivityResultInfo(@NonNull String sourceUuid, int resultCode, @Nullable Intent data, @NonNull Intent startIntent) {
        super(sourceUuid);
        this.resultCode = resultCode;
        this.data = data;
        this.startIntent = startIntent;
    }
}

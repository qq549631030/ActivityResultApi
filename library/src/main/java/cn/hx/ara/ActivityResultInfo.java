package cn.hx.ara;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ActivityResultInfo {
    public final int resultCode;
    @Nullable
    public final Intent data;
    @NonNull
    public final ActivityResultSource caller;
    @NonNull
    public final Intent startIntent;

    public ActivityResultInfo(int resultCode, @Nullable Intent data, @NonNull ActivityResultSource caller, @NonNull Intent startIntent) {
        this.resultCode = resultCode;
        this.data = data;
        this.caller = caller;
        this.startIntent = startIntent;
    }
}

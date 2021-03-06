package cn.hx.ara;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

public class StartActivityInfo {
    @NonNull
    final Intent intent;
    @Nullable
    final ActivityOptionsCompat optionsCompat;
    @NonNull
    final ActivityResultCallback callback;

    public StartActivityInfo(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback callback) {
        this.intent = intent;
        this.optionsCompat = optionsCompat;
        this.callback = callback;
    }
}

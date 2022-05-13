package cn.hx.ara;

import android.content.Intent;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

public class ActivityResultSourceImpl implements ActivityResultSource {

    private final ActivityResultSourceDelegate delegate = new ActivityResultSourceDelegate();

    @NonNull
    @Override
    public ActivityResultSourceDelegate getActivityResultSourceDelegate() {
        return delegate;
    }

    @Nullable
    @Override
    public ActivityResultRegistry customRegistry() {
        return null;
    }

    @Override
    public void startActivityForResult(@NonNull Intent intent, @NonNull ActivityResultCallback<ActivityResult> callback) {
        startActivityForResult(intent, null, callback);
    }

    @Override
    public void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback<ActivityResult> callback) {
        delegate.startActivityForResult(intent, optionsCompat, callback);
    }
}

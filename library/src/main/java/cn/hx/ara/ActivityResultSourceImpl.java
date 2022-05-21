package cn.hx.ara;

import android.content.Intent;
import android.net.Uri;

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

    @NonNull
    @Override
    public String getSourceUuid() {
        return delegate.getUuid();
    }

    @Override
    public void startActivityForResult(@NonNull Intent intent, @NonNull ActivityResultCallback callback) {
        startActivityForResult(intent, null, callback);
    }

    @Override
    public void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback callback) {
        delegate.startActivityForResult(intent, optionsCompat, callback);
    }

    @Override
    public void takePicture(@NonNull Uri outputUri, @NonNull TakePictureCallback callback) {
        delegate.takePicture(outputUri, callback);
    }

    @Override
    public void takeVideo(@NonNull Uri outputUri, @NonNull TakeVideoCallback callback) {
        delegate.takeVideo(new VideoConfig(outputUri), callback);
    }

    @Override
    public void takeVideo(@NonNull VideoConfig config, @NonNull TakeVideoCallback callback) {
        delegate.takeVideo(config, callback);
    }
}

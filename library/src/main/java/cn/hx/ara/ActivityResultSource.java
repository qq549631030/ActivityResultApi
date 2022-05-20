package cn.hx.ara;

import android.content.Intent;

import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;
import androidx.core.app.ActivityOptionsCompat;

public interface ActivityResultSource {

    String ACTIVITY_RESULT_SOURCE_UUID = "cn.hx.ara.source.uuid";

    @RestrictTo(RestrictTo.Scope.LIBRARY)
    @NonNull
    ActivityResultSourceDelegate getActivityResultSourceDelegate();

    @Nullable
    ActivityResultRegistry customRegistry();

    @NonNull
    String getSourceUuid();

    void startActivityForResult(@NonNull Intent intent, @NonNull final ActivityResultCallback callback);

    void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull final ActivityResultCallback callback);
}

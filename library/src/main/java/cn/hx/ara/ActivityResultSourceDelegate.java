package cn.hx.ara;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;

import java.util.UUID;
import java.util.concurrent.LinkedBlockingDeque;

public class ActivityResultSourceDelegate {

    private String mUuid;

    private ActivityResultLauncher<Intent> activityResultLauncher;

    @NonNull
    String getUuid() {
        if (mUuid == null) {
            mUuid = UUID.randomUUID().toString();
        }
        return mUuid;
    }

    void init(@NonNull ActivityResultSource activityResultSource, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUuid = savedInstanceState.getString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID);
        }
        ActivityResultRegistry customRegistry = activityResultSource.customRegistry();
        if (customRegistry != null) {
            activityResultLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), customRegistry, result -> {
                ActivityResultCallback callback = getActivityResultCallbacks().pollFirst();
                if (callback != null) {
                    callback.onActivityResult(result.getResultCode(), result.getData(), activityResultSource);
                }
            });
        } else {
            activityResultLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                ActivityResultCallback callback = getActivityResultCallbacks().pollFirst();
                if (callback != null) {
                    callback.onActivityResult(result.getResultCode(), result.getData(), activityResultSource);
                }
            });
        }
    }

    @NonNull
    LinkedBlockingDeque<ActivityResultCallback> getActivityResultCallbacks() {
        LinkedBlockingDeque<ActivityResultCallback> resultCallbacks = ActivityResultManager.activityResultCallbackMap.get(getUuid());
        if (resultCallbacks == null) {
            resultCallbacks = new LinkedBlockingDeque<>();
            ActivityResultManager.activityResultCallbackMap.put(getUuid(), resultCallbacks);
        }
        return resultCallbacks;
    }

    void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback callback) {
        getActivityResultCallbacks().offerFirst(callback);
        activityResultLauncher.launch(intent, optionsCompat);
    }
}

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

    void init(@NonNull ActivityResultCaller activityResultCaller, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUuid = savedInstanceState.getString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID);
        }
        ActivityResultRegistry customRegistry = ((ActivityResultSource) activityResultCaller).customRegistry();
        if (customRegistry != null) {
            activityResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), customRegistry, result -> {
                ActivityResultCallback<ActivityResult> callback = getActivityResultCallbacks().pollFirst();
                if (callback != null) {
                    callback.onActivityResult(activityResultCaller, result);
                }
            });
        } else {
            activityResultLauncher = activityResultCaller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                ActivityResultCallback<ActivityResult> callback = getActivityResultCallbacks().pollFirst();
                if (callback != null) {
                    callback.onActivityResult(activityResultCaller, result);
                }
            });
        }
    }

    @NonNull
    LinkedBlockingDeque<ActivityResultCallback<ActivityResult>> getActivityResultCallbacks() {
        LinkedBlockingDeque<ActivityResultCallback<ActivityResult>> resultCallbacks = ActivityResultManager.activityResultCallbackMap.get(getUuid());
        if (resultCallbacks == null) {
            resultCallbacks = new LinkedBlockingDeque<>();
            ActivityResultManager.activityResultCallbackMap.put(getUuid(), resultCallbacks);
        }
        return resultCallbacks;
    }

    void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback<ActivityResult> callback) {
        getActivityResultCallbacks().offerFirst(callback);
        activityResultLauncher.launch(intent, optionsCompat);
    }
}

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
                dispatchResult(activityResultSource, result);
            });
        } else {
            activityResultLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                dispatchResult(activityResultSource, result);
            });
        }
    }

    @NonNull
    LinkedBlockingDeque<StartActivityInfo> getStartActivityInfo() {
        LinkedBlockingDeque<StartActivityInfo> resultCallbacks = ActivityResultManager.activityResultCallbackMap.get(getUuid());
        if (resultCallbacks == null) {
            resultCallbacks = new LinkedBlockingDeque<>();
            ActivityResultManager.activityResultCallbackMap.put(getUuid(), resultCallbacks);
        }
        return resultCallbacks;
    }

    void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback callback) {
        getStartActivityInfo().offerFirst(new StartActivityInfo(intent, optionsCompat, callback));
        activityResultLauncher.launch(intent, optionsCompat);
    }

    void dispatchResult(@NonNull ActivityResultSource activityResultSource, @NonNull ActivityResult result) {
        StartActivityInfo startActivityInfo = getStartActivityInfo().pollFirst();
        if (startActivityInfo != null) {
            startActivityInfo.callback.onActivityResult(new ActivityResultInfo(result.getResultCode(), result.getData(), activityResultSource, startActivityInfo.intent));
        }
    }
}

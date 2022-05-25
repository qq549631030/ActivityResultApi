package cn.hx.ara;

import android.content.Intent;
import android.net.Uri;
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
    private ActivityResultLauncher<Uri> takePictureLauncher;
    private ActivityResultLauncher<VideoConfig> takeVideoLauncher;

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
                dispatchStartActivityResult(activityResultSource, result);
            });
            takePictureLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.TakePicture(), customRegistry, result -> {
                dispatchTakePictureResult(activityResultSource, result);
            });
            takeVideoLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new CustomCaptureVideo(), customRegistry, result -> {
                dispatchTakeVideoResult(activityResultSource, result);
            });
        } else {
            activityResultLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                dispatchStartActivityResult(activityResultSource, result);
            });
            takePictureLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                dispatchTakePictureResult(activityResultSource, result);
            });
            takeVideoLauncher = ((ActivityResultCaller) activityResultSource).registerForActivityResult(new CustomCaptureVideo(), result -> {
                dispatchTakeVideoResult(activityResultSource, result);
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

    @NonNull
    LinkedBlockingDeque<TakePictureInfo> getTakePictureInfo() {
        LinkedBlockingDeque<TakePictureInfo> resultCallbacks = ActivityResultManager.takePictureCallbackMap.get(getUuid());
        if (resultCallbacks == null) {
            resultCallbacks = new LinkedBlockingDeque<>();
            ActivityResultManager.takePictureCallbackMap.put(getUuid(), resultCallbacks);
        }
        return resultCallbacks;
    }

    @NonNull
    LinkedBlockingDeque<TakeVideoInfo> getTakeVideoInfo() {
        LinkedBlockingDeque<TakeVideoInfo> resultCallbacks = ActivityResultManager.takeVideoCallbackMap.get(getUuid());
        if (resultCallbacks == null) {
            resultCallbacks = new LinkedBlockingDeque<>();
            ActivityResultManager.takeVideoCallbackMap.put(getUuid(), resultCallbacks);
        }
        return resultCallbacks;
    }

    void startActivityForResult(@NonNull Intent intent, @Nullable ActivityOptionsCompat optionsCompat, @NonNull ActivityResultCallback callback) {
        getStartActivityInfo().offerFirst(new StartActivityInfo(intent, optionsCompat, callback));
        activityResultLauncher.launch(intent, optionsCompat);
    }

    void takePicture(@NonNull Uri outputUri, @NonNull TakePictureCallback callback) {
        getTakePictureInfo().offerFirst(new TakePictureInfo(outputUri, callback));
        takePictureLauncher.launch(outputUri);
    }

    void takeVideo(@NonNull VideoConfig config, @NonNull TakeVideoCallback callback) {
        getTakeVideoInfo().offerFirst(new TakeVideoInfo(config, callback));
        takeVideoLauncher.launch(config);
    }

    void dispatchStartActivityResult(@NonNull ActivityResultSource activityResultSource, @NonNull ActivityResult result) {
        StartActivityInfo startActivityInfo = getStartActivityInfo().pollFirst();
        if (startActivityInfo != null) {
            startActivityInfo.callback.onActivityResult(new ActivityResultInfo(result.getResultCode(), result.getData(), activityResultSource.getSourceUuid(), startActivityInfo.intent));
        }
    }

    void dispatchTakePictureResult(@NonNull ActivityResultSource activityResultSource, boolean result) {
        TakePictureInfo takePictureInfo = getTakePictureInfo().pollFirst();
        if (takePictureInfo != null) {
            takePictureInfo.callback.onTakePictureResult(new TakePictureResultInfo(result, activityResultSource.getSourceUuid(), takePictureInfo.outputUri));
        }
    }

    void dispatchTakeVideoResult(@NonNull ActivityResultSource activityResultSource, boolean result) {
        TakeVideoInfo takeVideoInfo = getTakeVideoInfo().pollFirst();
        if (takeVideoInfo != null) {
            takeVideoInfo.callback.onTakeVideoResult(new TakeVideoResultInfo(result, activityResultSource.getSourceUuid(), takeVideoInfo.config.outputUri));
        }
    }
}

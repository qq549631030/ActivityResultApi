package cn.hx.ara;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomCaptureVideo extends ActivityResultContract<VideoConfig, Boolean> {

    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, VideoConfig input) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE)
                .putExtra(MediaStore.EXTRA_OUTPUT, input.outputUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, input.quality);
        if (input.durationLimit != 0) {
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, input.durationLimit);
        }
        if (input.sizeLimit != 0) {
            intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, input.sizeLimit);
        }
        return intent;
    }

    @Override
    public Boolean parseResult(int resultCode, @Nullable Intent intent) {
        return resultCode == Activity.RESULT_OK;
    }
}

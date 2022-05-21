package cn.hx.ara;

import androidx.annotation.NonNull;

public interface TakePictureCallback {
    void onTakePictureResult(@NonNull TakePictureResultInfo resultInfo);
}
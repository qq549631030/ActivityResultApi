package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;

public class TakePictureInfo {
    @NonNull
    final Uri outputUri;

    @NonNull
    final TakePictureCallback callback;

    public TakePictureInfo(@NonNull Uri outputUri, @NonNull TakePictureCallback callback) {
        this.outputUri = outputUri;
        this.callback = callback;
    }
}
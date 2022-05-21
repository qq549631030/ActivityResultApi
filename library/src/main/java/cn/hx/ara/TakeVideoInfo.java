package cn.hx.ara;

import androidx.annotation.NonNull;

public class TakeVideoInfo {

    @NonNull
    final VideoConfig config;

    @NonNull
    final TakeVideoCallback callback;

    public TakeVideoInfo(@NonNull VideoConfig config, @NonNull TakeVideoCallback callback) {
        this.config = config;
        this.callback = callback;
    }
}
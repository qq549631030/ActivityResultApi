package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;

public class VideoConfig {
    @NonNull
    final Uri outputUri;

    public int quality = 1;
    public int durationLimit = 0;
    public long sizeLimit = 0L;

    public VideoConfig(@NonNull Uri outputUri) {
        this.outputUri = outputUri;
    }

    public VideoConfig(@NonNull Uri outputUri, int quality, int durationLimit, long sizeLimit) {
        this.outputUri = outputUri;
        this.quality = quality;
        this.durationLimit = durationLimit;
        this.sizeLimit = sizeLimit;
    }
}
package cn.hx.ara;

import android.net.Uri;

import androidx.annotation.NonNull;

public class TakeVideoResultInfo {

    public final boolean success;

    @NonNull
    public final ActivityResultSource caller;

    @NonNull
    public final Uri outputUri;

    public TakeVideoResultInfo( boolean success, @NonNull ActivityResultSource caller, @NonNull Uri outputUri) {
        this.success = success;
        this.caller = caller;
        this.outputUri = outputUri;
    }
}
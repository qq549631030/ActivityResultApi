package cn.hx.ara;

import androidx.annotation.NonNull;

public interface TakeVideoCallback {
    void onTakeVideoResult(@NonNull TakeVideoResultInfo resultInfo);
}
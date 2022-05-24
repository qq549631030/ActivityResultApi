package cn.hx.ara;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

public class ActivityResultManager {

    static final Map<String, ActivityResultSource> activityResultSourceMap = new HashMap<>();
    static final Map<String, LinkedBlockingDeque<StartActivityInfo>> activityResultCallbackMap = new HashMap<>();
    static final Map<String, LinkedBlockingDeque<TakePictureInfo>> takePictureCallbackMap = new HashMap<>();
    static final Map<String, LinkedBlockingDeque<TakeVideoInfo>> takeVideoCallbackMap = new HashMap<>();

    public static void init(Context context) {
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                if (activity instanceof ActivityResultSource && activity instanceof ActivityResultCaller) {
                    ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) activity).getActivityResultSourceDelegate();
                    activityResultSourceDelegate.init((ActivityResultSource) activity, bundle);
                    activityResultSourceMap.put(((ActivityResultSource) activity).getSourceUuid(), (ActivityResultSource) activity);
                }
                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                            if (f instanceof ActivityResultSource) {
                                ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) f).getActivityResultSourceDelegate();
                                activityResultSourceDelegate.init((ActivityResultSource) f, savedInstanceState);
                                activityResultSourceMap.put(((ActivityResultSource) f).getSourceUuid(), (ActivityResultSource) f);
                            }
                        }

                        @Override
                        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
                            if (f instanceof ActivityResultSource) {
                                outState.putString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID, ((ActivityResultSource) f).getSourceUuid());
                            }
                        }

                        @Override
                        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                            if (f instanceof ActivityResultSource) {
                                activityResultSourceMap.remove(((ActivityResultSource) f).getSourceUuid());
                                if (f.isRemoving()) {
                                    activityResultCallbackMap.remove(((ActivityResultSource) f).getSourceUuid());
                                    takePictureCallbackMap.remove(((ActivityResultSource) f).getSourceUuid());
                                    takeVideoCallbackMap.remove(((ActivityResultSource) f).getSourceUuid());
                                }
                            }
                        }
                    }, true);
                }
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {
                if (activity instanceof ActivityResultSource) {
                    bundle.putString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID, ((ActivityResultSource) activity).getSourceUuid());
                }
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (activity instanceof ActivityResultSource) {
                    activityResultSourceMap.remove(((ActivityResultSource) activity).getSourceUuid());
                    if (activity.isFinishing()) {
                        activityResultCallbackMap.remove(((ActivityResultSource) activity).getSourceUuid());
                        takePictureCallbackMap.remove(((ActivityResultSource) activity).getSourceUuid());
                        takeVideoCallbackMap.remove(((ActivityResultSource) activity).getSourceUuid());
                    }
                }
            }
        });
    }

    @Nullable
    public static ActivityResultSource findSourceByUuid(@NonNull String uuid) {
        return activityResultSourceMap.get(uuid);
    }
}

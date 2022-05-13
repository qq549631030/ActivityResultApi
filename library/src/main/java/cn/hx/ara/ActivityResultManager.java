package cn.hx.ara;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
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

    static final Map<String, LinkedBlockingDeque<ActivityResultCallback<ActivityResult>>> activityResultCallbackMap = new HashMap<>();

    public static void init(Context context) {
        ((Application) context.getApplicationContext()).registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
                if (activity instanceof ActivityResultSource && activity instanceof ActivityResultCaller) {
                    ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) activity).getActivityResultSourceDelegate();
                    activityResultSourceDelegate.init((ActivityResultCaller) activity, bundle);
                }
                if (activity instanceof FragmentActivity) {
                    ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
                        @Override
                        public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
                            if (f instanceof ActivityResultSource) {
                                ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) f).getActivityResultSourceDelegate();
                                activityResultSourceDelegate.init(f, savedInstanceState);
                            }
                        }

                        @Override
                        public void onFragmentSaveInstanceState(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Bundle outState) {
                            if (f instanceof ActivityResultSource) {
                                ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) f).getActivityResultSourceDelegate();
                                outState.putString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID, activityResultSourceDelegate.getUuid());
                            }
                        }

                        @Override
                        public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
                            if (f instanceof ActivityResultSource && f.isRemoving()) {
                                activityResultCallbackMap.remove(((ActivityResultSource) f).getActivityResultSourceDelegate().getUuid());
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
                    ActivityResultSourceDelegate activityResultSourceDelegate = ((ActivityResultSource) activity).getActivityResultSourceDelegate();
                    bundle.putString(ActivityResultSource.ACTIVITY_RESULT_SOURCE_UUID, activityResultSourceDelegate.getUuid());
                }
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                if (activity instanceof ActivityResultSource && activity.isFinishing()) {
                    activityResultCallbackMap.remove(((ActivityResultSource) activity).getActivityResultSourceDelegate().getUuid());
                }
            }
        });
    }
}

package hamletleon.empleado_androidjava.infrastructure.utils.requestUtils;

import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hsantana on 31/7/2017.
 * Call with Retry Utils
 */

public class CallUtil {
    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        call.enqueue(new CallbackWithRetry<T>(call) {

            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (!call.isCanceled()) callback.onResponse(call, response);
            }

            @Override
            public void onFinalFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                if (!call.isCanceled()) callback.onFailure(call, t);
            }
        });
    }

    private abstract static class CallbackWithRetry<T> implements Callback<T> {
        private static final int TOTAL_RETRIES = 3;
        private final String TAG = CallbackWithRetry.class.getSimpleName();
        private Call<T> call;
        private int retryCount = 0;

        CallbackWithRetry(Call<T> call) {
            this.call = call;
        }

        @Override
        public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
            if (retryCount++ < TOTAL_RETRIES && !call.isCanceled()) {
                Log.v(TAG, "Retrying... (" + retryCount + " out of " + TOTAL_RETRIES + ")");
                retry();
            } else if (!call.isCanceled()) {
                // TODO: CUSTOM ERROR MESSAGE -> Depends on API
                call.cancel();
                onFinalFailure(call, t);
                Log.e(TAG, t.getLocalizedMessage());
            }
        }

        private void retry() {
            call.clone().enqueue(this);
        }

        public abstract void onFinalFailure(@NonNull Call<T> call, @NonNull Throwable t);
    }
}
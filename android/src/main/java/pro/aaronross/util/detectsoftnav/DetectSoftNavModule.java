package pro.aaronross.util.detectsoftnav;

import android.os.Build;
import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import javax.annotation.Nullable;

public class DetectSoftNavModule extends ReactContextBaseJavaModule {
    ReactApplicationContext mContext;
    boolean _init = false;

    public DetectSoftNavModule(final ReactApplicationContext reactContext) {
        super(reactContext);
        mContext = reactContext;
    }

    @ReactMethod
    public void init() {
        if (_init) return;

        getCurrentActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View decorView = getCurrentActivity().getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        WritableMap params = Arguments.createMap();
                        sendEvent(mContext, (visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0 ? "softNavDidShow" : "softNavDidHide", params);
                    }
                });
            }
        });
        _init = true;
    }

    @ReactMethod
    public void isVisible(final Callback callback) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) callback.invoke(true);

        int visibility = getCurrentActivity().getWindow().getDecorView().getSystemUiVisibility();
        callback.invoke((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0);
    }

    @Override
    public String getName() {
        return "DetectSoftNav";
    }

    private void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}

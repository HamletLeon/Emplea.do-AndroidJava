package hamletleon.empleado_androidjava.infrastructure.utils.sessionUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;

import java.util.ArrayList;

import static hamletleon.empleado_androidjava.infrastructure.utils.tools.intentActivityActionWithMultipleExtras;

/**
 * Created by hsantana on 3/7/2017.
 * Generic Session Manager
 */

public class SessionManager {
    private static final String PREFERENCES_NAME = "SessionManager_Preferences";
    private static final String IS_LOGIN = "IsLoggedIn";
    private Activity mActivity;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Activity context) {
        mActivity = context;
        mPreferences = mActivity.getSharedPreferences(PREFERENCES_NAME, 0);
        mEditor = mPreferences.edit();
    }

    public static void logoutSession(Activity activity, Boolean intent, Class classIntent) {
        SessionManager sessionManager = new SessionManager(activity);
        sessionManager.clearEditor();
        if (intent) intentActivityActionWithMultipleExtras(activity, classIntent, null, null, true);
    }

    public void initializeSession(ArrayList<String> keys, ArrayList<Object> objects) {
        mEditor.putBoolean(IS_LOGIN, true);
        if (keys != null && objects != null) saveObjects(keys, objects);
        else mEditor.commit();
    }

    private void saveObjects(ArrayList<String> keys, ArrayList<Object> objects) {
        if (keys != null && objects != null && keys.size() == objects.size()) {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                Object object = objects.get(i);
                if (object != null) {
                    if (object instanceof String) mEditor.putString(key, (String) object);
                    else if (object instanceof Integer) mEditor.putInt(key, (Integer) object);
                    else if (object instanceof Float) mEditor.putFloat(key, (Float) object);
                    else if (object instanceof Boolean) mEditor.putBoolean(key, (Boolean) object);
                    else if (object instanceof Long) mEditor.putLong(key, (Long) object);
                    else throw new RuntimeException("Instanciable Object!");
                }
            }
            mEditor.commit();
        } else if (keys == null) throw new RuntimeException("Array of keys is null!");
        else if (objects == null) throw new RuntimeException("Array of objects is null!");
        else if (keys.size() != objects.size())
            throw new RuntimeException("Incompatible array sizes!");
    }

    public void logoutSession(Boolean intent, Class classIntent) {
        clearEditor();
        if (intent)
            intentActivityActionWithMultipleExtras(mActivity, classIntent, null, null, true);
    }

    private void clearEditor() {
        mEditor.clear();
        mEditor.commit();
    }

    public Object getObjectByKey(String KEY) {
        return mPreferences.getAll().get(KEY);
    }

    public boolean isLoggedIn() {
        return mPreferences.getBoolean(IS_LOGIN, false);
    }
}
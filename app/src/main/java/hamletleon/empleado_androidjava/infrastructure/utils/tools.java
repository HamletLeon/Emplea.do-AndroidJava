package hamletleon.empleado_androidjava.infrastructure.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hsantana on 5/25/2017.
 * Generic Tools
 */

public final class tools {
    private static final DateFormat m_ISO8601Withms = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", new Locale("es", "ES"));
    private static final DateFormat m_ISO8601WithOutms = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", new Locale("es", "ES"));

    public static void intentActivityActionWithMultipleExtras(Context context, Class classIntent, Object[] objects, String[] TAG) {
        if (context != null && classIntent != null) {
            Intent intent = new Intent(context, classIntent);
            if (TAG != null && objects != null && TAG.length == objects.length) {
                for (int i = 0; i < objects.length; i++) {
                    if (objects[i] instanceof Parcelable) {
                        intent.putExtra(TAG[i], (Parcelable) objects[i]);
                    } else if (objects[i] instanceof String) {
                        intent.putExtra(TAG[i], (String) objects[i]);
                    } else if (objects[i] instanceof Integer) {
                        intent.putExtra(TAG[i], (Integer) objects[i]);
                    }
                }
            }
            context.startActivity(intent);
        } else throw new IllegalStateException();
    }

    public static void hideKeyboard(Activity mActivity, View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void SendEmailIntent(Context context, String email, String subject, String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        try {
            context.startActivity(Intent.createChooser(intent, "Enviar correo..."));
        } catch (android.content.ActivityNotFoundException exc) {
            Toast.makeText(context, "No hay ningun cliente de correos instalado en su dispositivo.", Toast.LENGTH_LONG).show();
        }
    }

    public static boolean checkString(String check) {
        return check != null && !check.isEmpty();
    }

    public static Date getDateFromString(String date) {
        Date _date = null;
        try {
            _date = m_ISO8601Withms.parse(date);
        } catch (ParseException ignored) {
        }
        if (_date == null) {
            try {
                _date = m_ISO8601WithOutms.parse(date);
            } catch (ParseException ignored) {
            }
        }
        return _date;
    }

    public static Date getDate(SimpleDateFormat dateFormat, String date) {
        try {
            return dateFormat.parse(date);
        } catch (Exception e) {
            return new Date();
        }
    }

    public static String getDate(SimpleDateFormat dateFormat, Date date) {
        return dateFormat.format(date);
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
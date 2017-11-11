package hamletleon.empleado_androidjava.infrastructure.utils.sessionUtils;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;

/**
 * Created by hsantana on 3/7/2017.
 * Conversor de Objetos a String y viceversa
 */

public class GenericObjectConverter<T> {
    public String getJsonStringFromObject(T object, Class<T> tClass) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> adapter = moshi.adapter(tClass);
        return adapter.toJson(object);
    }

    public T getObjectFromJsonString(String json, Class<T> tClass) {
        Moshi moshi = new Moshi.Builder().build();
        JsonAdapter<T> jsonAdapter = moshi.adapter(tClass);
        try {
            return jsonAdapter.fromJson(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

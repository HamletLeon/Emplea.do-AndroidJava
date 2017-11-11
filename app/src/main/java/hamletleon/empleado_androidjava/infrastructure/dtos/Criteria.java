package hamletleon.empleado_androidjava.infrastructure.dtos;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hsantana on 25/9/2017.
 * Criteria Model
 */

public abstract class Criteria {
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();
        for (Field f : this.getClass().getFields()) {
            try {
                if (!f.isSynthetic() && !f.getName().toLowerCase().equals("serialversionuid")
                        && !f.getName().toLowerCase().equals("creator") && !f.getName().toLowerCase().equals("parcelable_write_return_value")
                        && !f.getName().toLowerCase().equals("contents_file_descriptor") && f.get(this) != null)
                    map.put(f.getName(), f.get(this));
            } catch (Exception ignored) {
            }
        }
        return map;
    }
}
package me.nort3x.quanta.internal.auto;

import me.nort3x.quanta.pub.config.QuantaConfiguration;

import java.lang.reflect.Field;
import java.util.*;

import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;

public class Scanner {
    public static List<Field> getFields(Class<?> clazz, QuantaConfiguration configuration) {
        List<Field> listOfFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int m = field.getModifiers();
            if (
                    (configuration.isIgnoreStaticFields() && isStatic(m)) ||
                    (configuration.isIgnoreTransientFields() && isTransient(m)) ||
                    (configuration.isIgnoreSyntheticFields() && field.isSynthetic()) ||
                    (configuration.getCustomFieldFilter() != null && configuration.getCustomFieldFilter().apply(field))
            )
                continue;
            field.setAccessible(true);
            listOfFields.add(field);
        }
        Collections.sort(listOfFields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
            }
        });
        return listOfFields;
    }

    public static List<Field> getFields(Class<?> clazz) {
        return getFields(clazz, QuantaConfiguration.getDefault());
    }
}

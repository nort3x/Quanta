package me.nort3x.quanta.internal.auto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.reflect.Modifier.isStatic;
import static java.lang.reflect.Modifier.isTransient;

public class Scanner {
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> listOfFields = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            int m = field.getModifiers();
            if (isStatic(m) || isTransient(m) || field.isSynthetic())
                continue;
            field.setAccessible(true);
            listOfFields.add(field);
        }
        listOfFields.sort((o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName()));
        return listOfFields;
    }
}

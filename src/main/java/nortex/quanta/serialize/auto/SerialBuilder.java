package nortex.quanta.serialize.auto;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import static java.lang.reflect.Modifier.*;

public class SerialBuilder {
    public static List<Field> generateSerialDeserializeTool(Class<?> zClass){
        return getFields(zClass);
    }

    private static List<Field> getFields(Class<?> clazz){
        List<Field> listOfFields = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            int m = field.getModifiers();
            if(isStatic(m) || isTransient(m) || field.isSynthetic())
                continue;
            field.setAccessible(true);
            listOfFields.add(field);
        }
        listOfFields.sort(new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return String.CASE_INSENSITIVE_ORDER.compare(o1.getName(), o2.getName());
            }
        });
        return listOfFields;
    }

}

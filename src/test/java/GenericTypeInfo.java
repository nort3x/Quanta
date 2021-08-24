import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;

public class GenericTypeInfo {

    public static Collection<String> c = new ArrayList<>();

    public static void main(String[] args) throws Exception {

        Class<?> type = (Class<?>) ((ParameterizedType)GenericTypeInfo.class.getField("c").getGenericType()).getActualTypeArguments()[0];
        System.out.println(type.getName());
    }
}
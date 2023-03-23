package start;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionRetrieveProperties {

    public static List<String> retrieveProperties(Object object) {
        List<String> fieldList = new ArrayList<String>();

        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            //Object value;
            try {
                //value = field.get(object);
                fieldList.add(field.getName());

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }



        return fieldList;
    }
}

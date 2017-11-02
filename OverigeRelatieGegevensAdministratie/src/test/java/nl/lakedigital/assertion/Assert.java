package nl.lakedigital.assertion;


import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;

public class Assert {
    public static void assertEquals(Object expected, Object actual) {
        org.junit.Assert.assertEquals(expected, actual);
    }

    public static void assertEquals(Object expected, Object actual, List<String> fields) {
        for (String field : fields) {
            Field f = ReflectionUtils.findField(expected.getClass(), field);
            ReflectionUtils.makeAccessible(f);
            Object expectedField = ReflectionUtils.getField(f, expected);

            Object actualField = ReflectionUtils.getField(f, actual);

            //            System.out.println(field);

            org.junit.Assert.assertEquals(expectedField, actualField);
        }
    }
}
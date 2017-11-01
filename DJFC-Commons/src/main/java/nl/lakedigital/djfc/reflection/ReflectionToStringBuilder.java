package nl.lakedigital.djfc.reflection;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

public class ReflectionToStringBuilder extends org.apache.commons.lang3.builder.ReflectionToStringBuilder {
    public ReflectionToStringBuilder(Object object) {
        super(object);
    }

    public static String toString(Object object) {
        return toString(object, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public static String toString(Object object, ToStringStyle style) {
        StringBuilder sb = new StringBuilder();

        if (object instanceof List) {
            for (Object obj : (List) object) {
                sb.append(org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(obj, style));
            }
        } else {
            sb.append(org.apache.commons.lang3.builder.ReflectionToStringBuilder.toString(object, style));
        }

        return sb.toString();
    }
}

package socialDist.util;

import java.lang.reflect.Field;

/**
 * Created by izoomko on 5/17/17.
 */
public class Injector {
    public static void inject(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                field.setAccessible(true);
                try {
                    field.set(instance, field.getType().getMethod("getInstance").invoke(null));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Injector() {
        Injector.inject(this);
    }
}
package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;

class InstrBuilderAnnotationTest {

    @Test
    void testRetentionPolicy() {
        Retention retention = InstrBuilder.class.getAnnotation(Retention.class);
        assertNotNull(retention, "InstrBuilder must have @Retention");
        assertEquals(RetentionPolicy.RUNTIME, retention.value(), "RetentionPolicy should be RUNTIME");
    }

    @Test
    void testTargetType() {
        Target target = InstrBuilder.class.getAnnotation(Target.class);
        assertNotNull(target, "InstrBuilder must have @Target");
        ElementType[] types = target.value();
        assertEquals(1, types.length);
        assertEquals(ElementType.TYPE, types[0], "Target should be TYPE");
    }
}
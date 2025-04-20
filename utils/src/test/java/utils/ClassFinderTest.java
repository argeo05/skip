package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;

class ClassFinderTest {

    @Test
    void testFindClassesInUtilsPackage() {
        ClassFinder finder = new ClassFinder("utils");
        List<Class<?>> classes = finder.findClasses();
        List<String> names = classes.stream()
                .map(Class::getName)
                .collect(Collectors.toList());

        assertTrue(names.contains("utils.BytesParserTest"), "Should find BytesParser");
        assertTrue(names.contains("utils.ClassFinderTest"), "Should find ClassFinder");
    }

    @Test
    void testFindClassesInNonexistentPackage() {
        ClassFinder finder = new ClassFinder("non.existent.pkg");
        List<Class<?>> classes = finder.findClasses();
        assertTrue(classes.isEmpty(), "Nonexistent package should return empty list");
    }
}
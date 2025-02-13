package cvm.parser;

import cvm.parser.instructions.InstructionBuilder;
import utils.InstrBuilder;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.CodeSource;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
    private final String basePackage;
    private final Map<String, Class<?>> builders = new HashMap<>();

    public ClassFinder(String basePackage) {
        this.basePackage = basePackage;
        scanClasses();
    }

    private void scanClasses() {
        String packagePath = basePackage.replace('.', '/');
        CodeSource src = getClass().getProtectionDomain().getCodeSource();
        if (src == null) {
            scanFileSystem(packagePath);
            return;
        }

        try {
            URL jarUrl = src.getLocation();
            String jarPath = jarUrl.toURI().getPath();
            try (JarFile jarFile = new JarFile(jarPath)) {
                Enumeration<JarEntry> entries = jarFile.entries();
                while (entries.hasMoreElements()) {
                    JarEntry entry = entries.nextElement();
                    processJarEntry(entry, packagePath);
                }
            }
        } catch (Exception e) {
            scanFileSystem(packagePath);
        }
    }

    private void processJarEntry(JarEntry entry, String packagePath) {
        String entryName = entry.getName();
        if (!entryName.startsWith(packagePath) || !entryName.endsWith(".class")) return;

        String className = entryName
                .replace('/', '.')
                .replace(".class", "");
        registerClass(className);
    }

    private void scanFileSystem(String packagePath) {
        URL resource = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        if (resource == null) return;

        try {
            File dir = new File(resource.toURI());
            if (!dir.exists()) return;
            scanDirectory(dir, basePackage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private void scanDirectory(File dir, String currentPackage) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, currentPackage + "." + file.getName());
            } else if (file.getName().endsWith(".class")) {
                String className = currentPackage + "." + file.getName().replace(".class", "");
                registerClass(className);
            }
        }
    }

    private void registerClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            InstrBuilder annotation = clazz.getAnnotation(InstrBuilder.class);
            if (annotation != null) {
                builders.put(annotation.value(), clazz);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public InstructionBuilder resolve(String name) throws Exception {
        Class<?> clazz = builders.get(name);
        if (clazz == null) throw new IllegalArgumentException("Builder not found: " + name);
        return (InstructionBuilder) clazz.getDeclaredConstructor().newInstance();
    }
}
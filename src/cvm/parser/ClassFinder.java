package cvm.parser;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
    private final String basePackage;

    public ClassFinder(String basePackage) {
        this.basePackage = basePackage;
    }

    public List<Class<?>> findClasses() {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources(packagePath);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if (resource.getProtocol().equals("jar")) {
                    processJar(resource, packagePath, classes);
                } else {
                    processDirectory(resource, classes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    private void processJar(URL jarUrl, String packagePath, List<Class<?>> classes) throws Exception {
        String jarPath = jarUrl.getPath().substring(5, jarUrl.getPath().indexOf("!"));
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(packagePath) && entryName.endsWith(".class")) {
                    String className = entryName
                            .replace('/', '.')
                            .replace(".class", "");
                    addClass(className, classes);
                }
            }
        }
    }

    private void processDirectory(URL resource, List<Class<?>> classes) throws URISyntaxException {
        File dir = new File(resource.toURI());
        if (!dir.exists()) return;
        scanDirectory(dir, basePackage, classes);
    }

    private void scanDirectory(File dir, String currentPackage, List<Class<?>> classes) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, currentPackage + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                String className = currentPackage + "." + file.getName().replace(".class", "");
                addClass(className, classes);
            }
        }
    }

    private void addClass(String className, List<Class<?>> classes) {
        try {
            classes.add(Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
package utils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Locates and loads all classes in a given package, whether on the file system or inside JARs.
 */
public class ClassFinder {
    private final String basePackage;

    /**
     * @param basePackage the package name to scan (e.g. "com.example.myapp")
     */
    public ClassFinder(String basePackage) {
        this.basePackage = basePackage;
    }

    /**
     * Finds and returns all {@link Class} objects under the configured package.
     *
     * @return list of classes found in the package
     */
    public List<Class<?>> findClasses() {
        List<Class<?>> classes = new ArrayList<>();
        String path = basePackage.replace('.', '/');
        try {
            Enumeration<URL> resources = Thread.currentThread()
                    .getContextClassLoader()
                    .getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                if ("jar".equals(resource.getProtocol())) {
                    processJar(resource, path, classes);
                } else {
                    processDirectory(resource, classes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    // Private helpers omitted from Javadoc
    private void processJar(URL jarUrl, String packagePath, List<Class<?>> classes) throws Exception {
        String jarPath = URLDecoder.decode(
                jarUrl.getPath().substring(5, jarUrl.getPath().indexOf('!')),
                "UTF-8"
        );
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                if (name.startsWith(packagePath) && name.endsWith(".class")) {
                    addClass(name.replace('/', '.').replace(".class", ""), classes);
                }
            }
        }
    }

    private void processDirectory(URL resource, List<Class<?>> classes) throws URISyntaxException {
        File dir = new File(resource.toURI());
        if (!dir.exists()) return;
        scanDirectory(dir, basePackage, classes);
    }

    private void scanDirectory(File dir, String pkg, List<Class<?>> classes) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(file, pkg + "." + file.getName(), classes);
            } else if (file.getName().endsWith(".class")) {
                addClass(pkg + "." + file.getName().replace(".class", ""), classes);
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
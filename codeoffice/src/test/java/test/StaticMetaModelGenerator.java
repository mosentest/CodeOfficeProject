package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import mu.codeoffice.entity.Case;

public class StaticMetaModelGenerator {

	private static final String DESTINATION = "/home/zimu/Java/CodeOfficeProject/CodeOfficeProject/codeoffice/src/main/resources/metamodel";

	public static void generate(String packagePath, String destination) throws Exception {
		getClasses(packagePath).forEach(c -> { try { generate(c, destination); } catch (Exception e) {} });
	}

	private static List<Class<?>> getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> classes = new ArrayList<>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes;
	}

	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static void generate(Class<?> typeClass, String destination) throws Exception {
		String javaFile = generate(typeClass);
		File file = new File(destination + typeClass.getSimpleName() + "_.java");
		if (!file.exists()) {
			file.createNewFile();
		}
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()))) {
			writer.write(javaFile);
			System.out.println("Static metamodel created for class: " + typeClass.getSimpleName());
		}
	}

	public static String generate(Class<?> typeClass) throws Exception {
		System.out.println(typeClass.getName());
		Field[] field = typeClass.getDeclaredFields();
		String className = typeClass.getSimpleName();
		StringBuilder buffer = new StringBuilder();
		return buffer.toString();
	}

	public static void main(String[] args) throws Exception {
		generate(Case.class, DESTINATION);
	}

}

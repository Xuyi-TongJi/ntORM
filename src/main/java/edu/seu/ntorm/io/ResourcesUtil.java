package edu.seu.ntorm.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 加载XML文件资源的工具类
 */
public class ResourcesUtil {

    /**
     * 根据资源路径名称获取字符流
     * @param resource resource
     * @return 字符流对象
     */
    public static Reader getResourceAsReader(String resource) throws IOException {
        InputStream stream = getResourceAsStream(resource);
        if (stream != null) {
            return new InputStreamReader(stream);
        }
        return null;
    }

    /**
     * 根据资源路径名称获取字节流
     * 用当前线程的所有类加载器轮询尝试加载，直到成功为止
     * @param resource resource
     * @return 字节流对象
     * @throws IOException
     */
    public static InputStream getResourceAsStream(String resource) throws IOException {
        ClassLoader[] classLoaders = getClassLoader();
        for (ClassLoader classLoader : classLoaders) {
            InputStream stream = classLoader.getResourceAsStream(resource);
            if (stream != null) {
                return stream;
            }
        }
        return null;
    }

    /**
     * 获得当前线程的类加载器
     */
    private static ClassLoader[] getClassLoader() {
        return new ClassLoader[]{
                // JVM Class Loader
                ClassLoader.getSystemClassLoader(),
                // context Class Loader
                Thread.currentThread().getContextClassLoader()
        };
    }

    /**
     * 加载一个类(通过获取该类Class对象的方式)
     * @param className class Name
     * @return the Class Object of this class
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }
}

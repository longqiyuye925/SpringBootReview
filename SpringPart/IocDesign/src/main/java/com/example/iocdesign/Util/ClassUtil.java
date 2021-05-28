package com.example.iocdesign.Util;

import javafx.scene.effect.SepiaTone;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description:类相关通用方法通过类加载器获取资源信息
 * @author: zf
 * @Param:
 * @Return:
 * @Date: 2021 05 2021/5/22
 */
@Slf4j
public class ClassUtil {
    public static final String FILE_PROTOCOL = "file";

    /**
     * 设置类的属性值
     *
     * @param field
     * @param target
     * @param value
     * @param accessible
     */
    public static void setField(Field field, Object target, Object value, boolean accessible) {

        try {
            field.setAccessible(accessible);//关闭安全访问检查
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static Set<Class<?>> extractPackageClass(String packageName) {
        //1 获取类的加载器
        ClassLoader classLoader = getClassLoader();
        //2通过类加载器获取加载的资源
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("unalbe to retrive anything from package" + packageName);
            return null;
        }
        //3 依据不同的资源类型，采用不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        //过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new HashSet<>();
            //这里是文件夹
            File packageDiretory = new File(url.getPath());
            extractClassFile(classSet, packageDiretory, packageName);
        }
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件（包括子package里的class文件）
     *
     * @param classSet
     * @param packageDiretory
     * @param packageName
     */
    private static void extractClassFile(Set<Class<?>> classSet, File packageDiretory, String packageName) {
        if (!packageDiretory.isDirectory()) {
            return;
        }
        //如果是一个文件夹，则调用listFiles方法获取文件夹下的文件或者文件夹
        File[] file = packageDiretory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                } else {
                    //获取文件的绝对路径值
                    String absoluterFilePath = pathname.getAbsolutePath();
                    if (absoluterFilePath.endsWith(".class")) {
                        //若是class文件则直接加载
                        addToClassSet(absoluterFilePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String absoluterFilePath) {
                //1.从class文件的绝对路径里面提取出包含了package的类名
                absoluterFilePath = absoluterFilePath.replace(File.separator, ".");
                String className = absoluterFilePath.substring(absoluterFilePath.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                Class<?> targerClass = loadClass(className);
                classSet.add(targerClass);
            }
        });
        if (file != null) {
            for (File f : file) {
                //递归调用
                extractClassFile(classSet, f, packageName);
            }
        }
    }

    private static Class<?> loadClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz;
        } catch (ClassNotFoundException e) {
            log.error("load class error ", e);
            throw new RuntimeException(e);
        }

    }

    private static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static <T> T newInstance(Class<?> clazz, boolean b) {
        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(b);
            return (T) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            //这里加运行时异常是为了出错的时候中断程序，RuntimeException可以中断程序
            throw new RuntimeException(e);
        }
    }
}

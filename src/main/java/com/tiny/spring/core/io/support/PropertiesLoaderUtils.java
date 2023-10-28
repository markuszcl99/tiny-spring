package com.tiny.spring.core.io.support;

import com.sun.istack.internal.Nullable;
import com.tiny.spring.util.ClassUtils;
import com.tiny.spring.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author: markus
 * @date: 2023/10/25 11:18 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public abstract class PropertiesLoaderUtils {
    private static final String XML_FILE_EXTENSION = ".xml";

    public static Properties readConfigFile(String resourceNames, @Nullable ClassLoader classLoader) throws IOException {
        ClassLoader work = classLoader;
        if (classLoader == null) {
            work = ClassUtils.getDefaultClassLoader();
        }
        Enumeration<URL> resources = work.getResources(resourceNames);
        Properties properties = new Properties();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            URLConnection con = url.openConnection();
            ResourceUtils.useCachesIfNecessary(con);
            InputStream is = con.getInputStream();
            try {
                if (resourceNames.endsWith(XML_FILE_EXTENSION)) {
                    properties.loadFromXML(is);
                } else {
                    properties.load(is);
                }
            } finally {
                is.close();
            }
        }
        return properties;
    }

    public static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}

package org.itstack.demo.jvm.classpath;

import org.itstack.demo.jvm.classpath.impl.CompositeEntry;
import org.itstack.demo.jvm.classpath.impl.DirEntry;
import org.itstack.demo.jvm.classpath.impl.WildcardEntry;
import org.itstack.demo.jvm.classpath.impl.ZipEntry;

import java.io.File;
import java.io.IOException;

/**
 * @author ssqswyf
 * @date 2022/2/28
 * 类路径接口
 */
public interface Entry {

    byte[] readClass(String classpath) throws IOException;

    static Entry create(String path) {

        // File.pathSeparator；路径分隔符(win\linux)
        if (path.contains(File.pathSeparator)) {
            return new CompositeEntry(path);
        }

        if (path.endsWith("*")) {
            return new WildcardEntry(path);
        }

        if (path.endsWith(".jar") || path.endsWith(".JAR") ||
                path.endsWith(".zip") || path.endsWith(".ZIP")) {
            return new ZipEntry(path);
        }

        return new DirEntry(path);
    }

}

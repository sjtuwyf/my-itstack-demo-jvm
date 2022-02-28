package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Map;

/**
 * @author ssqswyf
 * @date 2022/2/28
 * zip/zar文件形式类路径
 */
public class ZipEntry implements Entry {

    private Path absolutePath;

    public ZipEntry(String path) {
        // 获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String classpath) throws IOException {
        try (FileSystem zipFs = FileSystems.newFileSystem(absolutePath, null)) {
            return Files.readAllBytes(zipFs.getPath(classpath));
        }
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}

package org.itstack.demo.jvm.classpath.impl;

import org.itstack.demo.jvm.classpath.Entry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author ssqswyf
 * @date 2022/2/28
 * 目录形式的类路径
 */
public class DirEntry implements Entry {

    private Path absolutePath;

    public DirEntry(String path) {
        // 获取绝对路径
        this.absolutePath = Paths.get(path).toAbsolutePath();
    }

    @Override
    public byte[] readClass(String classpath) throws IOException {
        return Files.readAllBytes(absolutePath.resolve(classpath));
    }

    @Override
    public String toString() {
        return this.absolutePath.toString();
    }
}

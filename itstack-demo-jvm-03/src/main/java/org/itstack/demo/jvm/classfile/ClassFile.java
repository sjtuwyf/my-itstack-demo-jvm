package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * @author ssqswyf
 * @date 2022/2/28
 */
public class ClassFile {

    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantpool;
    private int accessFlags;
    private int thisClassIdx;
    private int supperClassIdx;
    private int[] interfaces;
    private MemberInfo[] fields;
    private MemberInfo[] methods;
    private AttributeInfo[] attributes;

    public ClassFile(byte[] classData) {
        ClassReader reader = new ClassReader(classData);
        this.readAndCheckMagic(reader);
        this.readAndCheckVersion(reader);
        this.constantpool = this.readConstantInfo(reader);
        this.accessFlags = reader.readUint16();
        this.thisClassIdx = reader.readUint16();
        this.supperClassIdx = reader.readUint16();
        this.interfaces = reader.readUint16s();
        this.fields = MemberInfo.readMembers(reader, constantpool);
        this.methods = MemberInfo.readMembers(reader, constantpool);
        this.attributes = AttributeInfo.readAttributes(reader, constantpool);
    }

    private void readAndCheckMagic(ClassReader reader) {
        long magic = reader.readUint32();
        if (magic != (0xCAFEBABE & 0x0FFFFFFFFL)) {
            throw new ClassFormatError("magic!");
        }
    }

    private void readAndCheckVersion(ClassReader reader) {
        this.minorVersion = reader.readUint16();
        this.majorVersion = reader.readUint16();
        switch (this.majorVersion) {
            case 45:
                return;
            case 46:
            case 47:
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
                if (this.minorVersion == 0) {
                    return;
                }
        }
        throw new UnsupportedClassVersionError();
    }

    private ConstantPool readConstantInfo(ClassReader reader) {
        return new ConstantPool(reader);
    }

    public int minorVersion() {
        return this.minorVersion;
    }

    public int majorVersion() {
        return this.majorVersion;
    }

    public ConstantPool constantPool() {
        return this.constantpool;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public MemberInfo[] fields() {
        return this.fields;
    }

    public MemberInfo[] methods() {
        return this.methods;
    }

    public String className() {
        return this.constantpool.getClassName(this.thisClassIdx);
    }

    public String superClassName() {
        if (this.supperClassIdx <= 0) {
            return "";
        }
        return this.constantpool.getClassName(this.supperClassIdx);
    }

    public String[] interfaceNames() {
        String[] interfaceNames = new String[this.interfaces.length];
        for (int i = 0; i < this.interfaces.length; i++) {
            interfaceNames[i] = this.constantpool.getClassName(interfaces[i]);
        }
        return interfaceNames;
    }


}

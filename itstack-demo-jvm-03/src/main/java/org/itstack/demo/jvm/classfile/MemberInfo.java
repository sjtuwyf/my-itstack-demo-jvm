package org.itstack.demo.jvm.classfile;

import org.itstack.demo.jvm.classfile.attributes.AttributeInfo;
import org.itstack.demo.jvm.classfile.attributes.impl.CodeAttribute;
import org.itstack.demo.jvm.classfile.attributes.impl.ConstantValueAttribute;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * @author ssqswyf
 * @date 2022/2/28
 */
public class MemberInfo {

    private ConstantPool constantpool;
    private int accessFlags;
    private int nameIdx;
    private int descriptorIdx;
    private AttributeInfo[] attributes;

    private MemberInfo(ClassReader reader, ConstantPool constantpool) {
        this.constantpool = constantpool;
        this.accessFlags = reader.readUint16();
        this.nameIdx = reader.readUint16();
        this.descriptorIdx = reader.readUint16();
        this.attributes = AttributeInfo.readAttributes(reader, constantpool);
    }

    static MemberInfo[] readMembers(ClassReader reader, ConstantPool constantpool) {
        int fieldCount = reader.readUint16();
        MemberInfo[] fields = new MemberInfo[fieldCount];
        for (int i = 0; i < fieldCount; i++) {
            fields[i] = new MemberInfo(reader, constantpool);
        }
        return fields;
    }

    public int accessFlags() {
        return this.accessFlags;
    }

    public String name() {
        return this.constantpool.getUTF8(this.descriptorIdx);
    }

    public String descriptor() {
        return this.constantpool.getUTF8(this.descriptorIdx);
    }

    public CodeAttribute codeAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof CodeAttribute) {
                return (CodeAttribute) attrInfo;
            }
        }
        return null;
    }

    public ConstantValueAttribute ConstantValueAttribute() {
        for (AttributeInfo attrInfo : attributes) {
            if (attrInfo instanceof ConstantValueAttribute) {
                return (ConstantValueAttribute) attrInfo;
            }
        }
        return null;
    }

}

package org.itstack.demo.jvm.classfile.attributes;

import org.itstack.demo.jvm.classfile.ClassReader;
import org.itstack.demo.jvm.classfile.attributes.impl.*;
import org.itstack.demo.jvm.classfile.constantpool.ConstantPool;

/**
 * @author ssqswyf
 * @date 2022/2/28
 */
public interface AttributeInfo {

    void readInfo(ClassReader reader);

    static AttributeInfo[] readAttributes(ClassReader reader, ConstantPool constantpool) {
        int attributesCount = reader.readUint16();
        AttributeInfo[] attributes = new AttributeInfo[attributesCount];
        for (int i = 0; i < attributesCount; i++) {
            attributes[i] = readAttribute(reader, constantpool);
        }
        return attributes;
    }

    static AttributeInfo readAttribute(ClassReader reader, ConstantPool constantpool) {
        int attrNameIdx = reader.readUint16();
        String attrName = constantpool.getUTF8(attrNameIdx);
        int attrLen = reader.readUint32TInteger();
        AttributeInfo attrInfo = newAttributeInfo(attrName,attrLen,constantpool);
        attrInfo.readInfo(reader);
        return attrInfo;

    }

    static AttributeInfo newAttributeInfo(String attrName, int attrLen, ConstantPool constantPool) {
        switch (attrName) {
            case "BootstrapMethods":
                return new BootstrapMethodsAttribute();
            case "Code":
                return new CodeAttribute(constantPool);
            case "ConstantValue":
                return new ConstantValueAttribute();
            case "Deprecated":
                return new DeprecatedAttribute();
            case "EnclosingMethod":
                return new EnclosingMethodAttribute(constantPool);
            case "Exceptions":
                return new ExceptionsAttribute();
            case "InnerClasses":
                return new InnerClassesAttribute();
            case "LineNumberTable":
                return new LineNumberTableAttribute();
            case "LocalVariableTable":
                return new LocalVariableTableAttribute();
            case "LocalVariableTypeTable":
                return new LocalVariableTypeTableAttribute();
            // case "MethodParameters":
            // case "RuntimeInvisibleAnnotations":
            // case "RuntimeInvisibleParameterAnnotations":
            // case "RuntimeInvisibleTypeAnnotations":
            // case "RuntimeVisibleAnnotations":
            // case "RuntimeVisibleParameterAnnotations":
            // case "RuntimeVisibleTypeAnnotations":
            case "Signature":
                return new SignatureAttribute(constantPool);
            case "SourceFile":
                return new SourceFileAttribute(constantPool);
            // case "SourceDebugExtension":
            // case "StackMapTable":
            case "Synthetic":
                return new SyntheticAttribute();
            default:
                return new UnparsedAttribute(attrName, attrLen);
        }

    }

}

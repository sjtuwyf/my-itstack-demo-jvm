package org.itstack.demo.jvm.rtda;

/**
 * @author ssqswyf
 * @date 2022/3/1
 * 栈帧
 */
public class Frame {

    /**
     * stack is implemented as linked list
     */
    Frame lower;

    /**
     * 局部变量表
     */
    private LocalVars localVars;

    /**
     * 操作数栈
     */
    private OperandStack operandStack;

    public Frame(int maxLocals,int maxStack) {
        this.localVars = new LocalVars(maxLocals);
        this.operandStack = new OperandStack(maxStack);
    }

    public LocalVars localVars() {
        return localVars;
    }

    public OperandStack operandStack() {
        return operandStack;
    }
}

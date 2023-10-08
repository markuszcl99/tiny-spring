package com.tiny.spring.beans.factory;

import com.sun.istack.internal.Nullable;

import java.util.*;

/**
 * @author: markus
 * @date: 2023/10/8 11:06 PM
 * @Description: 构造器参数集合抽象
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new ArrayList<>();

    public ArgumentValues() {
    }

    public void addArgumentValue(ArgumentValue argumentValue) {
        this.argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index) {
        ArgumentValue argumentValue = this.argumentValueList.get(index);
        return argumentValue;
    }

    public int getArgumentCount() {
        return (this.argumentValueList.size());
    }

    public boolean isEmpty() {
        return (this.argumentValueList.isEmpty());
    }
}

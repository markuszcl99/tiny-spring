package com.tiny.spring.beans;

import com.tiny.spring.beans.factory.PropertyValue;
import com.tiny.spring.beans.factory.PropertyValues;

import java.beans.PropertyEditor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: markus
 * @date: 2023/10/29 11:32 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class BeanWrapperImpl extends PropertyEditorRegistrySupport {
    /**
     * 目标对象
     */
    Object wrappedObject;
    Class<?> clazz;
    PropertyValues pvs;

    public BeanWrapperImpl(Object wrappedObject) {
        registerDefaultEditors();
        this.wrappedObject = wrappedObject;
        this.clazz = wrappedObject.getClass();
    }

    public void setBeanInstance(Object obj) {
        this.wrappedObject = obj;
    }

    public Object getBeanInstance() {
        return this.wrappedObject;
    }

    public void setPropertyValues(PropertyValues pvs) {
        this.pvs = pvs;
        for (PropertyValue propertyValue : this.pvs.getPropertyValues()) {
            setPropertyValue(propertyValue);
        }
    }

    public void setPropertyValue(PropertyValue pv) {
        BeanPropertyHandler handler = new BeanPropertyHandler(pv.getName());
        PropertyEditor editor = getPropertyEditor(handler.getPropertyClz());
        editor.setAsText((String) pv.getValue());
        handler.setValue(editor.getValue());
    }

    private PropertyEditor getPropertyEditor(Class<?> clazz) {
        if (hasCustomEditorForElement(clazz)) {
            return getCustomEditor(clazz);
        } else {
            return getDefaultEditor(clazz);
        }
    }

    class BeanPropertyHandler {
        Method writeMethod = null;
        Method readMethod = null;
        Class<?> propertyClz = null;

        public Class<?> getPropertyClz() {
            return propertyClz;
        }

        public BeanPropertyHandler(String propertyName) {
            try {
                Field field = clazz.getDeclaredField(propertyName);
                propertyClz = field.getType();
                this.writeMethod = clazz.getDeclaredMethod("set" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1), propertyClz);
                this.readMethod = clazz.getDeclaredMethod("get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1));
            } catch (NoSuchFieldException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        public Object getValue() {
            Object result = null;
            readMethod.setAccessible(true);
            try {
                result = readMethod.invoke(wrappedObject);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            return result;
        }

        public void setValue(Object value) {
            writeMethod.setAccessible(true);
            try {
                writeMethod.invoke(wrappedObject, value);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

}

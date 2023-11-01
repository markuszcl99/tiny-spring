package com.tiny.spring.web.http.convert;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: markus
 * @date: 2023/10/31 11:12 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DefaultObjectMapper implements ObjectMapper {

    private String dateFormat = "yyyy-MM-dd";
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);

    private String decimalFormat = "#,##0.00";
    private DecimalFormat decimalFormatter = new DecimalFormat(decimalFormat);

    @Override
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        this.dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
    }

    @Override
    public void setDecimalFormat(String decimalFormat) {
        this.decimalFormat = decimalFormat;
        this.decimalFormatter = new DecimalFormat(decimalFormat);
    }

    @Override
    public String writeValuesAsString(Object obj) {
        String jsonStr = "{";
        Class<?> clz = obj.getClass();
        Field[] fields = clz.getDeclaredFields();
        // 对返回对象中的每一个属性进行格式转换
        for (Field field : fields) {
            String sField = "";
            Object value = null;
            Class<?> type = null;
            String name = field.getName();
            String strValue = "";
            field.setAccessible(true);
            try {
                value = field.get(obj);
                type = field.getType();
                // 针对不同的数据类型进行格式替换
                if (value instanceof Date) {
                    LocalDate localDate = ((Date) value).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    strValue = localDate.format(this.dateTimeFormatter);
                } else if (value instanceof BigDecimal || value instanceof Double || value instanceof Float) {
                    strValue = this.decimalFormatter.format(value);
                } else {
                    strValue = value.toString();
                }
                // 拼接 json 串
                if (jsonStr.equals("{")) {
                    sField = "\"" + name + "\":\"" + strValue + "\"";
                } else {
                    sField = ",\"" + name + "\":\"" + strValue + "\"";
                }
                jsonStr += sField;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        jsonStr += "}";
        return jsonStr;
    }
}

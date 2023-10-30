package com.tiny.spring.test.mvc.editor;

import com.tiny.spring.web.bind.WebDataBinder;
import com.tiny.spring.web.bind.support.WebBindingInitializer;

import java.util.Date;

/**
 * @author: markus
 * @date: 2023/10/30 11:10 PM
 * @Description:
 * @Blog: https://markuszhang.com
 * It's my honor to share what I've learned with you!
 */
public class DateInitializer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class, "yyyy-MM-dd", false));
    }
}

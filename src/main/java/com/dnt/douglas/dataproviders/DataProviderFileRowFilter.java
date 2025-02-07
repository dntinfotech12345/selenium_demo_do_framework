package com.dnt.douglas.dataproviders;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(java.lang.annotation.ElementType.METHOD)
public @interface DataProviderFileRowFilter {
 
    String file() default "";
    String sql() default "";
}

package com.training.annotation.inherited;

import java.lang.annotation.*;

/**
 * george 2018/12/18 11:22
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Dbtable {
    String name() default "";
}

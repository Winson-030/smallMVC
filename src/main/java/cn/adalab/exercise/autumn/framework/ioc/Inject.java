package cn.adalab.exercise.autumn.framework.ioc;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Target({ CONSTRUCTOR})
public @interface Inject {
}
//
//@Target({METHOD, FIELD, TYPE, CONSTRUCTOR})
//public @interface Inject {
//}

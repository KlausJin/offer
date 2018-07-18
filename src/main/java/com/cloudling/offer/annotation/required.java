package com.cloudling.offer.annotation;

import com.mysql.fabric.xmlrpc.base.Param;
import com.mysql.fabric.xmlrpc.base.Params;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
public @interface required {

}

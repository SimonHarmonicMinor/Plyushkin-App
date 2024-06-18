package com.plyushkin.util;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import org.springframework.transaction.annotation.Transactional;

@Retention(RUNTIME)
@Transactional(rollbackFor = Throwable.class)
public @interface WriteTransactional {

}

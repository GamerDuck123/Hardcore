package com.hardcore.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
	public String command() default "";
	public String permission() default "";
	public String[] aliases() default "";
	public String description() default "";
	public String noperms() default "&cNot enough permissions to run this command";
}

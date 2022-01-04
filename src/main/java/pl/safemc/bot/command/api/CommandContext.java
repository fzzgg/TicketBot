package pl.safemc.bot.command.api;

import net.dv8tion.jda.api.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Kamil on 03.01.2022
 */

@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandContext {

    String name();

    Permission[] permissions() default Permission.UNKNOWN;

}
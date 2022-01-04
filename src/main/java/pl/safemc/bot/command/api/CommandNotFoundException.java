package pl.safemc.bot.command.api;

/**
 * Created by Kamil on 03.01.2022
 */

public final class CommandNotFoundException extends RuntimeException {

    public CommandNotFoundException(String message) {
        super(message);
    }

}
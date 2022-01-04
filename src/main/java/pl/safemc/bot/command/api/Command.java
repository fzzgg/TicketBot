package pl.safemc.bot.command.api;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.safemc.bot.TicketBot;

/**
 * Created by Kamil on 04.01.2022
 */

public abstract class Command {

    protected final TicketBot ticketBot = TicketBot.getInstance();

    private String name;
    private Permission[] permissions;

    public abstract void execute(MessageReceivedEvent event, User user, String... args);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissions(Permission[] permissions) {
        this.permissions = permissions;
    }

    public Permission[] getPermissions() {
        return permissions;
    }

}

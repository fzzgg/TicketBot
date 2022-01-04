package pl.safemc.bot.command.api;

import com.google.common.reflect.ClassPath;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.safemc.bot.TicketBot;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by Kamil on 04.01.2022
 */

public final class CommandManager {

    private final TicketBot ticketBot;

    public CommandManager(TicketBot ticketBot) {
        this.ticketBot = ticketBot;
    }

    private final Set<Command> commands = new HashSet<>();

    public Command findCommand(String name) {
        return commands
                .stream()
                .filter(command -> command.getName().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    private void registerCommand(Command command) {
        CommandContext context = command.getClass().getAnnotation(CommandContext.class);
        if (Objects.isNull(context)) {
            throw new CommandNotFoundException("Command must have \"CommandContext\"");
        }

        System.out.printf("Registered command with name %s%n", context.name());
        command.setName(String.format(ticketBot.getPrefix() + "%s", context.name()));
        command.setPermissions(context.permissions());
        commands.add(command);
    }

    public void initCommands() {
        try {
            for (ClassPath.ClassInfo classInfo : ClassPath.from(getClass().getClassLoader())
                    .getTopLevelClasses("pl.safemc.bot.command")
                    .asList()) {

                var aConstructor = classInfo.load().getConstructor();
                aConstructor.setAccessible(true);
                registerCommand((Command) aConstructor.newInstance());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

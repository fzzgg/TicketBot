package pl.safemc.bot.util;

import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;

/**
 * Created by Kamil on 04.01.2022
 */

public final class EmbedUtil {

    public static EmbedBuilder create(String title, String footer, Color color) {
        return new EmbedBuilder()
                .setColor(color)
                .setTitle(title, null)
                .setDescription(footer);
    }

}

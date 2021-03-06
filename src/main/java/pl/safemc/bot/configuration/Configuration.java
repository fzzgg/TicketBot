package pl.safemc.bot.configuration;

import com.moandjiezana.toml.Toml;

import java.io.File;

/**
 * Created by Kamil on 03.01.2022
 */

public final class Configuration {

    private Toml toml;

    public void load(File file) {
        toml = new Toml().read(file);
    }

    public String getString(String option) {
        return toml.getString(option);
    }

}
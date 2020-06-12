package com.github.frcsty.districtcore.plugins.tokens.token;

import com.github.frcsty.districtcore.DistrictCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class FileManager {

    private final DistrictCore core;
    private final File folder;
    private FileConfiguration tokenConfig;
    private File file;

    public FileManager(final DistrictCore core) {
        this.core = core;
        this.folder = core.getDataFolder();
        this.tokenConfig = YamlConfiguration.loadConfiguration(getTokenFile());
        this.file = new File(core.getDataFolder(), "tokens.yml");
    }

    private File getTokenFile() {
        if (this.file == null) {
            this.file = new File(folder, "tokens.yml");
        }
        return this.file;
    }

    public final void saveTokenFile() {
        try {
            tokenConfig.save(getTokenFile());
        } catch (IOException ex) {
            core.getLogger().log(Level.WARNING, "Failed to save tokens.yml file!", ex);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public final void createTokenFile() {
        if (!getTokenFile().exists()) {
            try {
                this.getTokenFile().createNewFile();
            } catch (IOException ex) {
                core.getLogger().log(Level.WARNING, "Failed to create file tokens.yml!", ex);
            }

            createFileSection();
            saveTokenFile();
        }
    }

    private void createFileSection() {
        if (!tokenConfig.isSet("users")) {
            tokenConfig.createSection("users");
            saveTokenFile();
        }
    }

    final FileConfiguration getTokenConfig() {
        if (this.tokenConfig == null) {
            this.tokenConfig = YamlConfiguration.loadConfiguration(getTokenFile());
        }
        return this.tokenConfig;
    }
}
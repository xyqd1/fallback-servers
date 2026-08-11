package ru.xyqd.xyfallback;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class ConfigManager {

    private final Plugin plugin;
    private Configuration config;
    private File file;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        
        file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = plugin.getResourceAsStream("config.yml")) {
                if (in != null) {
                    Files.copy(in, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Could not create config.yml", e);
            }
        }
        
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not load config.yml", e);
        }
    }

    public Map<String, String> getCommands() {
        Map<String, String> commands = new HashMap<>();
        if (config != null) {
            Configuration cmdsNode = config.getSection("commands");
            if (cmdsNode != null) {
                for (String key : cmdsNode.getKeys()) {
                    commands.put(key, cmdsNode.getString(key));
                }
            }
        }
        return commands;
    }

    public List<String> getFallbackServers() {
        if (config != null) {
            return config.getStringList("fallback.servers");
        }
        return List.of("lobby");
    }

    public boolean isKickIfAllDown() {
        if (config != null) {
            return config.getBoolean("fallback.kick-if-all-down", true);
        }
        return true;
    }

    public String getMessage(String key, String def) {
        String msg = def;
        if (config != null) {
            msg = config.getString("messages." + key, def);
        }
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', msg);
    }

    public boolean isBStatsEnabled() {
        if (config != null) {
            return config.getBoolean("bstats", true);
        }
        return true;
    }
}

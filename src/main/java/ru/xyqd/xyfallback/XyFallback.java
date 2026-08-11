package ru.xyqd.xyfallback;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.bstats.bungeecord.Metrics;
import ru.xyqd.xyfallback.commands.DynamicCommand;
import ru.xyqd.xyfallback.commands.ReloadCommand;
import ru.xyqd.xyfallback.listeners.KickListener;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class XyFallback extends Plugin {

    private ConfigManager configManager;
    private final Set<DynamicCommand> registeredDynamicCommands = new HashSet<>();

    @Override
    public void onEnable() {
        configManager = new ConfigManager(this);
        configManager.load();

        if (configManager.isBStatsEnabled()) {
            int pluginId = 33300;
            new Metrics(this, pluginId);
        }

        PluginManager pluginManager = getProxy().getPluginManager();
        pluginManager.registerCommand(this, new ReloadCommand(this));
        pluginManager.registerListener(this, new KickListener(this));

        registerDynamicCommands();

        getLogger().info("XyFallback successfully enabled on BungeeCord!");
    }

    public void reloadPlugin() {
        configManager.load();

        PluginManager pluginManager = getProxy().getPluginManager();
        for (DynamicCommand cmd : registeredDynamicCommands) {
            pluginManager.unregisterCommand(cmd);
        }
        registeredDynamicCommands.clear();

        registerDynamicCommands();
    }

    private void registerDynamicCommands() {
        PluginManager pluginManager = getProxy().getPluginManager();
        Map<String, String> commands = configManager.getCommands();

        for (Map.Entry<String, String> entry : commands.entrySet()) {
            String alias = entry.getKey();
            String target = entry.getValue();

            DynamicCommand cmd = new DynamicCommand(this, alias, target);
            pluginManager.registerCommand(this, cmd);
            registeredDynamicCommands.add(cmd);
        }
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}

package ru.xyqd.xyfallback.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;
import ru.xyqd.xyfallback.XyFallback;

import java.util.Collections;

public class ReloadCommand extends Command implements TabExecutor {

    private final XyFallback plugin;

    public ReloadCommand(XyFallback plugin) {
        super("xyfallback", "xyfallback.admin", "xyf");
        this.plugin = plugin;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("xyfallback.admin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            String msg = plugin.getConfigManager().getMessage("no_permission", "§cУ вас нет прав.");
            sender.sendMessage(TextComponent.fromLegacyText(msg));
            return;
        }

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            plugin.reloadPlugin();
            String msg = plugin.getConfigManager().getMessage("reload_success", "§aПлагин XyFallback успешно перезагружен!");
            sender.sendMessage(TextComponent.fromLegacyText(msg));
        } else {
            sender.sendMessage(TextComponent.fromLegacyText("§eUsage: /xyfallback reload"));
        }
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}

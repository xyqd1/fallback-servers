package ru.xyqd.xyfallback.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import ru.xyqd.xyfallback.XyFallback;

public class DynamicCommand extends Command {

    private final XyFallback plugin;
    private final String targetServerName;

    public DynamicCommand(XyFallback plugin, String alias, String targetServerName) {
        super(alias);
        this.plugin = plugin;
        this.targetServerName = targetServerName;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(TextComponent.fromLegacyText("§cOnly players can use this command!"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) sender;
        ServerInfo targetServer = plugin.getProxy().getServerInfo(targetServerName);

        if (targetServer == null) {
            sender.sendMessage(TextComponent.fromLegacyText("§cTarget server '" + targetServerName + "' does not exist!"));
            return;
        }

        if (player.getServer() != null && player.getServer().getInfo().getName().equalsIgnoreCase(targetServerName)) {
            String msg = plugin.getConfigManager().getMessage("already_connected", "§cВы уже находитесь на этом сервере!");
            player.sendMessage(TextComponent.fromLegacyText(msg));
            return;
        }

        String connectMsg = plugin.getConfigManager().getMessage("connecting", "§aПодключение к серверу §e%server%§a...")
                .replace("%server%", targetServerName);
        player.sendMessage(TextComponent.fromLegacyText(connectMsg));
        
        player.connect(targetServer);
    }
}

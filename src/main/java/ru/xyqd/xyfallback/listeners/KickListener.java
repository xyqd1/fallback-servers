package ru.xyqd.xyfallback.listeners;

import net.md_5.bungee.api.AbstractReconnectHandler;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import ru.xyqd.xyfallback.XyFallback;

import java.util.List;

public class KickListener implements Listener {

    private final XyFallback plugin;

    public KickListener(XyFallback plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onServerKick(ServerKickEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo kickedFrom = event.getKickedFrom();
        String serverName = kickedFrom.getName();

        String reason = BaseComponent.toLegacyText(event.getKickReasonComponent());
        if (reason == null || reason.trim().isEmpty()) {
            reason = "Server disconnected";
        } else {
            reason = reason.trim();
        }

        List<String> fallbacks = plugin.getConfigManager().getFallbackServers();
        boolean kickIfAllDown = plugin.getConfigManager().isKickIfAllDown();

        ServerInfo targetFallback = null;
        for (String fallbackName : fallbacks) {
            ServerInfo rs = plugin.getProxy().getServerInfo(fallbackName);
            if (rs != null && !rs.getName().equals(serverName)) {
                targetFallback = rs;
                break;
            }
        }

        if (targetFallback != null) {
            String msg = plugin.getConfigManager().getMessage("kicked_to_fallback", "§cВы были отключены от сервера §e%server%§c. Причина: §e%reason%\n§aВы были перемещены в лобби.")
                    .replace("%reason%", reason)
                    .replace("%server%", serverName);
            
            event.setCancelled(true);
            event.setCancelServer(targetFallback);
            player.sendMessage(TextComponent.fromLegacyText(msg));
        } else {
            if (kickIfAllDown) {
                String kickMsg = plugin.getConfigManager().getMessage("kick_all_down", "§cВы были кикнуты с сервера §e%server%§c: §e%reason%\n§cРезервные сервера также недоступны.")
                        .replace("%reason%", reason)
                        .replace("%server%", serverName);
                
                event.setKickReasonComponent(TextComponent.fromLegacyText(kickMsg));
            }
        }
    }
}

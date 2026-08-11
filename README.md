# XyFallback

<div align="center">
  A lightweight and powerful BungeeCord/Waterfall plugin for fallback servers and custom hub commands.
  <br />
  Легкий и мощный плагин для BungeeCord/Waterfall для настройки резервных серверов и кастомных команд.
</div>

---

## 🌟 Features / Особенности

- **Automatic Fallback (Авто-редирект):** Automatically redirects players to a fallback server (like a lobby) when they get kicked or when their current server crashes. / *Автоматически перенаправляет игроков в лобби при кике или падении сервера.*
- **Multiple Fallback Servers (Несколько резервных серверов):** Supports a priority list of fallback servers. If one is down, it tries the next one. / *Поддерживает список резервных серверов. Если первый недоступен, плагин попытается перевести на следующий.*
- **Dynamic Commands (Динамические команды):** Easily create custom commands (e.g., `/hub`, `/lobby`, `/survival`) to connect players to specific servers instantly. / *Легко создавайте кастомные команды (например, `/hub`, `/lobby`), чтобы мгновенно отправлять игроков на нужные серверы.*
- **Fully Customizable (Полная кастомизация):** All messages are fully customizable with legacy color code support (`&a`, `&c`, etc.) and placeholders. / *Все сообщения полностью настраиваются, поддерживают цветовые коды (`&a`, `&c` и т.д.) и плейсхолдеры.*
- **In-game Reload (Перезагрузка в игре):** Reload your configuration on the fly without restarting the proxy. / *Перезагружайте конфигурацию на лету без перезапуска прокси.*

---

## ⚙️ Commands & Permissions / Команды и Права

| Command / Команда | Permission / Право | Description / Описание |
| :--- | :--- | :--- |
| `/xyfallback reload` (or `/xyf reload`) | `xyfallback.admin` | Reloads the plugin configuration. / *Перезагружает конфигурацию плагина.* |
| `/*` (Custom commands from config) | *None* | Connects the player to the specified server. / *Подключает игрока к указанному серверу.* |

*(Note: The `/xyf` command is completely hidden from the tab-complete list for players without the `xyfallback.admin` permission.)*

---

## 📝 Configuration / Конфигурация

The default `config.yml` / Стандартный `config.yml`:

```yaml
# [RU] Команда: Сервер. Пример: 'hub: lobby' означает, что при вводе /hub игрок будет перемещен на сервер lobby.
# [EN] Command: Target Server. Example: 'hub: lobby' means typing /hub sends the player to the lobby server.
commands:
  hub: "lobby-01"
  lobby: "lobby-02"
  vanilla: "vanilla"

# [RU] Резервные серверы. При падении или кике плагин попытается перевести игрока на первый доступный сервер из этого списка.
# [EN] Fallback servers. Upon crash or kick, the plugin will try to move the player to the first available server in this list.
fallback:
  servers:
    - "lobby-01"
    - "lobby-02"
  
  # [RU] Кикать ли игрока с прокси, если все резервные серверы недоступны.
  # [EN] Whether to disconnect the player from the proxy if all fallback servers are down.
  kick-if-all-down: true

# [RU] Сообщения. Плейсхолдеры:
# %server% - Целевой сервер (при подключении) или сервер, с которого кикнуло (при падении/кике).
# %reason% - Причина кика (используется только в сообщениях о кике).
# [EN] Messages. Placeholders:
# %server% - Target server (when connecting) or the server the player was kicked from (on crash/kick).
# %reason% - Kick reason (only used in kick messages).
messages:
  connecting: "&aПодключение к серверу &e%server%&a..."
  already_connected: "&cВы уже находитесь на этом сервере!"
  kicked_to_fallback: "&cВы были отключены от сервера &e%server%&c. Причина: &e%reason%\n&aВы были перемещены в лобби."
  kick_all_down: "&cВы были кикнуты с сервера &e%server%&c: &e%reason%\n&cРезервные сервера также недоступны."
  no_permission: "&cУ вас нет прав."
  reload_success: "&aПлагин XyFallback успешно перезагружен!"
```

---

## 🚀 Installation / Установка

1. Download the latest `XyFallback-1.1.jar` from the Releases page. / *Скачайте последний `XyFallback-1.1.jar` со страницы Releases.*
2. Place the file into the `plugins` folder of your **BungeeCord**, **Waterfall**, or **FlameCord** server. / *Поместите файл в папку `plugins` вашего **BungeeCord**, **Waterfall** или **FlameCord** сервера.*
3. Restart your proxy. / *Перезапустите прокси-сервер.*
4. Edit `plugins/XyFallback/config.yml` to your liking. / *Настройте `plugins/XyFallback/config.yml` под свои нужды.*
5. Run `/xyf reload` from the console or in-game (requires `xyfallback.admin`) to apply changes. / *Пропишите `/xyf reload` в консоли или в игре (требуется `xyfallback.admin`), чтобы применить изменения.*

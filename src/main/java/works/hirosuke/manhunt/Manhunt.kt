package works.hirosuke.manhunt

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

class Manhunt : JavaPlugin(), Listener {

    private val compassTargetData = mutableMapOf<Player, Player>()

    override fun onEnable() {
        // Plugin startup logic
        logger.info("plugin has loaded.")
        this.server.pluginManager.registerEvents(this, this)
    }

    override fun onDisable() {
        // Plugin shutdown logic
        logger.info("plugin has unloaded.")
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        if (!event.hasItem() || event.action !in listOf(Action.RIGHT_CLICK_AIR, Action.RIGHT_CLICK_BLOCK)) return
        if (event.item.type != Material.COMPASS) return

        val player = event.player
        val meta = event.item.itemMeta
        val onlinePlayers = this.server.onlinePlayers.toList().sortedByDescending { it.name }
        val index = onlinePlayers.indexOf(compassTargetData[player]) + 1

        compassTargetData[player] = onlinePlayers[if (index > onlinePlayers.lastIndex) 0 else index]
        player.compassTarget = compassTargetData[player]?.location

        meta.displayName = "§rTarget to ${compassTargetData[player]?.name}"
        event.item.itemMeta = meta
    }

    @EventHandler
    fun onMove(event: PlayerMoveEvent) {
        val player = event.player
        compassTargetData.filter { it.value == player }.forEach {
            it.key.compassTarget = player.location
        }
    }

    @EventHandler
    fun onRespawn(event: PlayerRespawnEvent) {
        val player = event.player
        player.inventory.addItem(ItemStack(Material.COMPASS))
    }

    @EventHandler
    fun onDeath(event: PlayerDeathEvent) {
        val player = event.entity
        if (player.scoreboard.getTeam("escaper") != null)
        if (!player.scoreboard.getTeam("escaper").hasPlayer(player)) return
        player.gameMode = GameMode.SPECTATOR
    }

    @EventHandler
    fun onChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        if (event.message.startsWith('!')) {
            event.message = "§a${event.message.substringAfter('!')}"

            event.recipients.removeAll(this.server.onlinePlayers.toSet())
            player.scoreboard.getTeam("escaper").players.forEach { event.recipients.add(Bukkit.getPlayer(it.uniqueId)) }
        }
    }
}
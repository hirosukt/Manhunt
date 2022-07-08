package works.hirosuke.manhunt.commands.impl

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import works.hirosuke.manhunt.Manhunt.Companion.plugin
import works.hirosuke.manhunt.commands.Command

object TellhereCommand : Command("tellhere") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player) return
        val loc = sender.location
        Bukkit.getPlayer(args[0])?.sendMessage("§7§oLocation from ${sender.displayName}: ${loc.x} / ${loc.y} / ${loc.z}")
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        return if (args.isNotEmpty()) {
            plugin.server.onlinePlayers.map { it.displayName }
        } else emptyList()
    }
}
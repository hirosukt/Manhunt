package works.hirosuke.manhunt.commands.impl

import org.bukkit.command.CommandSender
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import works.hirosuke.manhunt.commands.Command

object ResetCommand : Command("reset") {

    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player) return
        sender.server.onlinePlayers.filterNot { it == sender }.forEach {
            it.inventory.clear()
            it.health = 0.0
        }

        sender.world.entities.filterIsInstance(Item::class.java).forEach(Item::remove)
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}
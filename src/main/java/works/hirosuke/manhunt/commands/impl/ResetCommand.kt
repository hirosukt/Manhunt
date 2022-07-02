package works.hirosuke.manhunt.commands.impl

import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import works.hirosuke.manhunt.commands.Command

object ResetCommand : Command("reset") {

    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player) return
        sender.server.onlinePlayers.filterNot { it == sender }.forEach {
            it.inventory.clear()
            it.bedSpawnLocation = sender.location
            it.health = 0.0
            it.scoreboard.teams.forEach(Team::unregister)
        }

        sender.world.entities.forEach(Entity::remove)
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}
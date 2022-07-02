package works.hirosuke.manhunt.commands.impl

import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.Team
import works.hirosuke.manhunt.commands.Command

object RegisterCommand : Command("registerTeam") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        if (sender !is Player) return

        sender.scoreboard.teams.forEach(Team::unregister)

        val team = sender.scoreboard.registerNewTeam("escaper")
        team.color = ChatColor.RED
        team.setAllowFriendlyFire(false)
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}
package works.hirosuke.manhunt.commands.impl

import org.bukkit.command.CommandSender
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import works.hirosuke.manhunt.Manhunt.Companion.plugin
import works.hirosuke.manhunt.commands.Command

object PrepareCommand : Command("prepare") {
    override fun onCommand(sender: CommandSender, label: String, args: Array<out String>) {
        plugin.server.onlinePlayers.filterNot { (it.scoreboard.getTeam("escaper") ?: return).hasEntry(it.displayName) }.forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 1200, 9))
            it.addPotionEffect(PotionEffect(PotionEffectType.BLINDNESS, 200, 9))
            it.addPotionEffect(PotionEffect(PotionEffectType.JUMP, 200, 136))
        }

        plugin.server.onlinePlayers.forEach {
            it.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 1200, 9))
            it.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, 1200, 9))
        }
    }

    override fun onTabComplete(sender: CommandSender, label: String, args: Array<out String>): List<String>? {
        TODO("Not yet implemented")
    }
}
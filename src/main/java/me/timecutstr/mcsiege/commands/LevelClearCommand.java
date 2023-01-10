package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LevelClearCommand implements CommandExecutor {
    GameManager gameManager = McSiege.getPlugin().getGameManager();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p) {
            if(p.hasPermission("MineSiege.clear")) {
                gameManager.Clear();
            }else {
                p.sendMessage("Vous n'avez pas la permission (MineSiege.clear) de faire cette commande");
            }
        } else {
            gameManager.Clear();
        }

        return true;
    }
}

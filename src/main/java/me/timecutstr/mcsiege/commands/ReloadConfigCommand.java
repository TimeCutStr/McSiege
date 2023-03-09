package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(sender instanceof Player p && !WorldCheck.worldCheck(p.getWorld())) {
            return false;
        }

            if (McSiege.getPlugin().getGameManager().getGameState() != GameState.LOBBY) {

                if (sender instanceof Player p) {
                    p.sendMessage("Attention il faut faire cette commande dans le lobby. /lobby pour passer au lobby");
                    return false;
                }

                System.out.println("Attention il faut faire cette commande dans le lobby. /lobby pour passer au lobby");
                return false;
            }

            if (sender instanceof Player p) {
                if (!p.hasPermission("MineSiege.reloadConfig")) {
                    p.sendMessage("Vous n'avez pas la permission (MineSiege.reloadConfig) de faire cette commande");
                    return false;
                }
            }

            McSiege.getPlugin().getGameManager().clear();
            McSiege.getPlugin().reloadConfig();
            McSiege.getPlugin().getGameManager().reload();

            return true;
        }


}

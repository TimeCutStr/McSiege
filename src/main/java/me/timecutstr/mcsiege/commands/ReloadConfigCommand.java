package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadConfigCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if(sender instanceof Player p) {
            if(p.hasPermission("MineSiege.reloadConfig")) {
                McSiege.getPlugin().getGameManager().Clear();
                McSiege.getPlugin().reloadConfig();
                McSiege.getPlugin().getGameManager().setGameState(GameState.LOBBY);
            }else {
                p.sendMessage("Vous n'avez pas la permission (MineSiege.reloadConfig) de faire cette commande");
                return false;
            }
        } else {
            McSiege.getPlugin().getGameManager().Clear();
            McSiege.getPlugin().reloadConfig();
            McSiege.getPlugin().getGameManager().setGameState(GameState.LOBBY);
        }

        return true;
    }

}

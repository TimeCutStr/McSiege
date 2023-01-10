package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LobbyCommand implements CommandExecutor {

    //TODO permission seulement op
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p) {
            if(p.hasPermission("MineSiege.lobby")) {
                McSiege.getPlugin().getGameManager().setGameState(GameState.LOBBY);
            }else {
                p.sendMessage("Vous n'avez pas la permission (MineSiege.lobby) de faire cette commande");
            }
        } else {
            McSiege.getPlugin().getGameManager().setGameState(GameState.LOBBY);
        }

        return true;
    }

}

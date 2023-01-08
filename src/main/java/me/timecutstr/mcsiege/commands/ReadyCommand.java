package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReadyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(McSiege.getPlugin().getGameManager().getGameState() == GameState.LOBBY && sender instanceof Player p) {
            McSiege.getPlugin().getGameManager().addPlayers(p);
            return true;
        }

        else {
            return false;
        }



    }
}

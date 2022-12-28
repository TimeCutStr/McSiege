package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        McSiege plugin = McSiege.getPlugin();

        if(plugin.getGameManager().getGameState() == GameState.LOBBY)
        {
            plugin.getGameManager().setGameState(GameState.WAVE1);
        } else {
            if(sender instanceof Player p) {
                p.sendMessage("Il faut être dans le lobby pour commencer le jeu");
            }
            else {
                System.out.println("Il faut être dans le lobby pour commencer le jeu");
            }
        }



        return true;
    }
}

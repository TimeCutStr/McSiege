package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetLocationCommand implements CommandExecutor {
    GameManager gameManager = GameManager.getGameManager();
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (command.getName()) {
            case "setL1" :
                if(sender instanceof Player) {
                    gameManager.setL1(((Player)sender).getLocation());
                }
            case "setL2" :
                if(sender instanceof Player) {
                    gameManager.setL2(((Player)sender).getLocation());
                }
        }


        return true;
    }
}

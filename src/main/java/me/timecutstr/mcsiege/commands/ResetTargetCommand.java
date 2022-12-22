package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ResetTargetCommand implements CommandExecutor {

    GameManager gameManager = GameManager.getGameManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //commande qui permet de reset manuelement la cible des zombies sur le centre du village
        gameManager.resetMonstreTarget();


        return true;
    }
}

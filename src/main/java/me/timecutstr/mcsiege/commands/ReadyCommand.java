package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReadyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(sender instanceof Player p && !WorldCheck.worldCheck(p.getWorld())) {
            return false;
        }

        GameManager gameManager = McSiege.getPlugin().getGameManager();

        if(gameManager.getGameState() == GameState.LOBBY && sender instanceof Player p) {
            McSiege.getPlugin().getGameManager().addPlayers(p);
            return true;
        }

        else if (!(sender instanceof Player)) {
            System.out.println("Cette commande ne peut pas être faite dans la console");
            return false;
        }

        else {
            return false;
        }

    }
}

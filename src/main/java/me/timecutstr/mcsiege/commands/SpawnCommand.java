package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    GameManager gameManager = McSiege.getPlugin().getGameManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p)  //On check si l'auteur de la commande est un joueur. Si oui on le cast dans la variable Player p
        {
            int nombreASpawn = 0;


            if(args.length == 0) {
                nombreASpawn = 1;
            }


            try {
                nombreASpawn = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                p.sendMessage("tu n'as pas rentré un nombre");
                p.sendMessage("Exemple : '/spawn' pour faire spawn un monstre et '/spawn 20' pour en faire spawn 20");

            }

            if(args.length > 1) {
                p.sendMessage("Tu as essayé de mettre trop d'arguments dans ta commande");
                return false;
            }
            //On set la location TODO : Faire un set locations sur un endroit avec un outil par exemple

            gameManager.spawn(nombreASpawn);

        }




        return true;
    }


}



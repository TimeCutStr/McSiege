package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class SpawnTarget implements CommandExecutor {

    GameManager gameManager = McSiege.getPlugin().getGameManager();

    Plugin plugin = McSiege.getPlugin(McSiege.class);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        switch (command.getName()) {

            //Commande /Spawntarget qui fait spawn un villageois qui sera la cible des monstre
            case "spawnTarget" :
                if (sender instanceof Player p) { //On check si l'auteur de la commande est un joueur. Si oui on le cast dans la variable Player p


                }
            break;

            //Commande /SetTarget qui permet au GM de décider où il met le villageois

        }


        return true;
    }



}

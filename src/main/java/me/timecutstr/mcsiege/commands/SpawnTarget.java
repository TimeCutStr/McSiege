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

                    //On récupère la position enregistrée dans le fichier de config

                    double x = plugin.getConfig().getDouble("targetLocationX");
                    double y = plugin.getConfig().getDouble("targetLocationY");
                    double z = plugin.getConfig().getDouble("targetLocationZ");

                    Location spawnLocastion = new Location(p.getWorld(),x,y,z);

                    //On fait spawn un Villageois à l'endroit défini avec un nom et on désactive le Aware
                    Entity target = spawnLocastion.getWorld().spawnEntity(spawnLocastion, EntityType.VILLAGER);

                    target.setCustomName("Victime");
                    target.setCustomNameVisible(true);
                    p.sendMessage("La cible à spawn");

                    if (target instanceof Mob mob) {
                        mob.setAware(false);
                    }

                    //On enregistre la target dans le gameManager
                    gameManager.setTarget(target);
                }
            break;

            //Commande /SetTarget qui permet au GM de décider où il met le villageois
            case "setTarget":
                if (sender instanceof Player p) {
                    Location targetLocation = p.getLocation();
                    System.out.println(targetLocation.getX());

                    p.sendMessage(String.valueOf(plugin.getConfig().getDouble("targetLocation.x")));

                    plugin.getConfig().set("targetLocationX", targetLocation.getX());
                    plugin.getConfig().set("targetLocationY", targetLocation.getY());
                    plugin.getConfig().set("targetLocationZ", targetLocation.getZ());

                    p.sendMessage(String.valueOf(plugin.getConfig().getDouble("targetLocation.x")));


                }

            break;

        }


        return true;
    }



}

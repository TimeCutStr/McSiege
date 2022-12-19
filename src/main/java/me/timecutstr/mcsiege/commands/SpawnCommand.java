package me.timecutstr.mcsiege.commands;

import com.destroystokyo.paper.entity.Pathfinder;
import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manger.GameManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;

public class SpawnCommand implements CommandExecutor {

    GameManager gameManager = GameManager.getGameManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player p)  //On check si l'auteur de la commande est un joueur. Si oui on le cast dans la variable Player p
        {
            //On set la location TODO : Faire un set locations sur un endroit avec un outil par exemple

            Location spawnLocastion = new Location(p.getWorld(), -24.0, -60.0, 165.0);

            Entity monstre = spawnLocastion.getWorld().spawnEntity(spawnLocastion, EntityType.ZOMBIE);

            monstre.setCustomName("Philippe");
            monstre.setCustomNameVisible(true);
            p.sendMessage("Un zombie à spawn");


            if(gameManager.getTarget() == null)
            {
                System.out.println("pas de target");
            }  else {
                if(monstre instanceof Mob mob && gameManager.getTarget() instanceof LivingEntity target){
                    mob.setTarget(target);
                    p.sendMessage("La cible est " + gameManager.getTarget().name());


                /* Pathfinder path = mob.getPathfinder();

                System.out.println( path.getEntity().getName());

                if(path.moveTo(p.getLocation())) {
                    System.out.println("Path ok");
                } else {
                    System.out.println("Path fail");
                }

                ((Mob)path.getEntity()).lookAt(p.getLocation());*/

                }

            }


        }




        return true;
    }


}

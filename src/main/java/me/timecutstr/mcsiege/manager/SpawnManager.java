package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

import java.util.List;

public class SpawnManager {

    final GameManager gameManager;

    public SpawnManager(GameManager gameManager){
        this.gameManager = gameManager;
    }

    public void spawn (int nombreASpawn, EntityType type, Location location, Entity target, ListMonstreManager listMonstreManager)
    {

        while (nombreASpawn > 0 ) {


            Entity monstre = location.getWorld().spawnEntity(location, type);

            if (target == null) {
                System.out.println("pas de target");
            } else {
                if (monstre instanceof Mob mob && target instanceof LivingEntity t) {
                    mob.setTarget(t);
                    mob.setCustomName("MONSTRE !!!");
                    mob.setCustomNameVisible(true);
                    mob.setRemoveWhenFarAway(false);
                    listMonstreManager.addMonstre(mob);

                }
            }
            nombreASpawn --;
        }

    }

    public void spawnVictime ()
    {
        //On récupère la position enregistrée dans le fichier de config
        Location spawnLocation = McSiege.getPlugin().getConfig().getLocation("targetLocation");

        //On fait spawn un Villageois à l'endroit défini avec un nom et on désactive le Aware
        Entity target = spawnLocation.getWorld().spawnEntity(spawnLocation, EntityType.VILLAGER);

        target.setCustomName("Victime");
        target.setCustomNameVisible(true);

        if (target instanceof Mob mob) {
            mob.setAware(false);
        }

        //On enregistre la target dans le gameManager
        gameManager.setTarget(target);
    }

    public void spawnShop (String nom, List<LivingEntity> villagerShops)
    {
        Location shopLocation = McSiege.getPlugin().getConfig().getLocation(nom+"Location");
        LivingEntity villager = (LivingEntity) shopLocation.getWorld().spawnEntity(shopLocation, EntityType.VILLAGER);
        villager.setCustomName(nom);
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        ((Mob)villager).setAware(false);
        villagerShops.add(villager);
    }
}

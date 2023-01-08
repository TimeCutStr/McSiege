package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MonsterDeathListener implements Listener {
    GameManager gameManager = McSiege.getPlugin().getGameManager();

    @EventHandler
    public void onDeathMonster(EntityDeathEvent e) {
        if(e.getEntity() instanceof Mob monstre) //si l'entité morte est bien un monstre
        {
            if(gameManager.MonstreIsDansListe(monstre)) //si l'entité à bien été tuée par un joueur
            {
                if(monstre.getKiller() instanceof Player) { // si l'entité est bien un monstre créé par le jeu
                    monstre.getKiller().getInventory().addItem(new ItemStack(Material.GOLD_NUGGET)); //Donne au joueur un nugget

                }
                gameManager.RetireDeLaListe(monstre);
            }
        }
    }
}

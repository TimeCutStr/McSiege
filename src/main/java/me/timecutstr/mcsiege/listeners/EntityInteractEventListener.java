package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.menu.ArmorMenuManager;
import me.timecutstr.mcsiege.manager.menu.WeaponMenuManager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;


public class EntityInteractEventListener implements Listener {
    GameManager gameManager = McSiege.getPlugin().getGameManager();


    @EventHandler
    public void test(PlayerInteractAtEntityEvent event)
    {
        Player p = event.getPlayer();


        if(event.getRightClicked() instanceof Villager villager) {
            if (villager.getName().equals("WeaponShop")) {
                event.setCancelled(true);
                //TODO FAIRE UNE FONCTION POUR GERER LES MENU DANS LE MENU MANAGER
                p.sendMessage(villager.getName());
                WeaponMenuManager menu = new WeaponMenuManager(p);
                p.openInventory(menu.getWeaponmenu());
            }

            if (villager.getName().equals("ArmorShop")) {
                event.setCancelled(true);
                //TODO FAIRE UNE FONCTION POUR GERER LES MENU DANS LE MENU MANAGER
                p.sendMessage(villager.getName());
                ArmorMenuManager menu = new ArmorMenuManager(p);
                p.openInventory(menu.getArmorMenu());
            }

        }
    }

}

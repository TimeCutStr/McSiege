package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.staticMethode.PlayerPay;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerClickOnMenu implements Listener {
    @EventHandler
    public void onClickOnMenuEvent (InventoryClickEvent event)
    {
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getWhoClicked().getWorld())) {
            return;
        }


        Player p = (Player)event.getWhoClicked();
        McSiege plugin = McSiege.getPlugin();
        if(event.getView().getTitle().equalsIgnoreCase("Marchand Menu"))
        {
            if(event.getCurrentItem() == null)
            {
                return;
            }
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();

            int price =  plugin.getConfig().getConfigurationSection("prix").getInt(item.getType().name());
            if(PlayerPay.pay(p,price)) //On utilise la fonction pay de "playperPay" pour faire payer le joueur. Si il ne peut pas payer renvoit false
            {
                if(item.getType() == Material.EGG)
                {
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.IRON_GOLEM);
                    return;
                }

                ItemMeta meta;
                meta = item.getItemMeta();
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                p.getInventory().addItem(item); //On ajoute l'item


            }


        }
    }

}

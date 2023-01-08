package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.staticMethode.PlayerPay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerClickOnMenu implements Listener {
    @EventHandler
    public void onClickOnMenuEvent (InventoryClickEvent event)
    {
        Player p = (Player)event.getWhoClicked();
        McSiege plugin = McSiege.getPlugin();
        if(event.getView().getTitle().equalsIgnoreCase("Marchand Menu"))
        {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            int price =  plugin.getConfig().getConfigurationSection("prix").getInt(item.getType().name());
            if(PlayerPay.pay(p,price)) //On utilise la fonction pay de "playperPay" pour faire payer le joueur. Si il ne peut pas payer revoit false
            {
                p.getInventory().addItem(item); //On ajoute l'item

            }
            else {
                p.sendMessage("Tu n'as pas assez d'argent !");

            }


        }
    }

}

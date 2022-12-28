package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import org.bukkit.Material;
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
        if(event.getView().getTitle().equalsIgnoreCase("Weapon Menu"))
        {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            int price =  plugin.getConfig().getConfigurationSection("prix").getInt(item.getType().name());
            if(p.getInventory().containsAtLeast(new ItemStack(Material.GOLD_NUGGET), price)) //S'il y a des goldNuggets dans l'inventaire
            {
                p.getInventory().addItem(item); //On ajoute l'item
                int resteAPayer = price;
                //on extrait le contenu de l'inventaire pour trouver les gold nugget
                for (int i = 0; i < p.getInventory().getSize(); i++) {
                    ItemStack itemDansSlot = p.getInventory().getItem(i);
                    if(itemDansSlot.getType() == Material.GOLD_NUGGET)
                    {
                        int gnDansLeSlot = itemDansSlot.getAmount();
                        if(gnDansLeSlot >= resteAPayer)
                            p.getInventory().getItem(i).setAmount(gnDansLeSlot-resteAPayer);
                        else {
                            resteAPayer -= gnDansLeSlot;
                            p.getInventory().getItem(i).setAmount(0);

                        }
                    }

                }
            }
            else {
                p.sendMessage("Tu n'as pas assez d'argent !");

            }


        }
    }

}

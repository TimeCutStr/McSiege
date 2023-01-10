package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void OnRespawnEvent (PlayerRespawnEvent event){

        Player p = event.getPlayer();
        p.sendMessage("Tu es mort donc tu perds tout ton or !");
        Inventory inventory = event.getPlayer().getInventory();

        if(inventory.contains(Material.GOLD_NUGGET)){
            for (int i = 0; i < inventory.getSize(); i++) {
                if(inventory.getItem(i).getType() == Material.GOLD_NUGGET) {
                    inventory.clear(i);
                }
            }
        }

    }
}

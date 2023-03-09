package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class HealListener implements Listener {
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event)
    {
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getEntity().getWorld())) {
            return;
        }

        if(event.getRegainReason() == EntityRegainHealthEvent.RegainReason.EATING || event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            event.setCancelled(true);
        }
    }

}

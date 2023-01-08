package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class DestroyBlockListener implements Listener {

    @EventHandler
    public void DestructionBlock(BlockBreakEvent event)
    {
        if(event.getPlayer() != null && event.getPlayer().isOp())
        {
            return;
        }

        else {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void ExplosionBlock(EntityExplodeEvent event)
    {
        event.setCancelled(true);
        if(event.getEntity() instanceof Mob m){
            if(McSiege.getPlugin().getGameManager().MonstreIsDansListe(m)){
                McSiege.getPlugin().getGameManager().RetireDeLaListe(m);
            }
        }

    }

}



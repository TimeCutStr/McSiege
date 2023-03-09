package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DestroyBlockListener implements Listener {
    GameManager gameManager = McSiege.getPlugin().getGameManager();


    @EventHandler
    public void DestructionBlock(BlockBreakEvent event)
    {
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getPlayer().getWorld())) {
            return;
        }

        if(event.getPlayer() != null && event.getPlayer().isOp())
        {
            return;
        }

        else {
            event.setCancelled(true);
        }
    }


    //GESTION EXPLOSION DES CREEPER
    @EventHandler
    public void ExplosionCreeper(EntityExplodeEvent event)
    {
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getEntity().getWorld())) {
            return;
        }


        event.setCancelled(true);
        if(event.getEntity() instanceof Mob m){

            //Si c'est un mob (creeper) on remet le son
            Sound sonExplosion = Sound.sound(Key.key("block.end_gateway.spawn"), Sound.Source.HOSTILE,1,1);
            Sound sonCrepperMort = Sound.sound(Key.key("entity.creeper.death"), Sound.Source.HOSTILE,1,1);
            List<Player> players = gameManager.getPlayers();
            for (Player p : players ) {
                p.playSound(sonExplosion, m.getLocation().getX(), m.getLocation().getY(), m.getLocation().getZ()) ;
                p.playSound(sonCrepperMort, m.getLocation().getX(), m.getLocation().getY(), m.getLocation().getZ()) ;
            }

            if(McSiege.getPlugin().getGameManager().MonstreIsDansListe(m)){ //si le mob est dans la liste (donc en game) on le tue


                McSiege.getPlugin().getGameManager().RetireDeLaListe(m);


            }
        }

    }

}



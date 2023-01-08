package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

import java.net.http.WebSocket;

public class SpawnPlayerListener implements Listener {

    @EventHandler
    public void OnRespawnEvent (PlayerRespawnEvent event){
        event.setRespawnLocation(McSiege.getPlugin().getGameManager().getTarget().getLocation().add(-10,0,0));
        //event.getPlayer().teleport(McSiege.getPlugin().getGameManager().getTarget().getLocation().add(-10,0,0));

    }
}

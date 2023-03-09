package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.net.http.WebSocket;

public class PlayerDisconnectListener implements Listener {

    @EventHandler
    public void OnPlayerDisconnect(PlayerQuitEvent event) {
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getPlayer().getWorld())) {
            return;
        }

        GameManager gameManager = McSiege.getPlugin().getGameManager();

        if(gameManager.getGameState() == GameState.STARTING) {
            gameManager.setGameState(GameState.LOBBY);
        }
    }
}

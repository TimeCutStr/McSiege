package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.time.Duration;

public class PlayerDeathListener implements Listener {

    @EventHandler
    public void OnRespawnEvent (PlayerRespawnEvent event){
        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(!WorldCheck.worldCheck(event.getPlayer().getWorld())) {
            return;
        }


        int respawnDelay = McSiege.getPlugin().getConfig().getInt("RespawnTime")*20;

        Player p = event.getPlayer();
        p.setGameMode(GameMode.SPECTATOR);
        Title titreMort = Title.title(Component.text("Tu es mort").color(NamedTextColor.RED),Component.text("Réapparition dans 5 secondes"),
                Title.Times.times( Duration.ofSeconds(1),Duration.ofSeconds(respawnDelay/20),Duration.ofSeconds(1)));
        p.showTitle(titreMort);
        Bukkit.getScheduler().scheduleSyncDelayedTask(McSiege.getPlugin(), new Runnable() {
            @Override
            public void run() {
                p.setGameMode(GameMode.SURVIVAL);
                p.teleport(McSiege.getPlugin().getConfig().getLocation("SpawnJoueurLocation")); //Teleport to a location
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
        },respawnDelay);

    }
}

package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.Listeners.MonsterDeathListener;
import me.timecutstr.mcsiege.commands.*;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageRecipient;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class McSiege extends JavaPlugin {

    private GameManager gameManager;
    private static McSiege plugin;


    @Override
    public void onEnable() {
        plugin = this;

        // config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //singleton du gamemanager
        this.gameManager = new GameManager(this);

        //gestion des commandes
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("spawnTarget").setExecutor(new SpawnTarget());
        getCommand("setTarget").setExecutor(new SpawnTarget());
        getCommand("resetTarget").setExecutor(new ResetTargetCommand());
        getCommand("clear").setExecutor(new LevelClearCommand());


        //gestion listener
        getServer().getPluginManager().registerEvents(new MonsterDeathListener(), this);


        //lancement de la boucle toutes les secondes
        resetAllTarget();
    }

    public static McSiege getPlugin()
    {
        return plugin;
    }


    public void resetAllTarget() {
        new BukkitRunnable() {
            @Override
            public void run() {
                gameManager.resetMonstreTarget();
                gameManager.perteDePoints();

            }
        }.runTaskTimer(this, 0, 20); // exécute la tâche toutes les secondes (20 ticks)

    }

    public GameManager getGameManager() {
        return gameManager;
    }


}

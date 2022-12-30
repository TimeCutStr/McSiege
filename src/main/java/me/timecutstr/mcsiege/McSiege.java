package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.listeners.EntityInteractEventListener;
import me.timecutstr.mcsiege.listeners.MonsterDeathListener;
import me.timecutstr.mcsiege.commands.*;
import me.timecutstr.mcsiege.listeners.PlayerClickOnMenu;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
        getCommand("spawnTarget").setExecutor(new SpawnTarget());
        getCommand("setTarget").setExecutor(new SetUpCommand());
        getCommand("setSpawn").setExecutor(new SetUpCommand());
        getCommand("setWeaponShop").setExecutor(new SetUpCommand());
        getCommand("setArmorShop").setExecutor(new SetUpCommand());
        getCommand("resetTarget").setExecutor(new ResetTargetCommand());
        getCommand("clear").setExecutor(new LevelClearCommand());
        getCommand("setup").setExecutor(new SetUpCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("menu").setExecutor(new MenuCommand());


        //gestion listener
        getServer().getPluginManager().registerEvents(new MonsterDeathListener(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickOnMenu(), this);


        //lancement de la boucle toutes les secondes
        resetAllTarget();

        //setup du gamestate
        gameManager.setGameState(GameState.LOBBY);
    }

    @Override
    public void onDisable() {
        gameManager.Clear();
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

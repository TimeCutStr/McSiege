package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.listeners.*;
import me.timecutstr.mcsiege.commands.*;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

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
        getCommand("setTarget").setExecutor(new SetUpCommand());
        getCommand("setSpawn").setExecutor(new SetUpCommand());
        getCommand("setSpawnJoueur").setExecutor(new SetUpCommand());
        getCommand("setWeaponShop").setExecutor(new SetUpCommand());
        getCommand("setArmorShop").setExecutor(new SetUpCommand());
        getCommand("setRevendeurShop").setExecutor(new SetUpCommand());
        getCommand("clear").setExecutor(new LevelClearCommand());
        getCommand("setup").setExecutor(new SetUpCommand());
        getCommand("start").setExecutor(new StartCommand());
        getCommand("lobby").setExecutor(new LobbyCommand());
        getCommand("rdy").setExecutor(new ReadyCommand());
        getCommand("reloadconfig").setExecutor(new ReloadConfigCommand());



        //gestion listener
        getServer().getPluginManager().registerEvents(new MonsterDeathListener(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickOnMenu(), this);
        getServer().getPluginManager().registerEvents(new InteractWithPanneau(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);
        getServer().getPluginManager().registerEvents(new HealListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnectListener(), this);


        //setup du gamestate
        gameManager.setGameState(GameState.LOBBY);


        //AutoFeed
        new BukkitRunnable() {
            @Override
            public void run() {

                List<Player> players = gameManager.getPlayers();

                for (Player p: players) {
                    p.setFoodLevel(20);
                }


            }
        }.runTaskTimer(McSiege.getPlugin(), 0, 200); // exécute la tâche toutes les secondes (20 ticks)
    }

    @Override
    public void onDisable() {
        gameManager.clear();
        gameManager = null;
    }

    public static McSiege getPlugin()
    {
        return plugin;
    }




    public GameManager getGameManager() {
        return gameManager;
    }



}

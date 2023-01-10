package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.listeners.*;
import me.timecutstr.mcsiege.commands.*;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import org.bukkit.plugin.java.JavaPlugin;

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
        getCommand("reloadConfig").setExecutor(new ReloadConfigCommand());



        //gestion listener
        getServer().getPluginManager().registerEvents(new MonsterDeathListener(), this);
        getServer().getPluginManager().registerEvents(new EntityInteractEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickOnMenu(), this);
        getServer().getPluginManager().registerEvents(new InteractWithPanneau(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new DestroyBlockListener(), this);


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




    public GameManager getGameManager() {
        return gameManager;
    }



}

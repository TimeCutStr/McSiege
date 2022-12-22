package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.commands.*;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class McSiege extends JavaPlugin {

    private GameManager gameManager;


    @Override
    public void onEnable() {
        // config file
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //singleton du gamemanager
        this.gameManager = GameManager.getGameManager();

        //gestion des commandes
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("spawnTarget").setExecutor(new SpawnTarget());
        getCommand("setTarget").setExecutor(new SpawnTarget());
        getCommand("resetTarget").setExecutor(new ResetTargetCommand());
        getCommand("clear").setExecutor(new LevelClearCommand());
        getCommand("setL1").setExecutor(new SetLocationCommand());
        getCommand("setL2").setExecutor(new SetLocationCommand());

        resetAllTarget();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("printMessageFromConfig")){
            Player player = (Player) sender;
            player.sendMessage(getConfig().getString("food"));
        } else if (command.getName().equals("SetFood")) {
            Player player = (Player) sender;
            player.sendMessage("la commande marche");
            getConfig().set("food", "citrouille");


        }

        return false;
    }

    public void resetAllTarget() {
        new BukkitRunnable() {
            @Override
            public void run() {
                gameManager.resetMonstreTarget();
            }
        }.runTaskTimer(this, 0, 20); // exécute la tâche toutes les secondes (20 ticks)

    }
}
//todo definir une zone de 5 par 5 autour du target. Si les monstres entrent dedans ils disparaissent et décrémente le nombre de vie
//todo /clear pour effacer le villageoois et les monstres
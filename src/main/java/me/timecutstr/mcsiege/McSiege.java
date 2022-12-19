package me.timecutstr.mcsiege;

import me.timecutstr.mcsiege.commands.SpawnCommand;
import me.timecutstr.mcsiege.commands.SpawnTarget;
import me.timecutstr.mcsiege.manager.GameManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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




}

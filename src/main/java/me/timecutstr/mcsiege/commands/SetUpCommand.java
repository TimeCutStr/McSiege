package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.GameState;
import me.timecutstr.mcsiege.staticMethode.WorldCheck;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetUpCommand implements CommandExecutor {
    McSiege plugin = McSiege.getPlugin();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        //VERIF QUE CET EVENEMENT NE S'APPLIQUE QU'A MINESIEGE
        if(sender instanceof Player p && !WorldCheck.worldCheck(p.getWorld())) {
            return false;
        }

        //Verification que la commande a bien été faite InGame
        // TODO verif de permission

        if(sender instanceof Player p) {
            if (!(p.hasPermission("MineSiege.setup"))) {
                p.sendMessage("Vous n'avez pas la permission (MineSiege.setup) de faire cette commande");
                return false;
            }
        }



        Player p;
        if (sender instanceof Player) {
            p = (Player) sender;
        } else {
            System.out.println("Cette commande doit être effectuée en jeu." +
                    " Si vous voulez modifier les coordonées de spawn," +
                    " vous pouvez modifier le fichier CONFIG.YML");
            return false;
        }


        switch (command.getName()) {
            case "setup":  //COMMANDE /SETUP
                if (plugin.getGameManager().getGameState() == GameState.LOBBY) {
                    plugin.getGameManager().setGameState(GameState.SETUP);
                } else if (plugin.getGameManager().getGameState() == GameState.SETUP) {
                    plugin.getGameManager().setGameState(GameState.LOBBY);
                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande que dans le Lobby");
                    return false;
                }

                return true;

            case "setTarget":   //COMMANDE /SETTARGET

                //Verif qu'on est bien dans la phase de SetUp
                if (plugin.getGameManager().getGameState() != GameState.SETUP) {
                    Bukkit.broadcast(Component.text("Vous n'êtes pas dans la phase de SetUp, vous ne pouvez pas changer les spawn"));
                    return false;
                } else {
                    Location targetLocation = p.getLocation();
                    plugin.reloadConfig();
                    plugin.getConfig().set("targetLocation", targetLocation);
                    plugin.saveConfig();
                    p.sendMessage(Component.text("L'emplacement du centre du village est définit."));
                    return true;
                }

            case "setSpawn":    //COMMANDE /SetSpawn
                //Message d'erreur
                TextComponent messageErreur = Component.text("Vous devez undiquer en argument un chiffre de 1 à 4. ")
                        .append(Component.text("Exemple :")
                                .append(Component.text("/SetSpawn 3").decoration(TextDecoration.ITALIC, true)));


                //Verif qu'on est bien dans la phase de SETUP
                if (plugin.getGameManager().getGameState() == GameState.SETUP) {

                    if (args.length == 0) {
                        p.sendMessage(messageErreur);
                        return false;
                    }

                    int nBSpawn;

                    try {
                        nBSpawn = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        p.sendMessage(messageErreur);
                        return false;
                    }

                    if (nBSpawn > 0 && nBSpawn <= 4) {
                        Location spawnLocation = p.getLocation();
                        plugin.reloadConfig();
                        plugin.getConfig().set("spawnLocation" + nBSpawn, spawnLocation);
                        plugin.saveConfig();
                        p.sendMessage("Le spawn n°"+ nBSpawn +" à bien été enregistré !");
                        return true;
                    } else {
                        p.sendMessage(messageErreur);
                        return false;
                    }

                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande qu'en phase de SetUp");
                    p.sendMessage("Pour entrer en phase de SetUp faite /setup depuis le lobby");
                    return false;
                }

            case "setWeaponShop":    //COMMANDE /SetWeaponShop


                //Verif qu'on est bien dans la phase de SETUP
                if (plugin.getGameManager().getGameState() == GameState.SETUP) {
                    Location weaponShopLocation = p.getLocation();
                    plugin.reloadConfig();
                    plugin.getConfig().set("WeaponShopLocation", weaponShopLocation);
                    plugin.saveConfig();
                    p.sendMessage("Le shop est créé");
                    return true;


                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande qu'en phase de SetUp");
                    p.sendMessage("Pour entrer en phase de SetUp faite /setup depuis le lobby");
                    return false;
                }

            case "setArmorShop":    //COMMANDE /SetArmorShop


                //Verif qu'on est bien dans la phase de SETUP
                if (plugin.getGameManager().getGameState() == GameState.SETUP) {
                    Location armorShopLocation = p.getLocation();
                    plugin.reloadConfig();
                    plugin.getConfig().set("ArmorShopLocation", armorShopLocation);
                    plugin.saveConfig();
                    p.sendMessage("Le shop est créé");
                    return true;


                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande qu'en phase de SetUp");
                    p.sendMessage("Pour entrer en phase de SetUp faite /setup depuis le lobby");
                    return false;
                }

            case "setRevendeurShop":    //COMMANDE /SetArmorShop


                //Verif qu'on est bien dans la phase de SETUP
                if (plugin.getGameManager().getGameState() == GameState.SETUP) {
                    Location RevendeurShopLocation = p.getLocation();
                    plugin.reloadConfig();
                    plugin.getConfig().set("RevendeurShopLocation", RevendeurShopLocation);
                    plugin.saveConfig();
                    p.sendMessage("Le shop est créé");
                    return true;


                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande qu'en phase de SetUp");
                    p.sendMessage("Pour entrer en phase de SetUp faite /setup depuis le lobby");
                    return false;
                }

            case "setSpawnJoueur":    //COMMANDE /SetArmorShop
                //Verif qu'on est bien dans la phase de SETUP
                if (plugin.getGameManager().getGameState() == GameState.SETUP) {
                    Location SpawnJoueurLocation = p.getLocation();
                    plugin.reloadConfig();
                    plugin.getConfig().set("SpawnJoueurLocation", SpawnJoueurLocation);
                    plugin.saveConfig();
                    p.sendMessage("Le spawn des joueurs est créé");
                    return true;


                } else {
                    p.sendMessage("Vous ne pouvez faire cette commande qu'en phase de SetUp");
                    p.sendMessage("Pour entrer en phase de SetUp faite /setup depuis le lobby");
                    return false;
                }

            default:
                Bukkit.broadcast(Component.text("Pas une bonne commande, va savoir pourquoi"));
                return false;

        }
    }
}


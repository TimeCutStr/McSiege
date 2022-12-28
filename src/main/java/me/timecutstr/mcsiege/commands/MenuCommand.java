package me.timecutstr.mcsiege.commands;

import me.timecutstr.mcsiege.manager.menu.WeaponMenuManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player p = (Player)sender;
        WeaponMenuManager menu = new WeaponMenuManager(p);
        p.sendMessage("test");
        p.openInventory(menu.getWeaponmenu());
        p.sendMessage("test2");

        return true;

    }
}

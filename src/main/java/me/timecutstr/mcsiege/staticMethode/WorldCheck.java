package me.timecutstr.mcsiege.staticMethode;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;


public class WorldCheck {

    public static Boolean worldCheck(World world)
    {

        if (world.getName().equals(McSiege.getPlugin().getConfig().getString("World")))
        {
            return true;
        }

        else
        {
            return false;
        }

    }
}

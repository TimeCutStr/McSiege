package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;

import javax.swing.text.AbstractDocument;
import java.util.ArrayList;
import java.util.List;

public class HealthManager {
    private int viesMax;
    private int vies;

    public void setGameOn(boolean gameOn) {
        this.gameOn = gameOn;
    }

    private boolean gameOn;

    public HealthManager (int vies)
    {
        this.viesMax = vies;
        this.vies = this.viesMax;
        gameOn = false;
    }

    public void resetHealthManager ()
    {
        vies = viesMax;
    }



    public void perteDePoints (McSiege plugin, ListMonstreManager listMonstreManager, Entity target)
    {
        if(!gameOn)
        {
            return;
        }
        ArrayList<Mob> monstres = listMonstreManager.getMonstres();
        ArrayList<Mob> monstresMort = new ArrayList<>();

        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste
            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                Location mL = monstre.getLocation();
                Location tL = target.getLocation();
                Double distX;
                Double distZ;
                Double distance;
                // CALCULE DE LA DISTANCE ENTRE LE MONSTRE ET LA TARGET
                if (mL.getX() > tL.getX()) {
                    distX = mL.getX() - tL.getX();
                } else {

                    distX = tL.getX() - mL.getX();
                }

                if (mL.getZ() > tL.getZ()) {
                    distZ = mL.getZ() - tL.getZ();
                } else {
                    distZ = tL.getZ() - mL.getZ();
                }

                distance = Math.sqrt(distX * distX + distZ * distZ);

                if (distance < 5) { //SI LA DISTANCE EST INFERIEUR A 5 BLICK ON TUE LE MONSTRE ET ON ENLEVE UN PV
                    monstresMort.add(monstre);
                    vies--;

                    Bukkit.broadcastMessage(ChatColor.RED + "Vies restantes : " + vies);
                    target.setCustomName("**"+vies+"**");

                }

                if(distance > 100) {
                    monstresMort.add(monstre);
                }

            }

            for (Mob monstre : monstresMort) {
                monstre.setHealth(0.0);

            }



        }



        if(vies <= 0)
        {
            plugin.getGameManager().setGameState(GameState.LOOSE);
            gameOn=false;
        }
    }

    public void distanceMonstre ( ArrayList<Monster> monstres, Entity target)
    {
        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste
            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                Location mL = monstre.getLocation();
                Location tL = target.getLocation();
                Double distX;
                Double distZ;
                Double distance;
                // CALCULE DE LA DISTANCE ENTRE LE MONSTRE ET LA TARGET
                if (mL.getX() > tL.getX()) {
                    distX = mL.getX() - tL.getX();
                } else {

                    distX = tL.getX() - mL.getX();
                }

                if (mL.getZ() > tL.getZ()) {
                    distZ = mL.getZ() - tL.getZ();
                } else {
                    distZ = tL.getZ() - mL.getZ();
                }

                distance = Math.sqrt(distX * distX + distZ * distZ);
            }
        }
    }


}

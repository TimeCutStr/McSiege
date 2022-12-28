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
    private int vies;

    public HealthManager (int vies)
    {
        this.vies = vies;
    }



    public void perteDePoints (McSiege plugin, ListMonstreManager listMonstreManager, Entity target)
    {
        List<Mob> monstres = listMonstreManager.getMonstres();
        List<Mob> monstresMorts = new ArrayList<>();
        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste
            Bukkit.broadcast(Component.text(monstres.toString()));
            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                Location mL = monstre.getLocation();
                Location tL = target.getLocation();
                Double distX;
                Double distZ;
                Double distance;


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


                if (distance < 5) {
                    monstre.setHealth(0);
                    monstresMorts.add(monstre);
                    vies--;

                    Bukkit.broadcastMessage(ChatColor.RED + "Vies restantes : " + vies);
                    target.setCustomName("**"+vies+"**");

                }

            }

            for (Mob monstre : monstresMorts) { //pour chaque monstre dans la liste des monstres à tuer

                monstres.remove(monstre); //on enlève le monstre qui a été tué précédement
                if(monstres.isEmpty()) {
                    plugin.getGameManager().setGameState(GameState.LOBBY);
                    Damageable targetATuer =  (Damageable)target;
                    targetATuer.setHealth(0);
                    //TODO METHODE DANS GAME MANAGER NEXT STATE
                }

            }
        }

        else if(plugin.getGameManager().getGameState() != GameState.LOBBY) {
            Bukkit.broadcast(Component.text("Bravo, vous êtes venu au bout de la vague"));
            plugin.getGameManager().setGameState(GameState.LOBBY);
            //TODO FAIRE UNE METHODE NEXT STEP
        }


        if(vies <= 0)
        {
            Bukkit.broadcast(Component.text("GAME OVER").color(NamedTextColor.RED));
            plugin.getGameManager().setGameState(GameState.LOBBY);

            //On tue le villageois
            Damageable targetATuer =  (Damageable)target;
            targetATuer.setHealth(0);
        }
    }
}

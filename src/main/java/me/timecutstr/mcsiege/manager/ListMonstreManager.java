package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ListMonstreManager {

    private final McSiege plugin;
    private List<Mob> monstres = new ArrayList<>();

    public List<Mob> getMonstres ()
    {
        return monstres;
    }
    public ListMonstreManager(McSiege plugin) {
        this.plugin = plugin;
    }

    public void addMonstre (Mob monstre) {
        //ajoute un monstre à la liste de monstres
        monstres.add(monstre);
    }

    public void clear()
    {
        //tue tous les monstres ainsi que la target
        if(plugin.getGameManager().getTarget() != null) {
            Mob m = (Mob) plugin.getGameManager().getTarget();
            m.setHealth(0);
        }

        for (LivingEntity mob : plugin.getGameManager().getVillagerShops()
             ) {
          mob.setHealth(0);

        }
        plugin.getGameManager().getVillagerShops().clear();

        for (Mob monstre: monstres
        ) {
            monstre.setHealth(0);
        }
        monstres.clear();
    }

    public void RetireDeLaListe (Mob monstre) {
        if(monstres.contains(monstre))
        {
            monstres.remove(monstre);
        }
    }


    public boolean MonstreIsDansListe(Mob m)
    {
        for (Mob monstre: monstres
        ) {
            if (m == monstre) {
                return true;
            }
        }
        return false;
    }

    public void resetMonstreTarget (Entity target)
    {
        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste
            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                if(!(monstre.getTarget() instanceof Player)) { //si sa cible n'est pas un joueur
                    if(monstre.getTarget() != (LivingEntity)target){ //si la cible n'est pas déjà la target
                        monstre.setTarget((LivingEntity)target); //la cible devient la target
                    }
                }
            }
        }

    }
}

package me.timecutstr.mcsiege.manager;

import org.apache.logging.log4j.CloseableThreadContext;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameManager {

    private Entity target;
    private Location L1;
    private Location L2;
    private List<Mob> monstres = new ArrayList<>();




    private GameManager()
    {}

    private static GameManager INSTANCE = new GameManager();

    public static GameManager getGameManager ()
    {
        return INSTANCE;

    }


    // TARGET METHODE
    public void setTarget(Entity target)
    {
        this.target = target;
    }

    public Entity getTarget()
    {
        if(target == null)
        {
            System.out.println("target not set");
            return null;
        }
        else {
            return target;
        }
    }

    public void resetMonstreTarget ()
    {
        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste
            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                if(!(monstre.getTarget() instanceof Player)) { //si sa cible n'est pas un joueur
                    if(monstre.getTarget() != (LivingEntity)this.target){ //si la cible n'est pas déjà la target
                        monstre.setTarget((LivingEntity) this.target); //la cible devient la target
                    }
                }
            }
        }
        else {
            System.out.println("la liste de mob est vide");
        }
    }

    //MONSTRE LIST METHODE

    public void addMonstre (Mob monstre) {
        //ajoute un monstre à la liste de monstres
        monstres.add(monstre);
    }

    public void clear()
    {
        //tue tous les monstres ainsi que la target
        Mob m = (Mob)target;
        m.setHealth(0);

        for (Mob monstre: monstres
             ) {
            monstre.setHealth(0);
        }
        monstres.clear();
    }

    //LOCATION CIBLE METHODE

    public void setL1(Location l1)
    {
        this.L1=l1;
    }

    public  void setL2(Location l2)
    {
        this.L2=l2;
    }

}

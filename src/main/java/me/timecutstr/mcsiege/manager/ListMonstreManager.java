package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Collection;

public class ListMonstreManager {

    private final McSiege plugin;
    private ArrayList<Mob> monstres = new ArrayList<>();

    public ArrayList<Mob> getMonstres ()
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
            for (Entity entity : m.getWorld().getEntities()) //on profite d'avoir un mob qu'on est sur d'être là pour clear les objets au sol
            {
                if(entity instanceof Item)
                {
                    entity.remove();
                }
            }
            m.setHealth(0);
        }

        for (LivingEntity mob : plugin.getGameManager().getVillagerShops()
             ) {
          mob.setHealth(0);

        }
        plugin.getGameManager().getVillagerShops().clear();


        ArrayList<Mob> monstreATuer = new ArrayList<>() ;
        for (Mob monstre: monstres) {
            monstreATuer.add(monstre);
        }
        for (Mob monstre: monstreATuer
        ) {
            monstre.setHealth(0);
        }

        Collection<Entity> entities = Bukkit.getWorld("minesiege").getEntities();

        for (Entity e: entities ) {
            if (!(e instanceof Player)) {
                e.remove();
            }

        }



    }

    public void RetireDeLaListe (Mob monstreMort) {
        if(monstres.contains(monstreMort))
        {
            monstres.remove(monstreMort); //On enlève le monstre de la liste
            CheckListVide(); //On vérifie si la liste est vide pour passer à la prochaine wave
        }
    }


    public boolean MonstreIsDansListe(Mob m) // Verifie que le monstre est bien dans la list
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

    public void CheckListVide ()
    {
        // si la liste des monstres est vide, on passe au gamestate suivant
        if(monstres.isEmpty() && plugin.getGameManager().isGameStarted() && plugin.getGameManager().getWave() >= 5)
        {
            Bukkit.broadcast(Component.text("Vous avez tué tous les monstres !"));
            McSiege.getPlugin().getGameManager().NextGameState();
            McSiege.getPlugin().getGameManager().RecompenseFinNiveau();
        }

    }

}

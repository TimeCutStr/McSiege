package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import org.apache.logging.log4j.CloseableThreadContext;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GameManager {

    private Plugin plugin;

    //variables de jeu
    private Entity target;
    private int vies =100;

    private GameState gameState = GameState.LOBBY;
    private List<Mob> monstres = new ArrayList<>();




    public GameManager(McSiege plugin)
    {
        this.plugin = plugin;
    }

    /*

            GAME STATE

                LOBBY
                    CONSTRUCTION EQUIPES
                    5 PERSONNES MAXIMUM
                    SI DES JOUEURS NE SONT PAS DANS L'EQUIPE ILS ONT KICK
                    SORTIE DU LOBBY AVEC UN /RDY OU /READY
                    QUAND TOUS LES JOUEURS ONT FAIRE UN /RDY PASSAGE A LA PHASE 2 : STUFF
                    TODO FAIRE LA COMMANDE /READY OU /RDY POUR SORTIR DU LOBBY

                STUFF
                    LES JOUEURS ONT 5 MINUTES POUR CONSTITUER LEUR EQUIPEMENT DANS LES SHOPS
                    ILS PEUVENT PRENDRE CE QU'ILS VEULENT AVEC UN NOMBRE D'OR LIMITÉ

                WAVE 1
                    APPARITION D'UN NOMBRE DE MONSTRE FIXE A DEFINIR DANS LE CONFIG.YML
                    TODO CONFIG.YML AVEC LES VAGUES ET LEUR CONSTITUTION
                    10 ZOMBIES
                    5 ARAIGNÉES
                    2 SQUELETTES
                    0 ENDERMAN

                STUFF
                    LES JOUEURS ONT 5 MINUTES POUR CONSTITUER LEUR EQUIPEMENT DANS LES SHOPS
                    ILS PEUVENT PRENDRE CE QU'ILS VEULENT AVEC L'OR QU'ILS ONT LOOT

                WAVE 2
                    ...

                STUFF

                VAGUE SPECIAL AVEC DES MOBS QUI CHERCHENT JUSTE A TUER LES JOUEURS OU UN JOUEUR EN PARTICULUER


     */

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case LOBBY :
                System.out.println("LOBBY");
                Bukkit.broadcastMessage("les joueurs qui veulent participer doivent faire /rdy");
                Bukkit.broadcastMessage("les joueurs qui veulent être specateur doivent faire /spectate");
                Bukkit.broadcastMessage("Quand 5 joueurs seront prèts la partie se lancera");

                //todo gestion des joueurs prets avec une liste de joueurs
                //Quand la liste arrive à 5 lancement de la phase CHOP

            case WAVE1:
                System.out.println("WAVE1");

            case WAVE2:
                System.out.println("CHOP");
        }
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

    }

    public void perteDePoints ()
    {
        List<Mob> monstresMorts = new ArrayList<>();
//TODO PROBLEME SUR LES COTES A VERIFIER
        if(!monstres.isEmpty()) { //si il y a des monstres dans la liste

            for (Mob monstre : monstres) { //pour chaque monstre dans la liste des monstres
                Location mL = monstre.getLocation();
                Location tL = target.getLocation();
                Double distX;
                Double distY;
                Double distance;

                if (mL.getX() > tL.getX()) {
                    distX = mL.getX() - tL.getX();
                } else {

                    distX = tL.getX() - mL.getX();
                }

                if (mL.getY() > tL.getY()) {
                    distY = mL.getY() - tL.getY();
                } else {
                    distY = tL.getY() - mL.getY();
                }

                distance = Math.sqrt(distX * distX + distY * distY);

                if (distance < 5) {
                    monstre.setHealth(0);
                    monstresMorts.add(monstre);
                    vies--;

                    Bukkit.broadcastMessage(ChatColor.RED + "Vies restantes : " + vies);

                }

            }

            for (Mob monstre : monstresMorts) { //pour chaque monstre dans la liste des monstres à tuer

                monstres.remove(monstre); //on enlève le monstre qui a été tué précédement
            }
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

    public void spawn (int nombreASpawn)
    {

        Location spawnLocastion = new Location(plugin.getServer().getWorld("world"), -24.0, -60.0, 165.0);

        while (nombreASpawn > 0 ) {


            Entity monstre = spawnLocastion.getWorld().spawnEntity(spawnLocastion, EntityType.ZOMBIE);

            if (target == null) {
                System.out.println("pas de target");
            } else {
                if (monstre instanceof Mob mob && target instanceof LivingEntity target) {
                    mob.setTarget(target);
                    addMonstre(mob);

                }
            }
            nombreASpawn --;
        }

    }

    public void spawn (int nombreASpawn, EntityType type)
    {
        Location spawnLocastion = new Location(plugin.getServer().getWorld("world"), -24.0, -60.0, 165.0);

        while (nombreASpawn > 0 ) {


            Entity monstre = spawnLocastion.getWorld().spawnEntity(spawnLocastion, type);

            if (target == null) {
                System.out.println("pas de target");
            } else {
                if (monstre instanceof Mob mob && target instanceof LivingEntity target) {
                    mob.setTarget(target);
                    addMonstre(mob);

                }
            }
            nombreASpawn --;
        }

    }

}

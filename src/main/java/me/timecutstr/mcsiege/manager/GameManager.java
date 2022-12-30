package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private McSiege plugin;
    private int vies =100;
    private HealthManager healthManager;
    private GameState gameState = GameState.LOBBY;
    private List<GameState> level;
    private ListMonstreManager listMonstreManager;
    private TargetManager targetManager;

    private SpawnManager spawnManager;


    public List<LivingEntity> getVillagerShops() {
        return villagerShops;
    }

    private List<LivingEntity> villagerShops;

    private List<Player> players;

    public Map<String, Integer> prix = new HashMap<>();
    public GameManager(McSiege plugin)
    {
        this.plugin = plugin;
        this.listMonstreManager = new ListMonstreManager(plugin);
        targetManager = new TargetManager();
        healthManager = new HealthManager(100);
        spawnManager = new SpawnManager(this);
        villagerShops = new ArrayList<>();
        level= new ArrayList<>();
        level.add(GameState.STARTING);
        level.add(GameState.WAVE1);
        level.add(GameState.SHOP);
        level.add(GameState.WAVE2);
        level.add(GameState.WAVE3);
        level.add(GameState.SHOP);
        level.add(GameState.WAVESPECIAL1);
        level.add(GameState.WAVE4);
        level.add(GameState.WAVE5);
        level.add(GameState.SHOP);
        level.add(GameState.WAVE6);
        level.add(GameState.WAVESPECIAL2);
        level.add(GameState.WAVE7);
        level.add(GameState.SHOP);
        level.add(GameState.BOSS);

    }




    // METHODE

    //LIST METHODE

    public void Clear ()
    {
        listMonstreManager.clear();
    }

    public boolean MonstreIsDansListe (Mob monstre)
    {
        return listMonstreManager.MonstreIsDansListe(monstre);
    }

    public void RetireDeLaListe (Mob monstre) {
        listMonstreManager.RetireDeLaListe(monstre);
    }

    public void resetMonstreTarget ()
    {
        listMonstreManager.resetMonstreTarget(this.targetManager.getTarget());
    }

    // TARGET METHODE
    public void setTarget(Entity target)
    {
        this.targetManager.setTarget(target);
    }

    public Entity getTarget()
    {
        if(targetManager.getTarget() == null)
        {
            return null;
        }
        else {
            return targetManager.getTarget();
        }
    }


    // POINT DE VIE METHODE
    public void perteDePoints ()
    {
        healthManager.perteDePoints(plugin,listMonstreManager,targetManager.getTarget());
    }

    // GAME STATE
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

    public GameState getGameState()
    {
        return this.gameState;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        switch (gameState) {
            case SETUP :
                Bukkit.broadcast(Component.text("Vous êtes dans le SetUp du jeu.")
                        .color(NamedTextColor.GOLD)
                        .decoration(TextDecoration.ITALIC, true));
                Bukkit.broadcast(Component.text("Pour enregistrer le centre de la carte placez vous au centre et faites ")
                        .color(NamedTextColor.BLUE)
                        .append(Component.text("/setTarget")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
                Bukkit.broadcast(Component.text("Il doit y avoir 4 spawn de monstres. " +
                        "Pour les enregistrez placez vous là où vous voulez qu'ils spawnent et faites")
                        .color(NamedTextColor.BLUE)
                        .append(Component.text("setSpawn + le numero du spawn (entre 1 et 4)")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
                Bukkit.broadcast(Component.text("Vous pouvez aussi placer les shop avec :")
                        .color(NamedTextColor.BLUE)
                        .append(Component.text("/setWeaponShop et /setArmorShop")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
            break;

            case LOBBY :
                //TODO si les emplacements n'ont pas encore été initialisés demander de passer par le setup

                System.out.println("LOBBY");
                Bukkit.broadcast(Component.text("Bienvenu sur le lobby"));
                Bukkit.broadcast(Component.text("les joueurs qui veulent participer doivent faire /rdy"));
                Bukkit.broadcast(Component.text("les joueurs qui veulent être specateur doivent faire /spectate"));
                Bukkit.broadcast(Component.text("Quand 5 joueurs seront prèts la partie se lancera"));

                if(villagerShops.isEmpty())
                {
                    spawnManager.spawnShop("WeaponShop",villagerShops);
                    spawnManager.spawnShop("ArmorShop", villagerShops);
                }

                //todo gestion des joueurs prets avec une liste de joueurs
                //Quand la liste arrive à 5 lancement de la phase CHOP
                //todo ne pas lancer si le spawn des monstres n'est pas set et si le spawntarget non plus

            break;

            case STARTING:
                Bukkit.broadcast(Component.text("La partie va commencer !").color(NamedTextColor.BLUE));
                Bukkit.broadcast(Component.text("Vous avez une minute pour faire vos achats !").color(NamedTextColor.BLUE));

            case SHOP:
                //TODO FAIRE UN GESTIONNAIR DES JOUEURS POUR GARDER QUE 5 JOUEUR ET DES SPECTATEURS
                for (Player p : players //clear d'inventaires
                ) {
                    p.getInventory().clear();
                    p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 20));
                }

                Bukkit.broadcast(Component.text("Bravo vous avez survecu jusque là !").color(NamedTextColor.BLUE));
                Bukkit.broadcast(Component.text("Vous avez une minute pour faire vos achats !").color(NamedTextColor.BLUE));
            break;

            case WAVE1:
                Bukkit.broadcast(Component.text("C'est parti pour la vague 1 !"));
                spawnManager.spawnVictime();

                for (int i = 1; i < 5; i++) {
                    Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                    spawnManager.spawn(5,EntityType.ZOMBIE ,location,targetManager,listMonstreManager);
                    spawnManager.spawn(2,EntityType.SKELETON,location,targetManager,listMonstreManager);
                    spawnManager.spawn(1,EntityType.SPIDER,location,targetManager,listMonstreManager);
                }

                players = new ArrayList<>(Bukkit.getOnlinePlayers());


            break;


        }
    }
}

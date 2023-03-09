package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.BukkitRunnable.CountDownLoose;
import me.timecutstr.mcsiege.manager.BukkitRunnable.CountDownNextPhase;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private McSiege plugin;
    private int vies =100;
    private HealthManager healthManager;

    public int getOpenWaves() {
        return openWaves;
    }

    private int openWaves;
    private GameState gameState;
    private int gameStatePhase ;

    public boolean isGameStarted() {
        return gameStarted;
    }

    private boolean gameStarted = false;
    private final ListMonstreManager listMonstreManager;
    private final TargetManager targetManager;

    private WaveManager waveManager;

    private Location location;

    public List<LivingEntity> getVillagerShops() {
        return villagerShops;
    }

    private List<LivingEntity> villagerShops;

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player player) {

        if(players.contains(player))
        {
            players.remove(player);
            Bukkit.broadcast(Component.text(player.getName() + " n'est plus prêt !"));
            return;
        }

        int joeurMax = McSiege.getPlugin().getConfig().getInt("JoueurMax");

        this.players.add(player);
        Bukkit.broadcast(Component.text(player.getName()+" est prêt !"));


        if(this.players.size() == McSiege.getPlugin().getConfig().getInt("JoueurMax")) {
            setGameState(GameState.STARTING);
            return;
        }

        Bukkit.broadcast(Component.text("Pour commencer il manque " + (joeurMax-this.players.size()) + " joueurs." ));
    }

    private List<Player> players;

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    private int countdown;
    private CountDownNextPhase countDownNextPhase;

    public ChunkLoad getChunkLoad() {
        return chunkLoad;
    }

    private ChunkLoad chunkLoad;

    int wave;

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getWave() {
        return wave;
    }



    private BukkitTask bukkitTask;



    public Map<String, Integer> prix = new HashMap<>();
    public GameManager(McSiege plugin)
    {
        this.plugin = plugin;
        this.listMonstreManager = new ListMonstreManager(plugin);
        targetManager = new TargetManager();
        healthManager = new HealthManager(plugin.getConfig().getInt("ViesMax"));
        villagerShops = new ArrayList<>();
        chunkLoad = new ChunkLoad();
        openWaves = plugin.getConfig().getInt("openWaves");
        players = new ArrayList<>();
        if(openWaves == 0)
        {
            System.out.println("Attention ! La ligne openWaves dans le config.yml est à 0 ou n'est pas configurée !");
            Bukkit.broadcast(Component.text("Attention ! La ligne openWaves dans le config.yml est à 0 ou n'est pas configurée !"));
        }

    }




    // METHODE

    //LIST METHODE

    public void clear()
    {
        gameStarted = false;
        listMonstreManager.clear();



    }

    public void reload()
    {
        healthManager = new HealthManager(plugin.getConfig().getInt("ViesMax"));
        openWaves = plugin.getConfig().getInt("openWaves");
        setGameState(GameState.LOBBY);
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

    // GESTION DU GAMESTATE

    public GameState getGameState()
    {
        return this.gameState;
    }

    public GameState NextGameState() {
        if(!gameStarted)
        {
            gameStatePhase = 0;
            setGameState(GameState.LOBBY);
            return GameState.LOBBY;
        }

        gameStatePhase ++;

        switch (gameStatePhase) {
            case 1 -> setGameState(GameState.LEVEL1);
            case 2 -> setGameState(GameState.SHOP);
            case 3 -> setGameState(GameState.LEVEL2);
            case 4 -> setGameState(GameState.SHOP);
            case 5 -> setGameState(GameState.LEVEL3);
            case 6 -> setGameState(GameState.WAVESPECIAL1);
            case 7 -> setGameState(GameState.SHOP);
            case 8 -> setGameState(GameState.LEVEL4);
            case 9 -> setGameState(GameState.SHOP);
            case 10 -> setGameState(GameState.LEVEL5);
            //case 11 -> setGameState(GameState.SHOP);
            //case 12 -> setGameState(GameState.BOSS);
            case 11 -> setGameState(GameState.WON);
            default -> setGameState(GameState.LOBBY);
        }

        return gameState;
    }

    public void RecompenseFinNiveau()
    {
        int gain = plugin.getConfig().getInt("RécompenseFinDeNiveau");
        if(gain == 0)
        {
            System.out.println("Vous devez définir les récompenses de fin de niveau dans le fichier Config.Yml !!!!");
            return;
        }
        for (Player p : players
             ) {

            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, gain));
            p.sendMessage(Component.text("Vous gagnez ").color(NamedTextColor.BLUE)
                    .append(Component.text(gain).color(NamedTextColor.GOLD))
                    .append(Component.text(" Gold Nuggets pour avoir survécu à la wave").color(NamedTextColor.BLUE)));
        }
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
                        .append(Component.text("/setWeaponShop, /setRevendeurShop et /setArmorShop")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
                Bukkit.broadcast(Component.text("Veillez à bien enregistrer le point de spawn avec ")
                        .color(NamedTextColor.BLUE)
                        .append(Component.text("/setSpawnJoueur")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
            break;

            case LOBBY :
                gameStarted = false;
                clear(); //On clear tout ce qui peut rester des anciennes games
                chunkLoad.unloadChunks(); //On unload les chunks pour si le jeu à planté ou si les joueurs ont fini une partie
                healthManager.resetHealthManager();
                players.clear();
                openWaves = plugin.getConfig().getInt("openWaves");
                if(bukkitTask != null)
                {
                    bukkitTask.cancel();
                }
                //TODO si les emplacements n'ont pas encore été initialisés demander de passer par le setup

                System.out.println("LOBBY");
                Bukkit.broadcast(Component.text("Bienvenu sur le lobby"));
                Bukkit.broadcast(Component.text("les joueurs qui veulent participer doivent faire /rdy"));
                Bukkit.broadcast(Component.text("les joueurs qui veulent être specateur doivent faire /spectate"));
                Bukkit.broadcast(Component.text("Quand 5 joueurs seront prèts la partie se lancera"));

                if(villagerShops.isEmpty())
                {
                    SpawnManager.spawnShop("WeaponShop",villagerShops);
                    SpawnManager.spawnShop("ArmorShop", villagerShops);
                    SpawnManager.spawnShop("RevendeurShop",villagerShops);
                }

                //todo gestion des joueurs prets avec une liste de joueurs
                //Quand la liste arrive à 5 lancement de la phase CHOP
                //todo ne pas lancer si le spawn des monstres n'est pas set et si le spawntarget non plus
                //TODO POINT DE SPAWN

            break;

            case STARTING:
                resetAllTarget();
                healthManager.setGameOn(true);
                SpawnManager.spawnVictime();
                if(targetManager.getTarget() != null) {
                    chunkLoad.loadChunks(targetManager.getTarget().getLocation());
                } else {
                    System.out.println("Attention la targetLocation est null");
                    Bukkit.broadcast(Component.text("Attention la targetLocation est null"));
                    setGameState(GameState.LOBBY);
                }


                //On récupère les joueurs présents
                //players = new ArrayList<>(Bukkit.getOnlinePlayers());

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if(!players.contains(p) && !p.isOp())
                    {
                        p.kick();
                    }

                }
                gameStatePhase = 0;


                Bukkit.broadcast(Component.text("La partie va commencer !").color(NamedTextColor.BLUE));
                Bukkit.broadcast(Component.text("Vous avez une minute pour faire vos achats !").color(NamedTextColor.BLUE));

                //Clear inventory
                for (Player p : players) {
                    p.getInventory().clear();
                    p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, plugin.getConfig().getInt("orDeDépart"))); //Don de Gn selon le fichier de config
                    p.sendMessage("Ton inventaire est vidé mais on t'as donné "+ plugin.getConfig().getInt("orDeDépart") +" Gold Nuggets");
                    p.setHealth(20);
                    p.setFoodLevel(20);
                }

                countdown = 40;
                countDownNextPhase = new CountDownNextPhase();
                countDownNextPhase.runTaskTimer(plugin, 0L, 20L);
                gameStarted = true;
                break;

            case SHOP:
                Bukkit.broadcast(Component.text("Bravo vous avez survecu jusque là !").color(NamedTextColor.BLUE));
                Bukkit.broadcast(Component.text("Vous avez une minute pour faire vos achats !").color(NamedTextColor.BLUE));
                countdown = 30;
                countDownNextPhase = new CountDownNextPhase();

                countDownNextPhase.runTaskTimer(plugin, 0L, 20L);
            break;

            case LEVEL1:
                waveManager = new WaveManager(1, 450,"Quelques Zombies et squelette pour se mettre en jambe",
                        players,this,getTarget(),listMonstreManager);  // Niveau 1 avec des vagues tous les 400 tic donc 20 sec
                waveManager.addMonstre(4,EntityType.ZOMBIE);
                //waveManager.addMonstre(1,EntityType.SKELETON);
                waveManager.addMonstreFinal(1, EntityType.STRAY);
                waveManager.startWave();
            break;

            case LEVEL2:
                waveManager = new WaveManager(2, 500,"Et si on ajoutais nos meilleurs amis ?",
                        players,this,getTarget(),listMonstreManager);  // Niveau 1 avec des vagues tous les 400 tic donc 20 sec
                waveManager.addMonstre(3,EntityType.ZOMBIE);
                waveManager.addMonstre(2,EntityType.SKELETON);
                waveManager.addMonstre(1,EntityType.CREEPER);
                waveManager.addMonstreFinal(1, EntityType.ENDERMAN);
                waveManager.startWave();
            break;

            case LEVEL3:
                waveManager = new WaveManager(3, 600,"J'espère que vous avez pris un bouclier...",
                        players,this,getTarget(),listMonstreManager);  // Niveau 1 avec des vagues tous les 400 tic donc 20 sec
                waveManager.addMonstre(3,EntityType.ZOMBIE);
                waveManager.addMonstre(4,EntityType.SKELETON);
                waveManager.addMonstre(2,EntityType.CREEPER);
                waveManager.addMonstreFinal(10, EntityType.SILVERFISH);
                waveManager.startWave();
             break;

            case LEVEL4:
                waveManager = new WaveManager(4, 650,"Vous avez pas tout misé sur l'arc n'est ce pas ?",
                        players,this,getTarget(),listMonstreManager);  // Niveau 1 avec des vagues tous les 400 tic donc 20 sec
                waveManager.addMonstre(5,EntityType.ZOMBIE);
                waveManager.addMonstre(4,EntityType.SKELETON);
                waveManager.addMonstre(2,EntityType.CREEPER);
                waveManager.addMonstre(1,EntityType.ENDERMAN);
                waveManager.addMonstreFinal(1, EntityType.GHAST);
                waveManager.startWave();

            break;

            case LEVEL5:
                waveManager = new WaveManager(5, 700,"Vous avez pas tout misé sur l'arc n'est ce pas ?",
                        players,this,getTarget(),listMonstreManager);  // Niveau 1 avec des vagues tous les 400 tic donc 20 sec
                waveManager.addMonstre(6,EntityType.ZOMBIE);
                waveManager.addMonstre(5,EntityType.SKELETON);
                waveManager.addMonstre(3,EntityType.CREEPER);
                waveManager.addMonstre(1,EntityType.WITCH);
                waveManager.addMonstreFinal(6, EntityType.WITHER_SKELETON);
                waveManager.startWave();

            break;

            case WAVESPECIAL1:

                float random = (float)(Math.random()*players.size()-0.5);
                Bukkit.broadcast(Component.text(random));
                int joueurSelect = Math.round((random));
                for (Player p : players) {
                    p.sendTitle("Vague Special ","Protégez "+players.get(joueurSelect).getName()+" !!!!",5,100,5);
                }
                location = plugin.getConfig().getLocation("spawnLocation"+(joueurSelect+1));
                SpawnManager.spawn(3,EntityType.ENDERMAN,location,(LivingEntity)players.get(joueurSelect),listMonstreManager);
                wave = 5;

            break;

            case BOSS:

                for (Player p : players) {
                    p.sendTitle("BOSS FINAL","Bonne chance",5,100,5);
                    p.playSound(p.getLocation(),Sound.ENTITY_ENDER_DRAGON_GROWL,10,1);
                }
                location = plugin.getConfig().getLocation("spawnLocation1");
                SpawnManager.spawn(1,EntityType.ENDER_DRAGON,location,getTarget(),listMonstreManager);
                wave = 5;

            break;

            case WON:  //TODO APPRENDRE A GERER UN ENDER DRAGON
                for (Player p : players) {
                    p.sendTitle("VICTOIRE !","Incroyable, vous êtes venu à bout du jeux !",5,100,5);
                    p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE,1,1);
                    p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST,1,1);
                }
                    listMonstreManager.clear();
                    countdown = 5;
                    countDownNextPhase = new CountDownNextPhase();
                    countDownNextPhase.runTaskTimer(plugin, 0L, 20L);
                    gameStarted = false;
            break;



            case LOOSE:
                for (Player p : players) {
                    p.sendTitle("GAME OVER", "", 5, 100, 5);
                }
                    gameStarted = false;
                    listMonstreManager.clear();
                    countdown = 5;
                    CountDownLoose countDownLoose = new CountDownLoose();
                    countDownLoose.runTaskTimer(plugin, 0L, 20L);


                break;

            default:
                Bukkit.broadcast(Component.text("Cette phase n'est pas codée, WIP retour au lobby"));
                setGameState(GameState.LOBBY);
            break;


        }


    }

    public void resetAllTarget() {
        new BukkitRunnable() {
            @Override
            public void run() {
                resetMonstreTarget();
                perteDePoints();
                if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || getGameState() == GameState.WON)
                {
                    this.cancel();
                }


            }
        }.runTaskTimer(McSiege.getPlugin(), 0, 20); // exécute la tâche toutes les secondes (20 ticks)

    }
}

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
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameManager {

    private McSiege plugin;
    private int vies =100;
    private HealthManager healthManager;
    private int openWaves;
    private GameState gameState = GameState.LOBBY;
    private int gameStatePhase ;

    public boolean isGameStarted() {
        return gameStarted;
    }

    private boolean gameStarted = false;
    private final ListMonstreManager listMonstreManager;
    private final TargetManager targetManager;

    private final SpawnManager spawnManager;


    public List<LivingEntity> getVillagerShops() {
        return villagerShops;
    }

    private List<LivingEntity> villagerShops;

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player player) {
        int joeurMax = McSiege.getPlugin().getConfig().getInt("JoueurMax");

        this.players.add(player);
        Bukkit.broadcast(Component.text(player.getName()+" est prèt !"));


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

    public int getWave() {
        return wave;
    }

    int wave;



    public Map<String, Integer> prix = new HashMap<>();
    public GameManager(McSiege plugin)
    {
        this.plugin = plugin;
        this.listMonstreManager = new ListMonstreManager(plugin);
        targetManager = new TargetManager();
        healthManager = new HealthManager(plugin.getConfig().getInt("ViesMax"));
        spawnManager = new SpawnManager(this);
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
            case 4 -> setGameState(GameState.LEVEL3);
            case 5 -> setGameState(GameState.SHOP);
            case 6 -> setGameState(GameState.WAVESPECIAL1);
            case 7 -> setGameState(GameState.LEVEL4);
            case 8 -> setGameState(GameState.SHOP);
            case 9 -> setGameState(GameState.LEVEL5);
            case 10 -> setGameState(GameState.WON);
            default -> setGameState(GameState.LOBBY);
        }

        return gameState;
    }

    public void RecompenseFinWave ()
    {
        int gain = 10; //TODO METTRE CETTE VALEUR DANS LE CONFIG
        for (Player p : players
             ) {

            p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, gain));
            p.sendMessage(Component.text("Vous gagnez ").color(NamedTextColor.BLUE)
                    .append(Component.text(gain).color(NamedTextColor.GOLD))
                    .append(Component.text("Gold Nuggets pour avoir survécu à la wave").color(NamedTextColor.BLUE)));
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
                        .append(Component.text("/setWeaponShop et /setArmorShop")
                                .color(NamedTextColor.RED)
                                .decoration(TextDecoration.BOLD, true)));
            break;

            case LOBBY :
                gameStarted = false;
                Clear(); //On clear tout ce qui peut rester des anciennes games
                chunkLoad.unloadChunks(); //On unload les chunks pour si le jeu à planté ou si les joueurs ont fini une partie
                healthManager.resetHealthManager();
                players.clear();
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
                resetAllTarget();
                healthManager.setGameOn(true);
                spawnManager.spawnVictime();
                if(targetManager.getTarget() != null) {
                    chunkLoad.loadChunks(targetManager.getTarget().getLocation());
                } else {
                    System.out.println("Attention la targetLocation est null");
                    Bukkit.broadcast(Component.text("Attention la targetLocation est null"));
                    setGameState(GameState.LOBBY);
                }

                gameStarted = true;
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

                countdown = 10;
                countDownNextPhase = new CountDownNextPhase();
                countDownNextPhase.runTaskTimer(plugin, 0L, 20L);

                break;

            case SHOP:
                Bukkit.broadcast(Component.text("Bravo vous avez survecu jusque là !").color(NamedTextColor.BLUE));
                Bukkit.broadcast(Component.text("Vous avez une minute pour faire vos achats !").color(NamedTextColor.BLUE));
                countdown = 30;
                countDownNextPhase = new CountDownNextPhase();

                countDownNextPhase.runTaskTimer(plugin, 0L, 20L);
            break;

            case LEVEL1:
                Bukkit.broadcast(Component.text("C'est parti pour la vague 1 !"));
                for (Player p : players) {
                    p.sendTitle("Niveau 1","Quelques zombies et squelettes pour se mettre en jambe !",5,100,5);
                }
                wave = 0;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave ++;
                        if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || wave > 5)
                        {
                            this.cancel();
                            return;
                        }

                        Bukkit.broadcast(Component.text("Vague :" + wave));
                        for (int i = 1; i <= openWaves; i++) {
                            Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                            spawnManager.spawn(2,EntityType.ZOMBIE ,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(1,EntityType.SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            if(wave == 5)
                            {
                                spawnManager.spawn(1,EntityType.WITHER_SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            }
                        }



                    }
                }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)



            break;

            case LEVEL2:
                for (Player p : players) {
                    p.sendTitle("Niveau 2","Et si on ajoutais nos meilleurs amis ?",5,100,5);
                }
                wave = 0;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave ++;
                        if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || wave > 5)
                        {
                            this.cancel();
                            return;
                        }

                        Bukkit.broadcast(Component.text("Vague :" + wave));
                        for (int i = 1; i <= openWaves; i++) {
                            Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                            spawnManager.spawn(3,EntityType.ZOMBIE ,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(2,EntityType.SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(1,EntityType.CREEPER,location,targetManager.getTarget(),listMonstreManager);
                            if(wave == 5)
                            {
                                spawnManager.spawn(1,EntityType.ENDERMAN,location,targetManager.getTarget(),listMonstreManager);
                            }
                        }

                        System.out.println(wave);

                    }
                }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)
            break;

            case LEVEL3:
                for (Player p : players) {
                    p.sendTitle("Niveau 3","J'espère que vous avez pris un bouclier",5,100,5);
                }
                wave = 0;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave ++;
                        if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || wave > 5)
                        {
                            this.cancel();
                            return;
                        }

                        Bukkit.broadcast(Component.text("Vague :" + wave));
                        for (int i = 1; i <= openWaves; i++) {
                            Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                            spawnManager.spawn(1,EntityType.ZOMBIE ,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(4,EntityType.SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(2,EntityType.CREEPER,location,targetManager.getTarget(),listMonstreManager);
                            if(wave == 5)
                            {
                                spawnManager.spawn(10,EntityType.SILVERFISH,location,targetManager.getTarget(),listMonstreManager);
                            }
                        }

                    }
                }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)
                break;

            case LEVEL4:
                for (Player p : players) {
                    p.sendTitle("Niveau 4","Vous avez pas tout misé sur l'arc n'est ce pas ?",5,100,5);
                }
                wave = 0;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave ++;
                        if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || wave > 5)
                        {
                            this.cancel();
                            return;
                        }

                        Bukkit.broadcast(Component.text("Vague :" + wave));
                        for (int i = 1; i <= openWaves; i++) {
                            Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                            spawnManager.spawn(8,EntityType.ZOMBIE ,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(6,EntityType.SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(4,EntityType.CREEPER,location,targetManager.getTarget(),listMonstreManager);
                            if(wave == 5)
                            {
                                spawnManager.spawn(6,EntityType.ENDERMAN,location,targetManager.getTarget(),listMonstreManager);
                            }
                        }
                        wave ++;


                    }
                }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)
            break;

            case LEVEL5:
                for (Player p : players) {
                    p.sendTitle("Vague 5","Je savais même pas que ça existait ces trucs !",5,100,5);
                }
                wave = 0;
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        wave ++;
                        if(getGameState() == GameState.LOBBY || getGameState() == GameState.LOOSE || wave > 5)
                        {
                            this.cancel();
                            return;
                        }

                        Bukkit.broadcast(Component.text("Vague :" + wave));
                        for (int i = 1; i <= openWaves; i++) {
                            Location location = plugin.getConfig().getLocation("spawnLocation"+i);
                            spawnManager.spawn(10,EntityType.ZOMBIE ,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(8,EntityType.SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(6,EntityType.CREEPER,location,targetManager.getTarget(),listMonstreManager);
                            spawnManager.spawn(4,EntityType.ENDERMAN,location,targetManager.getTarget(),listMonstreManager);
                            if(wave == 5)
                            {
                                spawnManager.spawn(6,EntityType.WITHER_SKELETON,location,targetManager.getTarget(),listMonstreManager);
                            }
                        }

                    }
                }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)
            break;

            case WON:
                for (Player p : players) {
                    p.sendTitle("Vague 5","Je savais même pas que ça existait ces trucs !",5,100,5);
                }
                    countdown = 5;
                    countDownNextPhase = new CountDownNextPhase();
                    countDownNextPhase.runTaskTimer(plugin, 0L, 20L);
            break;

            case WAVESPECIAL1:

                float random = (float)(Math.random()*players.size());
                int joueurSelect = Math.round(random);
                Location location = plugin.getConfig().getLocation("spawnLocation"+joueurSelect);
                spawnManager.spawn(2,EntityType.ENDERMAN,location,(LivingEntity)players.get(joueurSelect),listMonstreManager);

                break;

            case LOOSE:
                for (Player p : players) {
                    p.sendTitle("GAME OVER", "", 5, 100, 5);
                }
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

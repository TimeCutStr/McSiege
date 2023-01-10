package me.timecutstr.mcsiege.manager;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaveManager {

    private ArrayList<Player> players;
    private BukkitTask bukkitTask;
    private GameManager gameManager;
    private Entity target;
    private ListMonstreManager listMonstreManager;

    private int niveau;
    String ssTtire;
    Map<Integer, Mob> mapMonstre;
    Map<Integer, Mob> mapMonstreFinal;


    public WaveManager (int niveau, String ssTitre, ArrayList<Player> players, GameManager gameManager, Entity target, ListMonstreManager listMonstreManager)
    {
        //On récupère depuis le gameManager les différents manageurs
        this.niveau = niveau;
        this.ssTtire = ssTitre;
        this.players = players;
        this.gameManager = gameManager;
        this.target = target;
        this.listMonstreManager = listMonstreManager;
        this.mapMonstre = new HashMap<>();  //On instancifie les deux map
        this.mapMonstreFinal = new HashMap<>();

    }

    public void addMonstre (int nb, Mob monstre)  //Méthode pour ajouter un monstre à spawn
    {
        mapMonstre.put(Integer.valueOf(nb),monstre);
    }

    public void addMonstreFinal (int nb, Mob monstre)  //Méthode pour ajouter un monstre à spawn uniquement sur la dernière vague
    {
        mapMonstreFinal.put(Integer.valueOf(nb),monstre);
    }

    public void startWave() //Méthode à appeler pour lancer les vagues
    {
        Bukkit.broadcast(Component.text("C'est parti pour le niveau ").color(NamedTextColor.GRAY)
                .append(Component.text(niveau).color(NamedTextColor.RED))
                .append(Component.text(" !").color(NamedTextColor.GRAY)));
        for (Player p : players) {


            Component compTitre = Component.text("Niveau " + niveau).color(NamedTextColor.RED);
            Component compSSTitre = Component.text(ssTtire);

            Title titre = Title.title(compTitre, compSSTitre);
            p.sendTitle("Vague 5","Je savais même pas que ça existait ces trucs !",5,100,5);
        }

        gameManager.setWave(0);
        bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                gameManager.setWave((gameManager.getWave()+1));
                if(gameManager.isGameStarted() == false || gameManager.getWave() > 5)
                {
                    this.cancel();
                    return;
                }

                Bukkit.broadcast(Component.text("Vague :" + gameManager.getWave()));
                for (int i = 1; i <= gameManager.getOpenWaves(); i++) {
                    Location location = McSiege.getPlugin().getConfig().getLocation("spawnLocation"+i);
                    for (Map.Entry<Integer, Mob> entry : mapMonstre.entrySet())
                    {
                        SpawnManager.spawn(entry.getKey().intValue(), entry.getValue().getType() ,location,target,listMonstreManager);
                    }

                    if(gameManager.getWave() == 5)
                    {
                        for (Map.Entry<Integer, Mob> entry : mapMonstreFinal.entrySet())
                        {
                            SpawnManager.spawn(entry.getKey().intValue(), entry.getValue().getType() ,location,target,listMonstreManager);
                        }
                    }
                }

            }
        }.runTaskTimer(McSiege.getPlugin(), 0, 600); // exécute la tâche toutes les 30 secondes (20 ticks)
    }
}

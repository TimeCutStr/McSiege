package me.timecutstr.mcsiege.manager;

import org.bukkit.entity.Entity;

public class GameManager {

    private Entity target;


    private GameManager()
    {}

    private static GameManager INSTANCE = new GameManager();

    public static GameManager getGameManager ()
    {
        return INSTANCE;

    }

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
}

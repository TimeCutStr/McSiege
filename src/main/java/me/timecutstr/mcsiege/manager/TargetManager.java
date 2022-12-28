package me.timecutstr.mcsiege.manager;

import org.bukkit.entity.Entity;

public class TargetManager {
    private Entity target;

    public Entity getTarget() {
        if(target == null)
        {
        }
        return target;
    }

    public void setTarget(Entity target) {
        if(target == null)
        {
            System.out.println("TARGET NOT SET");
        }
        this.target = target;
    }



}

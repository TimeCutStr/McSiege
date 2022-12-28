package me.timecutstr.mcsiege.shop;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;

public class ArmorShop {
    private final LivingEntity villagerArmorShop;

    public ArmorShop (Location location)
    {
        villagerArmorShop = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villagerArmorShop.setCustomName("Magasin d'armure");
        ((Mob)villagerArmorShop).setAware(false);
        villagerArmorShop.setCustomNameVisible(true);
        villagerArmorShop.setInvulnerable(true);
    }

    public LivingEntity getVillagerArmorShop ()
    {
        return villagerArmorShop;
    }
}

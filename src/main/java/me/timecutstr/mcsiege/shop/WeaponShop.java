package me.timecutstr.mcsiege.shop;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class WeaponShop {
    final LivingEntity villagerWeaponShop;

    public WeaponShop (Location location)
    {
        villagerWeaponShop = (LivingEntity) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villagerWeaponShop.setCustomName("Magasin d'arme");
        villagerWeaponShop.setCustomNameVisible(true);
        villagerWeaponShop.setInvulnerable(true);
    }

    public LivingEntity getVillagerWeaponShop ()
    {
        return villagerWeaponShop;
    }
}

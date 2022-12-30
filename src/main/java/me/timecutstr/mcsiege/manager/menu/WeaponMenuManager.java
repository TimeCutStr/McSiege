package me.timecutstr.mcsiege.manager.menu;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponMenuManager {
    private final Inventory weaponmenu;
    public Map<String, Integer> prix;
    McSiege plugin = McSiege.getPlugin();

    public WeaponMenuManager(Player p) {

        weaponmenu = Bukkit.createInventory(p,27, Component.text("Weapon Menu"));

        weaponmenu.addItem(AddEquipment(Material.WOODEN_SWORD));
        weaponmenu.addItem(AddEquipment(Material.STONE_SWORD));
        weaponmenu.addItem(AddEquipment(Material.GOLDEN_SWORD));
        weaponmenu.addItem(AddEquipment(Material.IRON_SWORD));
        weaponmenu.addItem(AddEquipment(Material.DIAMOND_SWORD));
        weaponmenu.addItem(AddEquipment(Material.NETHERITE_SWORD));
        weaponmenu.addItem(AddEquipment(Material.BOW));
        weaponmenu.addItem(AddEquipment(Material.ARROW, 64));
        weaponmenu.addItem(AddEquipment(Material.SHIELD));



    }

    public Inventory getWeaponmenu() {
        return weaponmenu;
    }

    private ItemStack AddEquipment (Material material)
    {
        //FONCTION POUR AJOUTER UN ITEM AVEC UN PRIX A CHAQUE ITEM
        int price =  plugin.getConfig().getConfigurationSection("prix").getInt(material.name());
        // TODO faire une verif que c'est bien un int
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> epeeLore = new ArrayList<>();
        epeeLore.add(Component.text(price + " Gold Nugget").color(NamedTextColor.GOLD));
        itemMeta.lore(epeeLore);
        item.setItemMeta(itemMeta);

        return item;
    }

    private ItemStack AddEquipment (Material material, int nombre)
    {
        //FONCTION POUR AJOUTER UN ITEM AVEC UN PRIX A CHAQUE ITEM
        int price =  plugin.getConfig().getConfigurationSection("prix").getInt(material.name());
        // TODO faire une verif que c'est bien un int
        ItemStack item = new ItemStack(material, nombre);
        ItemMeta itemMeta = item.getItemMeta();
        List<Component> epeeLore = new ArrayList<>();
        epeeLore.add(Component.text(price + " Gold Nugget").color(NamedTextColor.GOLD));
        itemMeta.lore(epeeLore);
        item.setItemMeta(itemMeta);

        return item;
    }
}

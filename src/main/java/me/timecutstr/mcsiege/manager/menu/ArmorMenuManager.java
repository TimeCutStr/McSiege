package me.timecutstr.mcsiege.manager.menu;

import me.timecutstr.mcsiege.McSiege;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArmorMenuManager {
    private final Inventory armorMenu;
    public Map<String, Integer> prix;
    McSiege plugin = McSiege.getPlugin();

    public ArmorMenuManager(Player p) {

        armorMenu = Bukkit.createInventory(p,27, Component.text("Marchand Menu"));

        armorMenu.addItem(AddEquipment(Material.LEATHER_HELMET));
        armorMenu.addItem(AddEquipment(Material.LEATHER_CHESTPLATE));
        armorMenu.addItem(AddEquipment(Material.LEATHER_LEGGINGS));
        armorMenu.addItem(AddEquipment(Material.LEATHER_BOOTS));
        armorMenu.addItem(AddEquipment(Material.IRON_HELMET));
        armorMenu.addItem(AddEquipment(Material.IRON_CHESTPLATE));
        armorMenu.addItem(AddEquipment(Material.IRON_LEGGINGS));
        armorMenu.addItem(AddEquipment(Material.IRON_BOOTS));
        armorMenu.addItem(AddEquipment(Material.DIAMOND_HELMET));
        armorMenu.addItem(AddEquipment(Material.DIAMOND_CHESTPLATE));
        armorMenu.addItem(AddEquipment(Material.DIAMOND_LEGGINGS));
        armorMenu.addItem(AddEquipment(Material.DIAMOND_BOOTS));
        armorMenu.addItem(AddEquipment(Material.NETHERITE_HELMET));
        armorMenu.addItem(AddEquipment(Material.NETHERITE_CHESTPLATE));
        armorMenu.addItem(AddEquipment(Material.NETHERITE_LEGGINGS));
        armorMenu.addItem(AddEquipment(Material.NETHERITE_BOOTS));
        armorMenu.addItem(AddEquipment(Material.GOLDEN_APPLE));



    }

    public Inventory getArmorMenu() {
        return armorMenu;
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
}

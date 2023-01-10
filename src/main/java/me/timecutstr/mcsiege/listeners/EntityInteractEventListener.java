package me.timecutstr.mcsiege.listeners;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import me.timecutstr.mcsiege.manager.menu.ArmorMenuManager;
import me.timecutstr.mcsiege.manager.menu.WeaponMenuManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;


public class EntityInteractEventListener implements Listener {
    GameManager gameManager = McSiege.getPlugin().getGameManager();


    @EventHandler
    public void OuverturMenuEquipement(PlayerInteractAtEntityEvent event)
    {
        Player p = event.getPlayer();
        // if(event.getRightClicked() instanceof Block  TODO Regarder comment gérer les interactions avec les panneaux

        if(event.getRightClicked() instanceof Villager villager) {
            if (villager.getName().equals("WeaponShop")) {
                event.setCancelled(true);
                WeaponMenuManager menu = new WeaponMenuManager(p);
                p.openInventory(menu.getWeaponmenu());
            }

            if (villager.getName().equals("ArmorShop")) {
                event.setCancelled(true);
                ArmorMenuManager menu = new ArmorMenuManager(p);
                p.openInventory(menu.getArmorMenu());
            }

            if (villager.getName().equals("RevendeurShop")) {
                event.setCancelled(true);

                ItemStack item = p.getInventory().getItemInMainHand(); // On récupère l'item dans la main
                if(item.getType() == Material.ARROW)
                {
                    p.sendMessage(Component.text("Tu ne peux pas vendre des flèches !").color(NamedTextColor.RED));
                    return;
                }

                int price =  McSiege.getPlugin().getConfig().getConfigurationSection("prix").getInt(item.getType().name()); // On regarde le materiel de l'item
                //Et on récupère son prix dans le fichier de config (on pourrait aussi passer par le lore ?)

                Map<Enchantment, Integer> enchant = item.getEnchantments();

                for (Map.Entry<Enchantment, Integer> entry : enchant.entrySet()) {
                    price = price + (10 * entry.getValue());
                }

                p.getInventory().clear(p.getInventory().getHeldItemSlot()); //on enlève l'item porté

                price = price*3/4; //On enlève les 3/4 du prix

                p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, price)); //On rembourse le joueur
            }

        }
    }

}

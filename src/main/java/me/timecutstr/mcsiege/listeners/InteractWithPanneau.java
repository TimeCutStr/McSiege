package me.timecutstr.mcsiege.listeners;

import com.destroystokyo.paper.MaterialSetTag;
import com.destroystokyo.paper.MaterialTags;
import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.staticMethode.PlayerPay;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class InteractWithPanneau implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        // Vérifiez que l'action de l'événement est un clic droit sur un bloc
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }


        if (event.getClickedBlock().getState() instanceof Sign sign) {
            if (sign.getLine(0).contains("Sharpness")) {
                ItemStack sword = event.getItem();
                if (sword.getType() == Material.IRON_SWORD ||
                        sword.getType() == Material.STONE_SWORD ||
                        sword.getType() == Material.GOLDEN_SWORD ||
                        sword.getType() == Material.DIAMOND_SWORD ||
                        sword.getType() == Material.WOODEN_SWORD ||
                        sword.getType() == Material.NETHERITE_SWORD) {

                    int price = McSiege.getPlugin().getConfig().getInt("PrixEnchantement");

                    Boolean capableDePayer = PlayerPay.pay(p, price); //On fait payer le joueur le prix. Si il peut on lui donne l'enchantement

                    if (capableDePayer) {
                        ItemMeta swordMeta = sword.getItemMeta();
                        if (swordMeta.hasEnchant(Enchantment.DAMAGE_ALL)) {
                            int level = swordMeta.getEnchantLevel(Enchantment.DAMAGE_ALL);
                            level++;
                            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, level, true);
                            sword.setItemMeta(swordMeta);
                        } else {
                            swordMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                            sword.setItemMeta(swordMeta);
                        }

                    } else {
                        p.sendMessage("Tu n'as pas assez d'argent !");

                    }


                } else {
                    p.sendMessage("Pour ajouter Sharpness à ton épée tu dois l'avoir dans la main.");
                }
            }
            if (sign.getLine(0).contains("Power")) {
                ItemStack bow = event.getItem();
                if (bow.getType() == Material.BOW) {

                    int price = McSiege.getPlugin().getConfig().getInt("PrixEnchantement");

                    Boolean capableDePayer = PlayerPay.pay(p, price); //On fait payer le joueur le prix. Si il peut on lui donne l'enchantement

                    if (capableDePayer) {
                        ItemMeta bowMeta = bow.getItemMeta();
                        if (bowMeta.hasEnchant(Enchantment.ARROW_DAMAGE)) {
                            int level = bowMeta.getEnchantLevel(Enchantment.ARROW_DAMAGE);
                            level++;
                            bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, level, true);
                            bow.setItemMeta(bowMeta);
                        } else {
                            bowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
                            bow.setItemMeta(bowMeta);
                        }

                    } else {
                        p.sendMessage("Tu n'as pas assez d'argent !");

                    }


                } else {
                    p.sendMessage("Pour ajouter Power à ton arc tu dois l'avoir dans la main.");
                }

            }

            if (sign.getLine(0).contains("Protection")) {
                ItemStack armure = event.getItem();

                if (MaterialTags.ARMOR.isTagged(armure)) {

                    int price = McSiege.getPlugin().getConfig().getInt("PrixEnchantement");

                    Boolean capableDePayer = PlayerPay.pay(p, price); //On fait payer le joueur le prix. Si il peut on lui donne l'enchantement

                    if (capableDePayer) {
                        ItemMeta armureMeta = armure.getItemMeta();
                        if (armureMeta.hasEnchant(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                            int level = armureMeta.getEnchantLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
                            level++;
                            armureMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, level, true);
                            armure.setItemMeta(armureMeta);
                        } else {
                            armureMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
                            armure.setItemMeta(armureMeta);
                        }


                    } else {
                        p.sendMessage("Tu n'as pas assez d'argent !");

                    }


                } else {
                    p.sendMessage("Pour ajouter Protections sur ta pièce d'armure, tu dois l'avoir dans la main.");
                }

            }


        }


    }
}


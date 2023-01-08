package me.timecutstr.mcsiege.staticMethode;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class  PlayerPay {

     public static boolean pay (Player player, int price)
    {
        if(player.getInventory().containsAtLeast(new ItemStack(Material.GOLD_NUGGET), price)) //S'il y a des goldNuggets dans l'inventaire
        {
            int resteAPayer = price;
            //on extrait le contenu de l'inventaire pour trouver les gold nugget
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack itemDansSlot = player.getInventory().getItem(i);
                if(itemDansSlot != null) {
                    if (itemDansSlot.getType() == Material.GOLD_NUGGET) {
                        int gnDansLeSlot = itemDansSlot.getAmount();
                        if (gnDansLeSlot >= resteAPayer) {
                            player.getInventory().getItem(i).setAmount(gnDansLeSlot - resteAPayer);
                            resteAPayer = 0;
                            return true;
                        } else {
                            resteAPayer -= gnDansLeSlot;
                            player.getInventory().getItem(i).setAmount(0);

                        }
                    }
                }
            }
            return true;
        }
        else {
            player.sendMessage("Tu n'as pas assez d'argent !");
            return false;
        }
    }
}

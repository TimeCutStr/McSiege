package me.timecutstr.mcsiege.manager.BukkitRunnable;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDownNextPhase extends BukkitRunnable {
    @Override
    public void run() {
        int countdown = McSiege.getPlugin().getGameManager().getCountdown();
        if (countdown <= 0) {
            McSiege.getPlugin().getGameManager().NextGameState();
            this.cancel();
            return;

        }

        if(McSiege.getPlugin().getGameManager().getGameState() == GameState.LOBBY)
        {
            this.cancel();
        }
        Bukkit.broadcast(Component.text("Prochaine phase dans "+countdown+" secondes.").color(NamedTextColor.BLUE));
        countdown --;
        McSiege.getPlugin().getGameManager().setCountdown(countdown);

    }
}

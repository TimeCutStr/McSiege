package me.timecutstr.mcsiege.HUD;

import me.timecutstr.mcsiege.McSiege;
import me.timecutstr.mcsiege.manager.GameManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nullable;

public class HealthBar {

    GameManager gm = McSiege.getPlugin().getGameManager();

    final Component name = Component.text("Vies Restantes : " + gm.healthManager.getVies());

    private @Nullable BossBar activeBar = BossBar.bossBar(name, 1, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);;

    public void showMyBossBar(final @NonNull Audience target) {


        // Send a bossbar to your audience
        target.showBossBar(this.activeBar);

    }

    public void updateBossBar()
    {
        float ratio = 0;
        if(gm.healthManager.getVies() > 0){
             ratio = (float) gm.healthManager.getVies()/(float) gm.healthManager.getViesMax();
        }

        this.activeBar.progress(ratio);



        this.activeBar.name(updateName());


    }

    public Component updateName ()
    {
        final Component name = Component.text("Vies Restantes : " + gm.healthManager.getVies());
        return name;
    }

    public void hideActiveBossBar(final @NonNull Audience target) {
        target.hideBossBar(this.activeBar);
        this.activeBar = null;
    }

}

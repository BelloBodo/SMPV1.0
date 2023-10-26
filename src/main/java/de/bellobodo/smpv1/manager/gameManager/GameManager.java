package de.bellobodo.smpv1.manager.gameManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.WorldBorder;

public class GameManager {

    private final SMPV1 smpv1;

    public GameManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        Bukkit.getLogger().info("[SMPV1] Start Building GameManager");


        this.gameIsActive = smpv1.getConfig().getBoolean("gameisactive", true);
        Bukkit.getLogger().info("GameIsActive: " + gameIsActive);


        reloadBorder();

        Bukkit.getLogger().info("[SMPV1] End Building GameManager");

    }

    private boolean gameIsActive;

    public void reloadBorder() {
        Bukkit.getWorlds().forEach(worlds -> {
            WorldBorder worldBorder = worlds.getWorldBorder();
            worldBorder.setCenter(0, 0);
            if (!gameIsActive) worldBorder.setSize(smpv1.getConfig().getInt("worldborderradius", 200) * 2);
            else worldBorder.reset();
        });
    }

    public boolean gameIsActive() {
        return gameIsActive;
    }

    public void setGameIsActive(boolean gameIsActive) {
        this.gameIsActive = gameIsActive;
        smpv1.getConfig().set("gameisactive", gameIsActive);
        smpv1.saveConfig();
    }
}

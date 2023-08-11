package de.bellobodo.smpv1.manager;

import de.bellobodo.smpv1.SMPV1;
import de.bellobodo.smpv1.manager.playerManager.PlayerManager;
import org.bukkit.entity.Player;

public class GameManager {

    private final SMPV1 smpv1;

    public GameManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        flagManager = new FlagManager(smpv1);
        playerManager = new PlayerManager(smpv1);
    }

    private FlagManager flagManager;

    public FlagManager getFlagManager() {
        return flagManager;
    }

    private PlayerManager playerManager;

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

}

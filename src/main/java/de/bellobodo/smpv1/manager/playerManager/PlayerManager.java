package de.bellobodo.smpv1.manager.playerManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerManager {

    private final SMPV1 smpv1;

    public PlayerManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        FileConfiguration config = smpv1.getConfig();

        //Create Clans from Config
        for (String configClans : config.getStringList("clans")) {
            Clan clan = new Clan(configClans);

            for (String configMembers:config.getStringList("clans." + configClans)) {
                if (clan.getLeader() == null) clan.setLeader(UUID.fromString(configMembers));
                else clan.addMember(UUID.fromString(configMembers));

                whitlistedPlayers.add(UUID.fromString(configMembers));
            }
        }

        //Create Söldner from Config
        for (String configSöldner : config.getStringList("söldner")) {
            söldner.add(UUID.fromString(configSöldner));

            whitlistedPlayers.add(UUID.fromString(configSöldner));
        }

        //Create Spectator from Config
        for (String configSpectator : config.getStringList("spectator")) {
            spectators.add(UUID.fromString(configSpectator));

            whitlistedPlayers.add(UUID.fromString(configSpectator));
        }

        //Clear Whitelisted Players
        for (OfflinePlayer offlinePlayer:Bukkit.getWhitelistedPlayers()) {
            offlinePlayer.setWhitelisted(false);
        }

        //Add new Whitelisted Players
        reloadWhitelist();
    }

    private void reloadWhitelist() {
        whitlistedPlayers.forEach(whitlistedPlayers -> {
            Bukkit.getOfflinePlayer(whitlistedPlayers).setWhitelisted(true);
        });

        Bukkit.reloadWhitelist();
    }

    private ArrayList<UUID> whitlistedPlayers = new ArrayList<>();

    private ArrayList<Clan> clans = new ArrayList<>();

    private ArrayList<UUID> söldner = new ArrayList<>();

    private ArrayList<UUID> spectators = new ArrayList<>();


//TODO METHODS

}

package de.bellobodo.smpv1.manager.playerManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class PlayerManager {

    private final SMPV1 smpv1;

    private FileConfiguration config;

    public PlayerManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        config = smpv1.getConfig();

        //Create Clans from Config
        for (String configClans : config.getStringList("clans")) {
            Clan clan = new Clan(configClans);

            UUID clanLeader = UUID.fromString(Objects.requireNonNull(config.getString("clans." + configClans + ".leader")));
            whitlistedPlayers.add(clanLeader);

            for (String configMembers:config.getStringList("clans." + configClans + ".member")) {
                UUID clanMember = UUID.fromString(configMembers);
                clan.addMember(clanMember);
                whitlistedPlayers.add(clanMember);
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

        reloadWhitelist();
    }

    private void reloadWhitelist() {
        //Clear Whitelisted Players
        for (OfflinePlayer offlinePlayer:Bukkit.getWhitelistedPlayers()) {
            offlinePlayer.setWhitelisted(false);
        }

        //Add new Whitelisted Players
        whitlistedPlayers.forEach(whitlistedPlayers -> {
            Bukkit.getOfflinePlayer(whitlistedPlayers).setWhitelisted(true);
        });

        Bukkit.reloadWhitelist();
    }

    private HashSet<UUID> whitlistedPlayers = new HashSet<>();

    private ArrayList<Clan> clans = new ArrayList<>();

    private HashSet<UUID> söldner = new HashSet<>();

    private HashSet<UUID> spectators = new HashSet<>();

    public boolean createClan(String clanName) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                return false;
            }
        };
        clans.add(new Clan(clanName));

        config.set("clans." + clanName + ".leader", "");
        config.set("clans." + clanName + ".member", new HashSet<>());
        return true;
    }

    public boolean removeClan(String clanName) {

        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                for (UUID uuid:clan.getMembers()) {
                    whitlistedPlayers.remove(uuid);
                }

                whitlistedPlayers.remove(clan.getLeader());

                clans.remove(clan);
                config.set("clans." + clanName, null);
                return true;
            }
        };

        return false;
    }

    public boolean setClanLeader(String clanName,UUID uuid) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                if (clan.getLeader() != null) {
                    clan.addMember(clan.getLeader());

                    addToConfigFileList("clans." + clanName + ".members", clan.getLeader().toString());
                }
                clan.setLeader(uuid);
                whitlistedPlayers.add(uuid);

                config.set("clans." + clanName + ".leader", uuid.toString());
                return true;
            }
        };
        return false;
    }

    public boolean addClanMember(String clanName,UUID uuid) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                clan.addMember(uuid);
                whitlistedPlayers.add(uuid);

                addToConfigFileList("clans." + clanName + ".members", uuid.toString());
                return true;
            }
        };
        return false;
    }

    public boolean removeClanMember(String clanName,UUID uuid) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                clan.removeMember(uuid);
                whitlistedPlayers.remove(uuid);

                removeFromConfigFileList("clans." + clanName + ".members", uuid.toString());
                return true;
            }
        };
        return false;
    }

    public boolean addSöldner(UUID uuid) {
        söldner.add(uuid);
        whitlistedPlayers.add(uuid);
        return addToConfigFileList("söldner", uuid.toString());
    }

    public boolean removeSöldner(UUID uuid) {
        söldner.remove(uuid);
        whitlistedPlayers.remove(uuid);
        return removeFromConfigFileList("söldner", uuid.toString());
    }

    public boolean addSpectator(UUID uuid) {
        spectators.add(uuid);
        whitlistedPlayers.add(uuid);
        return addToConfigFileList("spectator", uuid.toString());
    }

    public boolean removeSpectator(UUID uuid) {
        spectators.remove(uuid);
        whitlistedPlayers.remove(uuid);
        return removeFromConfigFileList("spectator", uuid.toString());
    }

    private boolean addToConfigFileList(String location, String uuid) {
        Set<String> stringSet = new HashSet<>(Objects.requireNonNull(config.getStringList(location)));
         final boolean result = stringSet.add(uuid);

        config.set(location, stringSet);

        return result;
    }

    private boolean removeFromConfigFileList(String location, String uuid) {
        Set<String> stringSet = new HashSet<>(Objects.requireNonNull(config.getStringList(location)));
        final boolean result = stringSet.remove(uuid);

        config.set(location, stringSet);

        return result;
    }
}

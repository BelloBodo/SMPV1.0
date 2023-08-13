package de.bellobodo.smpv1.manager.playerManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerManager {

    private final SMPV1 smpv1;

    private FileConfiguration config;

    public PlayerManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;

        this.config = smpv1.getConfig();

        //Create Clans from Config
        for (String configClans : config.getStringList("clans")) {
            Clan clan = new Clan(configClans);

            UUID clanLeader = UUID.fromString(Objects.requireNonNull(config.getString("clans." + configClans + ".leader")));
            this.whitlistedPlayers.add(clanLeader);

            for (String configMembers:config.getStringList("clans." + configClans + ".member")) {
                UUID clanMember = UUID.fromString(configMembers);
                clan.addMember(clanMember);
                this.whitlistedPlayers.add(clanMember);
            }
            this.clans.add(clan);
        }

        //Create Söldner from Config
        for (String configSöldner : config.getStringList("söldner")) {
            this.söldner.add(UUID.fromString(configSöldner));

            this.whitlistedPlayers.add(UUID.fromString(configSöldner));
        }

        //Create Spectator from Config
        for (String configSpectator : config.getStringList("spectator")) {
            this.spectators.add(UUID.fromString(configSpectator));

            this.whitlistedPlayers.add(UUID.fromString(configSpectator));
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


    //Methods for adding Positions to whitlistedPlayers, clans, söldner, spectators
    public boolean createClan(String clanName) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                return false;
            }
        };
        this.clans.add(new Clan(clanName));

        config.set("clans." + clanName + ".leader", "");
        config.set("clans." + clanName + ".member", new HashSet<>());
        return true;
    }

    public boolean removeClan(String clanName) {

        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                for (UUID uuid:clan.getMembers()) {
                    this.whitlistedPlayers.remove(uuid);
                }

                this.whitlistedPlayers.remove(clan.getLeader());

                this.clans.remove(clan);
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
                this.whitlistedPlayers.add(uuid);

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
                this.whitlistedPlayers.add(uuid);

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
                this.whitlistedPlayers.remove(uuid);

                removeFromConfigFileList("clans." + clanName + ".members", uuid.toString());
                return true;
            }
        };
        return false;
    }

    public boolean addSöldner(UUID uuid) {
        this.söldner.add(uuid);
        this.whitlistedPlayers.add(uuid);
        return addToConfigFileList("söldner", uuid.toString());
    }

    public boolean removeSöldner(UUID uuid) {
        this.söldner.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        return removeFromConfigFileList("söldner", uuid.toString());
    }

    public boolean addSpectator(UUID uuid) {
        this.spectators.add(uuid);
        this.whitlistedPlayers.add(uuid);
        return addToConfigFileList("spectator", uuid.toString());
    }

    public boolean removeSpectator(UUID uuid) {
        this.spectators.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        return removeFromConfigFileList("spectator", uuid.toString());
    }


    //Additional Method for working with Clans
    @Nullable
    public HashSet<UUID> getPlayersInClan(String clanName) {
        for (Clan clan:clans) {
            if (clan.getName() == clanName) {
                HashSet<UUID> hashSet = clan.getMembers();
                hashSet.add(clan.getLeader());

                return hashSet;
            }
        }
        return null;
    }

    @Nullable
    public HashSet<UUID> getPlayersInClan(UUID uuid) {
        for (Clan clan:clans) {
            if (clan.getName() == getClanOfPlayer(uuid)) {
                HashSet<UUID> hashSet = clan.getMembers();
                hashSet.add(clan.getLeader());

                return hashSet;
            }
        }
        return null;
    }

    public PlayerRole getPlayerRole(UUID uuid) {
        for (Clan clan:clans) if (clan.getMembers().contains(uuid) || clan.getLeader() == uuid) return PlayerRole.CLAN;
        if (söldner.contains(uuid)) return PlayerRole.SÖLDNER;
        else if (spectators.contains(uuid)) return PlayerRole.SPECTATOR;
        else return PlayerRole.UNSPECIFIED;
    }

    @Nullable
    public String getClanOfPlayer(UUID uuid) {
        for (Clan clan:clans) if (clan.getMembers().contains(uuid) || clan.getLeader() == uuid) return clan.getName();
        return null;
    }


    //Private Methods for writing in the config File
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

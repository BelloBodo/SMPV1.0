package de.bellobodo.smpv1.manager.playerManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerManager {

    private final SMPV1 smpv1;

    private FileConfiguration config;

    public PlayerManager(SMPV1 smpv1) {
        this.smpv1 = smpv1;
        this.config = smpv1.getConfig();

        Bukkit.getLogger().info("[SMPV1] Start Building PlayerManager");

        //Create Clans from Config
        ConfigurationSection configurationSection = config.getConfigurationSection("clans");
        if (configurationSection != null) {
            for (String clanNames : configurationSection.getKeys(false).toArray(new String[0])) {
                Clan clan = new Clan(clanNames.toUpperCase());

                Bukkit.getLogger().info("Clan hinzugefügt: " + clanNames);

                ConfigurationSection configurationSection1 = config.getConfigurationSection("clans." + clanNames);
                if (configurationSection1 != null) {
                    for (String clanMember : configurationSection1.getKeys(false).toArray(new String[0])) {
                        UUID clanMemberUUID = UUID.fromString(clanMember);
                        clan.addMember(clanMemberUUID);
                        this.playerRoles.put(clanMemberUUID, PlayerRole.CLAN);
                        this.whitlistedPlayers.add(clanMemberUUID);

                        Bukkit.getLogger().info("Clan Member hinzugefügt: " + clanMember);
                    }
                }
                this.clans.add(clan);
            }
        }

        this.clans.forEach(clan -> {
            Bukkit.getLogger().info("ClanName: " + clan.getName());
            for (UUID uuids : clan.getMembers()) {
                Bukkit.getLogger().info("ClanUUID: " + uuids.toString());
            }
        });

        //Create Söldner from Config
        configurationSection = config.getConfigurationSection("söldner");
        if (configurationSection != null) {
            for (String söldner : configurationSection.getKeys(false).toArray(new String[0])) {
                UUID söldnerUUID = UUID.fromString(söldner);
                this.söldner.add(söldnerUUID);
                this.playerRoles.put(söldnerUUID, PlayerRole.SOELDNER);
                this.whitlistedPlayers.add(söldnerUUID);

                Bukkit.getLogger().info("Söldner hinzugefügt: " + söldner);
            }

        }

        //Create Spectator from Config
        configurationSection = config.getConfigurationSection("spectator");
        if (configurationSection != null) {
            for (String spectator : configurationSection.getKeys(false).toArray(new String[0])) {
                UUID spectatorUUID = UUID.fromString(spectator);
                this.spectators.add(spectatorUUID);
                this.playerRoles.put(spectatorUUID, PlayerRole.SPECTATOR);
                this.whitlistedPlayers.add(spectatorUUID);

                Bukkit.getLogger().info("Spectator hinzugefügt: " + spectator);
            }
        }

        Bukkit.getLogger().info("[SMPV1] End Building PlayerManager");

        reloadWhitelist();
    }


    private HashSet<UUID> whitlistedPlayers = new HashSet<>();

    private HashMap<UUID,PlayerRole> playerRoles = new HashMap<>();

    private ArrayList<Clan> clans = new ArrayList<>();

    private HashSet<UUID> söldner = new HashSet<>();

    private HashSet<UUID> spectators = new HashSet<>();

    //Reload the Whitelist
    private void reloadWhitelist() {
        //Clear Whitelisted Players
        for (OfflinePlayer offlinePlayer : Bukkit.getWhitelistedPlayers()) {
            offlinePlayer.setWhitelisted(false);
        }

        //Add new Whitelisted Players
        whitlistedPlayers.forEach(whitlistedPlayers -> {
            Bukkit.getOfflinePlayer(whitlistedPlayers).setWhitelisted(true);
        });

        Bukkit.setWhitelist(smpv1.getConfig().getBoolean("whitelist", true));
        Bukkit.reloadWhitelist();
    }


    //Methods for adding/removing Positions to whitelistedPlayers, clans, söldner, spectators
    public boolean createClan(String clanName) {
        clanName = clanName.toUpperCase();

        for (Clan clan : clans) {
            if (clan.getName().equals(clanName)) {
                return false;
            }
        };
        this.clans.add(new Clan(clanName));
        return true;
    }

    public boolean deleteClan(String clanName) {
        clanName = clanName.toUpperCase();

        for (Clan clan:clans) {
            if (clan.getName().equals(clanName)) {
                for (UUID uuid : clan.getMembers()) {
                    this.whitlistedPlayers.remove(uuid);
                }
                reloadWhitelist();
                this.clans.remove(clan);
                config.set("clans." + clanName, null);
                smpv1.saveConfig();
                return true;
            }
        };
        return false;
    }

    public boolean addClanMember(String clanName,UUID uuid) {
        clanName = clanName.toUpperCase();

        for (Clan clan : clans) {
            if (clan.getName().equals(clanName)) {
                clan.addMember(uuid);
                this.whitlistedPlayers.add(uuid);
                this.playerRoles.put(uuid, PlayerRole.CLAN);
                reloadWhitelist();
                config.set("clans." + clanName + "." + uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName());
                smpv1.saveConfig();
                return true;
            }
        };
        return false;
    }

    public boolean removeClanMember(String clanName,UUID uuid) {
        clanName = clanName.toUpperCase();

        for (Clan clan:clans) {
            if (clan.getName().equals(clanName)) {
                clan.removeMember(uuid);
                this.whitlistedPlayers.remove(uuid);
                this.playerRoles.remove(uuid);
                reloadWhitelist();
                config.set("clans." + clanName + "." + uuid.toString(), null);
                smpv1.saveConfig();
                return true;
            }
        };
        return false;
    }

    public void addSöldner(UUID uuid) {
        this.söldner.add(uuid);
        this.whitlistedPlayers.add(uuid);
        this.playerRoles.put(uuid, PlayerRole.SOELDNER);
        reloadWhitelist();
        config.set("söldner." + uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName());
        smpv1.saveConfig();
    }

    public void removeSöldner(UUID uuid) {
        this.söldner.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        this.playerRoles.remove(uuid);
        reloadWhitelist();
        config.set("söldner." + uuid.toString(), null);
        smpv1.saveConfig();
    }

    public void addSpectator(UUID uuid) {
        this.spectators.add(uuid);
        this.whitlistedPlayers.add(uuid);
        this.playerRoles.put(uuid, PlayerRole.SPECTATOR);
        reloadWhitelist();
        config.set("spectator." + uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName());
        smpv1.saveConfig();
    }

    public void removeSpectator(UUID uuid) {
        this.spectators.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        this.playerRoles.remove(uuid);
        reloadWhitelist();
        config.set("spectator." + uuid.toString(), null);
        smpv1.saveConfig();
    }


    //Additional Method for working with Clans
    @Nullable
    public HashSet<UUID> getPlayersInClan(String clanName) {
        clanName = clanName.toUpperCase();
        for (Clan clan : clans) {
            if (clan.getName().equalsIgnoreCase(clanName)) {
                return clan.getMembers();
            }
        }
        return null;
    }

    @Nullable
    public HashSet<UUID> getPlayersInClan(UUID uuid) {
        for (Clan clan : clans) {
            if (clan.getName().equalsIgnoreCase(getClanOfPlayer(uuid))) {
                return new HashSet<>(clan.getMembers());
            }
        }
        return null;
    }

    @Nullable
    public PlayerRole getPlayerRole(UUID uuid) {
        if (!playerRoles.containsKey(uuid)) return null;
        return playerRoles.get(uuid);
    }

    @Nullable
    public String getClanOfPlayer(UUID uuid) {
        final String[] clanName = {null};
        clans.forEach(clan -> {
            clan.getMembers().forEach(uuids -> {
                if (uuids.toString().equals(uuid.toString())) clanName[0] = clan.getName();
            });
        });
        return clanName[0];
    }

    //Getter

    public ArrayList<Clan> getClans() {
        return clans;
    }

    public ArrayList<UUID> getClanMembers() {
        ArrayList<UUID> clanMembers = new ArrayList<>();

        getClans().forEach(clan -> {
            clanMembers.addAll(clan.getMembers());
        });

        return clanMembers;
    }

    public HashSet<UUID> getSöldner() {
        return söldner;
    }

    public HashSet<UUID> getSpectators() {
        return spectators;
    }
}

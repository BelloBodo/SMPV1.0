package de.bellobodo.smpv1.playerManager;

import de.bellobodo.smpv1.SMPV1;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

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
                    for (String clanMembers : configurationSection1.getKeys(false).toArray(new String[0])) {
                        UUID clanMember = UUID.fromString(clanMembers);
                        clan.addMember(clanMember);
                        this.whitlistedPlayers.add(clanMember);

                        Bukkit.getLogger().info("Clan Member hinzugefügt: " + clanMembers);
                    }
                }
                this.clans.add(clan);
            }
        }

        //Create Söldner from Config
        configurationSection = config.getConfigurationSection("söldner");
        if (configurationSection != null) {
            for (String söldner : configurationSection.getKeys(false).toArray(new String[0])) {
                this.söldner.add(UUID.fromString(söldner));
                this.whitlistedPlayers.add(UUID.fromString(söldner));

                Bukkit.getLogger().info("Söldner hinzugefügt: " + söldner);
            }

        }

        //Create Spectator from Config
        configurationSection = config.getConfigurationSection("spectator");
        if (configurationSection != null) {
            for (String spectator : configurationSection.getKeys(false).toArray(new String[0])) {
                this.spectators.add(UUID.fromString(spectator));
                this.whitlistedPlayers.add(UUID.fromString(spectator));

                Bukkit.getLogger().info("Spectator hinzugefügt: " + spectator);
            }
        }

        Bukkit.getLogger().info("[SMPV1] End Building PlayerManager");

        reloadWhitelist();
    }


    private HashSet<UUID> whitlistedPlayers = new HashSet<>();

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
        reloadWhitelist();
        config.set("söldner." + uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName());
        smpv1.saveConfig();
    }

    public void removeSöldner(UUID uuid) {
        this.söldner.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        reloadWhitelist();
        config.set("söldner." + uuid.toString(), null);
        smpv1.saveConfig();
    }

    public void addSpectator(UUID uuid) {
        this.spectators.add(uuid);
        this.whitlistedPlayers.add(uuid);
        reloadWhitelist();
        config.set("spectator." + uuid.toString(), Bukkit.getOfflinePlayer(uuid).getName());
        smpv1.saveConfig();
    }

    public void removeSpectator(UUID uuid) {
        this.spectators.remove(uuid);
        this.whitlistedPlayers.remove(uuid);
        reloadWhitelist();
        config.set("spectator." + uuid.toString(), null);
        smpv1.saveConfig();
    }


    //Additional Method for working with Clans
    @Nullable
    public HashSet<UUID> getPlayersInClan(String clanName) {
        clanName = clanName.toUpperCase();
        for (Clan clan : clans) {
            if (clan.getName().equals(clanName)) {
                return clan.getMembers();
            }
        }
        return null;
    }

    @Nullable
    public HashSet<UUID> getPlayersInClan(UUID uuid) {
        for (Clan clan : clans) {
            if (clan.getName().equals(getClanOfPlayer(uuid))) {
                return clan.getMembers();
            }
        }
        return null;
    }

    public PlayerRole getPlayerRole(UUID uuid) {
        for (Clan clan : clans) if (clan.getMembers().contains(uuid)) return PlayerRole.CLAN;
        if (söldner.contains(uuid)) return PlayerRole.SÖLDNER;
        else if (spectators.contains(uuid)) return PlayerRole.SPECTATOR;
        else return PlayerRole.UNSPECIFIED;
    }

    @Nullable
    public String getClanOfPlayer(UUID uuid) {
        for (Clan clan : clans) if (clan.getMembers().contains(uuid)) return clan.getName().toUpperCase();
        return null;
    }

    //Getter


    public ArrayList<Clan> getClans() {
        return clans;
    }

    public HashSet<UUID> getSöldner() {
        return söldner;
    }

    public HashSet<UUID> getSpectators() {
        return spectators;
    }
}

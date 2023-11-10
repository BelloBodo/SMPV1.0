package de.bellobodo.smpv1.manager.playerManager;

import java.util.HashSet;
import java.util.UUID;

public class Clan {

    private String name;

    private HashSet<UUID> members = new HashSet<>();


    public Clan(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public HashSet<UUID> getMembers() {
        return new HashSet<>(members);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }


}

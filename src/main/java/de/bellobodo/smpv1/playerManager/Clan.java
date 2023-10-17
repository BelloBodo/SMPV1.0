package de.bellobodo.smpv1.playerManager;

import java.util.HashSet;
import java.util.UUID;

public class Clan {

    private String name;

    private HashSet<UUID> members;


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
        return members;
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }


}

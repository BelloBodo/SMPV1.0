package de.bellobodo.smpv1.manager.playerManager;

import java.util.ArrayList;
import java.util.UUID;

public class Clan {

    private String name;

    private UUID leader;

    private ArrayList<UUID> members;


    public Clan(String name) {
        this.name = name;
    }

    public void setLeader(UUID uuid) {
        leader = uuid;
    }

    public UUID getLeader() {
        return leader;
    }

    public void addMember(UUID uuid) {
        members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        members.remove(uuid);
    }


}

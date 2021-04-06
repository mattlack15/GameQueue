package ca.mattlack.gamequeue.matchserver;

import ca.mattlack.gamequeue.common.SecuredTCPConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ConnectedGame {
    public UUID id;
    public int maxPlayers;
    public int currentPlayers;
    public SecuredTCPConnection connection;
    public boolean usable = true;
    public List<Integer> pendingPlayers = new ArrayList<>();

    public ConnectedGame(UUID id, int maxPlayers, int currentPlayers, SecuredTCPConnection connection) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.currentPlayers = currentPlayers;
        this.connection = connection;
    }
}

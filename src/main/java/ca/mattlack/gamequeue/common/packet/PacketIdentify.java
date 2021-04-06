package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

import java.util.UUID;

public class PacketIdentify extends Packet {

    private String apiKey;
    private UUID serverId;
    private int maxPlayers;
    private int players;

    public PacketIdentify(String apiKey, UUID serverId, int maxPlayers, int players) {
        this.apiKey = apiKey;
        this.serverId = serverId;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }

    public PacketIdentify(GravSerializer serializer) {
        this.apiKey = serializer.readString();
        this.serverId = serializer.readUUID();
        this.maxPlayers = serializer.readInt();
        this.players = serializer.readInt();
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public UUID getServerId() {
        return this.serverId;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getPlayers() {
        return this.players;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeString(apiKey);
        serializer.writeUUID(serverId);
        serializer.writeInt(maxPlayers);
        serializer.writeInt(players);
    }
}

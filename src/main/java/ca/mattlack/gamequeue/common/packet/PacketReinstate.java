package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

public class PacketReinstate extends Packet{
    public int players;
    public int maxPlayers;
    public PacketReinstate(int players, int maxPlayers) {
        this.players = players;
        this.maxPlayers = maxPlayers;
    }

    public PacketReinstate(GravSerializer serializer) {
        this.players = serializer.readInt();
        this.maxPlayers = serializer.readInt();
    }


    @Override
    public int getId() {
        return 4;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeInt(players);
        serializer.writeInt(maxPlayers);
    }
}

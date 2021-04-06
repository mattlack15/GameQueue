package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

public class PacketMessage extends Packet {
    public String message;

    public PacketMessage(String message) {
        this.message = message;
    }

    public PacketMessage(GravSerializer serializer) {
        this.message = serializer.readString();
    }

    @Override
    public int getId() {
        return 2;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeString(message);
    }
}

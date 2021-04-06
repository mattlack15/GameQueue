package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

public class PacketReserveResponse extends Packet {

    public boolean response;

    public PacketReserveResponse(boolean response) {
        this.response = response;
    }

    public PacketReserveResponse(GravSerializer serializer) {
        this.response = serializer.readBoolean();
    }

    @Override
    public int getId() {
        return 5;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeBoolean(response);
    }
}

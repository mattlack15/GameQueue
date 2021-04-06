package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

public class PacketReserveRequest extends Packet {

    public int amount;
    public PacketReserveRequest(int amount) {
        this.amount = amount;
    }

    public PacketReserveRequest(GravSerializer serializer) {
        this.amount = serializer.readInt();
    }

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeInt(amount);
    }
}

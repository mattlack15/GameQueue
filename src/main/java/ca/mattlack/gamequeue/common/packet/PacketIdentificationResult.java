package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

public class PacketIdentificationResult extends Packet {

    public int result;

    public PacketIdentificationResult(int result) {
        this.result = result;
    }

    public PacketIdentificationResult(GravSerializer serializer) {
        this.result = serializer.readInt();
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void serialize(GravSerializer serializer) {
        serializer.writeInt(result);
    }
}

package ca.mattlack.gamequeue.common.packet;

import net.ultragrav.serializer.GravSerializer;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class Packet {

    private static Map<Integer, Class<? extends Packet>> idMap = new HashMap<>();

    static {
        idMap.put(0, PacketIdentify.class);
        idMap.put(1, PacketIdentificationResult.class);
        idMap.put(2, PacketMessage.class);
        idMap.put(3, PacketReserveRequest.class);
        idMap.put(4, PacketReinstate.class);
        idMap.put(5, PacketReserveResponse.class);
    }

    public abstract int getId();

    public abstract void serialize(GravSerializer serializer);

    public static <T extends Packet> T deserialize(GravSerializer serializer, int packetId) {
        Class<? extends Packet> c = idMap.get(packetId);
        try {
            Object o = c.getConstructor(GravSerializer.class).newInstance(serializer);
            return (T) o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}

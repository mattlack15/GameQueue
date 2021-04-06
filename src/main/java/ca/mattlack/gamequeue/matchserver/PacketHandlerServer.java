package ca.mattlack.gamequeue.matchserver;

import ca.mattlack.gamequeue.common.packet.*;

public class PacketHandlerServer {

    private MatchServer server;

    public PacketHandlerServer(MatchServer server) {
        this.server = server;
    }

    public void handle(ConnectedGame connectedGame, Packet packet) {
        if(packet instanceof PacketIdentify) {
            PacketIdentify packetIdentify = (PacketIdentify) packet;
            connectedGame.currentPlayers = packetIdentify.getPlayers();
            connectedGame.maxPlayers = packetIdentify.getMaxPlayers();
        } else if(packet instanceof PacketMessage) {
            System.out.println("Received message from " + connectedGame.id.toString() + ": " + ((PacketMessage)packet).message);
        } else if(packet instanceof PacketReserveResponse) {
            if(!((PacketReserveResponse)packet).response) {
                server.matchMaker.queue.addAll(connectedGame.pendingPlayers);
                connectedGame.usable = true;
            }
            connectedGame.pendingPlayers.clear();
        } else if(packet instanceof PacketReinstate) {
            PacketReinstate packetReinstate = (PacketReinstate) packet;
            connectedGame.currentPlayers = packetReinstate.players;
            connectedGame.maxPlayers = packetReinstate.maxPlayers;
            connectedGame.usable = true;
        }
    }
}

package ca.mattlack.gamequeue.matchserver;

import ca.mattlack.gamequeue.common.SecuredTCPConnection;
import ca.mattlack.gamequeue.common.SecuredTCPServer;
import ca.mattlack.gamequeue.common.packet.PacketIdentificationResult;
import ca.mattlack.gamequeue.common.packet.PacketIdentify;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class MatchServer extends SecuredTCPServer {

    public List<ConnectedGame> connectedGames = Collections.synchronizedList(new ArrayList<>());
    public PacketHandlerServer packetHandler = new PacketHandlerServer(this);
    public MatchMaker matchMaker = new MatchMaker(this);
    public long tick = 0;

    public MatchServer(int port) {
        super(port);
    }

    @Override
    public void start() throws IOException, NoSuchAlgorithmException, NoSuchPaddingException {
        super.start();
        new Thread(this::mainLoop, "Server Main Thread").start();
    }

    private void mainLoop() {
        while (true) {
            tick();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public ConnectedGame getConnectedGame(UUID id) {
        for (ConnectedGame connectedGame : this.connectedGames) {
            if (connectedGame.id.equals(id))
                return connectedGame;
        }
        return null;
    }

    private Random random = new Random(System.currentTimeMillis());

    public void tick() {

        if(tick % 5 == 0) {
            System.out.println();
        }

        for (ConnectedGame connectedGame : this.connectedGames) {
            while (connectedGame.connection.hasNext()) {
                packetHandler.handle(connectedGame, connectedGame.connection.nextPacket());
            }
            if (tick % 5 == 0) {
                System.out.println(connectedGame.id.toString() + ": " + connectedGame.currentPlayers + "/" + connectedGame.maxPlayers);
            }
        }
        matchMaker.tryMatch();

        if (tick % 5 == 0) {
            System.out.println("Queue: " + matchMaker.queue);
        }

        for (int i = 0; i < 10; i++) {
            matchMaker.queue.add(random.nextInt(4) + 1);
        }

        tick++;
    }

    @Override
    public void handleConnection(SecuredTCPConnection connection) {
        PacketIdentify packetIdentify = connection.nextPacket();
        ConnectedGame connectedGame = new ConnectedGame(packetIdentify.getServerId(), packetIdentify.getMaxPlayers(), packetIdentify.getPlayers(), connection);

        PacketIdentificationResult identificationResult = new PacketIdentificationResult(0);
        connectedGame.connection.sendPacket(identificationResult);

        connectedGames.add(connectedGame);
    }
}

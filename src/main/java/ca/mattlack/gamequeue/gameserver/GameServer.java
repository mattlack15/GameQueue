package ca.mattlack.gamequeue.gameserver;

import ca.mattlack.gamequeue.common.SecuredTCPClient;
import ca.mattlack.gamequeue.common.SecuredTCPConnection;
import ca.mattlack.gamequeue.common.packet.*;

import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;
import java.util.UUID;

public class GameServer {
    private int maxPlayers;
    private int players;
    private UUID serverID = UUID.randomUUID();

    private String apiKey = "ONEFIWRPIFBPISOIEJVOASXDL";

    private SecuredTCPConnection connection;

    public GameServer(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void start() {
        try {
            connection = new SecuredTCPClient("127.0.0.1", 14822).getConnection();
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException e) {
            e.printStackTrace();
        }

        //Identify
        PacketIdentify packetIdentify = new PacketIdentify(apiKey, serverID, maxPlayers, players);
        connection.sendPacket(packetIdentify);

        PacketIdentificationResult result = connection.nextPacket();
        System.out.println("Identification result: " + (result.result == 0 ? "SUCCESS" : "REJECTED"));

        boolean reinstate = false;

        Random random = new Random(System.currentTimeMillis());


        while (true) {
            connection.sendPacket(new PacketIdentify(apiKey, serverID, maxPlayers, players));

            if (reinstate) {
                connection.sendPacket(new PacketReinstate(this.players, this.maxPlayers));
                reinstate = false;
            }

            if (players == maxPlayers) {
                players = 0;
            }

            if (connection.hasNext()) {
                Packet packet = connection.nextPacket();
                if (packet instanceof PacketReserveRequest) {
                    int amount = ((PacketReserveRequest) packet).amount;
                    boolean response = this.players + amount <= this.maxPlayers;
                    Packet responsePacket = new PacketReserveResponse(response);
                    connection.sendPacket(responsePacket);

                    if (response) {
                        this.players += amount;
                        reinstate = true;
                    }
                }
            }

            if (random.nextInt(100) < 20 && players - 1 >= 0) {
                if (players + 1 <= this.maxPlayers)
                    players++;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

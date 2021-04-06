package ca.mattlack.gamequeue.matchserver;

import ca.mattlack.gamequeue.common.packet.PacketReserveRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatchMaker {
    public List<Integer> queue = new ArrayList<>();
    private MatchServer server;

    public MatchMaker(MatchServer server) {
        this.server = server;
    }

    public void tryMatch() {
        for (ConnectedGame connectedGame : server.connectedGames) {

            if(!connectedGame.usable)
                continue;

            int target = connectedGame.maxPlayers - connectedGame.currentPlayers;
            int[] arr = new int[queue.size()];
            for (int i = 0; i < queue.size(); i++) {
                arr[i] = queue.get(i);
            }
            int[] result = match(arr, target);

            //Check if there is no match
            if(result == null)
                continue;

            //Remove from queue
            int sum = 0;
            for (int i : result) {
                connectedGame.pendingPlayers.add(i);
                queue.remove((Integer)i);
                sum += i;
            }

            //Send reservation request
            PacketReserveRequest reserveRequest = new PacketReserveRequest(sum);
            connectedGame.connection.sendPacket(reserveRequest);

            //Stop from using this until a Reinstate packet is received or the reserve request is rejected
            connectedGame.usable = false;
        }
    }

    public static int[] match(int[] arr, int target) {
        List<Integer> currentList = new ArrayList<>();
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            currentList.clear();
            int sum = 0;
            for (int j = 0; j < arr.length; j++) {
                int n = arr[arr.length - (i + j) % arr.length - 1];
                if (sum + n == target) {
                    currentList.add(n);
                    int[] out = new int[currentList.size()];
                    for (int i1 = 0; i1 < currentList.size(); i1++) {
                        out[i1] = currentList.get(i1);
                    }
                    return out;
                }
                if (sum + n > target)
                    continue;
                sum += n;
                currentList.add(n);
            }
        }

        int[] out = new int[currentList.size()];
        for (int i1 = 0; i1 < currentList.size(); i1++) {
            out[i1] = currentList.get(i1);
        }

        if(out.length == 0)
            return null;

        return out;
    }
}

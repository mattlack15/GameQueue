package ca.mattlack.gamequeue.matchserver;

import ca.mattlack.gamequeue.gameserver.GameServer;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class MatchTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        CompletableFuture<Void> f = new CompletableFuture<>();
        new Thread(() -> {
            try {
                MatchServer server = new MatchServer(14822);
                server.start();
                f.complete(null);
            } catch (IOException | NoSuchPaddingException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }).start();

        f.join();
        for(int i = 0; i < 8; i++) {
            new Thread(() -> {
                GameServer client = new GameServer(16);
                client.start();
            }).start();
        }
    }

    public static boolean hasSum(int[] arr, int target) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = 0; j < arr.length; j++) {
                int n = arr[arr.length - (i + j) % arr.length - 1];
                if (sum + n == target)
                    return true;
                if (sum + n > target)
                    continue;
                sum += n;
            }
        }
        return false;
    }
}

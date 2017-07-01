package tools;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * Created by Michał on 2017-06-21.
 */
public class Hash {

    public static void main(String[] args) {

        String hashToCheck = helpfulHashMethod("Al@makota2", 1000, true);

        System.out.println(hashToCheck);
    }

    public static String helpfulHashMethod(String toHash, int howMany, boolean isSalted) {

        String salted;

        if (isSalted)
            salted = "^*)" + toHash + "%h&";
        else
            salted = toHash;

        String hashToCheck = Hashing.sha256().hashString(salted, StandardCharsets.UTF_8).toString();

        for (int i = 0; i < howMany; i++) {

            hashToCheck = Hashing.sha256().hashString(hashToCheck, StandardCharsets.UTF_8).toString();
        }

        return hashToCheck;
    }
}

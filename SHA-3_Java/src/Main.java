import Core.KECCAK;
import IO.terminalInput;
import Util.HexUtil;
import Util.Padding;
import Util.StateConverter;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

public class Main {

    public static void main(String[] args) {
        final String PLAIN = terminalInput.inputVIATermial();
//        final String PLAIN = "abc";
        byte[] message = PLAIN.getBytes(StandardCharsets.UTF_8);

        // --- 1. 패딩 ---
        int rateBytes = 136; // SHA3-256의 rate
        byte[] paddedMessage = Padding.applyPadding(message, rateBytes);

        // --- 2. 흡수 (Absorbing) ---
        long[] state = new long[25];
        byte[] block = Arrays.copyOfRange(paddedMessage, 0, rateBytes);
        ByteBuffer buffer = ByteBuffer.wrap(block).order(ByteOrder.LITTLE_ENDIAN);
        for (int j = 0; j < rateBytes / 8; j++) {
            state[j] ^= buffer.getLong();
        }

        // --- 3. 순열 (Permutation) ---
        KECCAK.KECCAK_f(state);

        // --- 4. 압착 (Squeezing) ---
        byte[] finalStateBytes = StateConverter.stateToBytes(state);

        // SHA3-256은 32바이트(256비트)를 결과로 사용
        int outputLengthBytes = 32;
        byte[] finalHash = new byte[outputLengthBytes];
        System.arraycopy(finalStateBytes, 0, finalHash, 0, outputLengthBytes);

        System.out.println("--- Final SHA3-256 Hash ---");
        System.out.println(HexUtil.toHex(finalHash));

//        System.out.println("\nCompare with the official test vector for an empty string:");
//        System.out.println("a7ffc6f8bf1ed76651c14756a061d662f580ff4de43b49fa82d80a4b80f8434a");
        
        try {
            System.out.println(shaAndHex(PLAIN, "SHA3-256"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
    }
    
    private static String shaAndHex(String plainText, String Algorithms) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Algorithms);
        md.update(plainText.getBytes(StandardCharsets.UTF_8));
        return Hex.encodeHexString(md.digest());
    }
}


package Core;

public class StepMappings {

    private static final int[] RHO_OFFSETS = {
         0,  1, 62, 28, 27,
        36, 44,  6, 55, 20,
         3, 10, 43, 25, 39,
        41, 45, 15, 21,  8,
        18,  2, 61, 56, 14
    };

    private static final long[] RC = {
        0x0000000000000001L, 0x0000000000008082L, 0x800000000000808AL,
        0x8000000080008000L, 0x000000000000808BL, 0x0000000080000001L,
        0x8000000080008081L, 0x8000000000008009L, 0x000000000000008AL,
        0x0000000000000088L, 0x0000000080008009L, 0x000000008000000AL,
        0x000000008000808BL, 0x800000000000008BL, 0x8000000000008089L,
        0x8000000000008003L, 0x8000000000008002L, 0x8000000000000080L,
        0x000000000000800AL, 0x800000008000000AL, 0x8000000080008081L,
        0x8000000000008080L, 0x0000000080000001L, 0x8000000080008008L
    };

    public static void theta(long[] state) {
        long[] C = new long[5];
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                C[x] ^= state[x + 5 * y];
            }
        }

        long[] D = new long[5];
        for (int x = 0; x < 5; x++) {
            D[x] = C[(x + 4) % 5] ^ Long.rotateLeft(C[(x + 1) % 5], 1);
        }

        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                state[x + 5 * y] ^= D[x];
            }
        }
    }

    public static void rho(long[] state) {
        for (int i = 1; i < 25; i++) {
            state[i] = Long.rotateLeft(state[i], RHO_OFFSETS[i]);
        }
    }

    public static void pi(long[] state) {
        long[] tempState = new long[25];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                int newX = y;
                int newY = (2 * x + 3 * y) % 5;
                tempState[newY * 5 + newX] = state[y * 5 + x];
            }
        }
        System.arraycopy(tempState, 0, state, 0, 25);
    }

    public static void chi(long[] state) {
        for (int y = 0; y < 5; y++) {
            long[] tempRow = new long[5];
            for (int x = 0; x < 5; x++) {
                tempRow[x] = state[y * 5 + x];
            }
            for (int x = 0; x < 5; x++) {
                state[y * 5 + x] = tempRow[x] ^ ((~tempRow[(x + 1) % 5]) & tempRow[(x + 2) % 5]);
            }
        }
    }

    public static void iota(long[] state, int roundIndex) {
        state[0] ^= RC[roundIndex];
    }
}

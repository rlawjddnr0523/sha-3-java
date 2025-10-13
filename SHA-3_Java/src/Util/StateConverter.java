package Util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class StateConverter {

    /**
     * byte[] 배열을 long[] 배열 (Keccak state)로 변환합니다.
     * Keccak 표준에 따라 Little-Endian 순서로 처리합니다.
     * @param bytes 200바이트 크기의 배열
     * @return 25개의 원소를 갖는 long 배열
     */
    public static long[] bytesToState(byte[] bytes) {
        if (bytes.length != 200) {
            throw new IllegalArgumentException("Byte array must be 200 bytes long for a full state conversion.");
        }
        long[] state = new long[25];
        ByteBuffer buffer = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
        for (int i = 0; i < 25; i++) {
            state[i] = buffer.getLong();
        }
        return state;
    }

    /**
     * long[] 배열 (Keccak state)을 byte[] 배열로 변환합니다.
     * Keccak 표준에 따라 Little-Endian 순서로 처리합니다.
     * @param state 25개의 원소를 갖는 long 배열
     * @return 200바이트 크기의 배열
     */
    public static byte[] stateToBytes(long[] state) {
        if (state.length != 25) {
            throw new IllegalArgumentException("Long array must have 25 elements for a full state conversion.");
        }
        ByteBuffer buffer = ByteBuffer.allocate(200).order(ByteOrder.LITTLE_ENDIAN);
        for (long lane : state) {
            buffer.putLong(lane);
        }
        return buffer.array();
    }
}
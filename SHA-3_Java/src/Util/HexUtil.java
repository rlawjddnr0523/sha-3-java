package Util;

public class HexUtil {
    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();
    
    public static String toHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF; // byte → unsigned
            hexChars[i * 2]     = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }
    
    public static String toHexWithSpaces(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF; // byte → unsigned 변환
            sb.append(HEX_ARRAY[v >>> 4]); // 상위 4비트
            sb.append(HEX_ARRAY[v & 0x0F]); // 하위 4비트
            if (i < bytes.length - 1) {
                sb.append(" "); // 마지막 원소 뒤에는 공백 안 붙임
            }
        }
        return sb.toString();
    }
    
    public static String stateToHexWithSpaces(long[] state, int rateBytes) {
        StringBuilder sb = new StringBuilder();
        int offset = 0;
        
        for (int i = 0; i < rateBytes / 8; i++) {
            long lane = state[i];
            for (int j = 0; j < 8; j++) { // lane을 바이트 단위로 쪼개기
                byte b = (byte) ((lane >>> (8 * j)) & 0xFF);
                sb.append(String.format("%02x", b));
                offset++;
                if (offset < rateBytes) sb.append(" "); // 마지막 바이트 뒤에는 공백 없음
            }
        }
        
        // 남은 바이트 처리 (rateBytes가 8의 배수가 아닌 경우)
        int remaining = rateBytes % 8;
        if (remaining > 0) {
            long lane = state[rateBytes / 8];
            for (int j = 0; j < remaining; j++) {
                byte b = (byte) ((lane >>> (8 * j)) & 0xFF);
                sb.append(String.format("%02x", b));
                offset++;
                if (offset < rateBytes) sb.append(" ");
            }
        }
        
        return sb.toString();
    }
}

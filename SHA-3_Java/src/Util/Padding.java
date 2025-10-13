package Util;

public class Padding {

    /**
     * SHA-3 표준에 따라 패딩을 적용합니다 (M || 01 || 10*1).
     * 메시지를 rateBytes의 배수 길이로 만듭니다.
     * @param input 원본 메시지 바이트 배열
     * @param rateBytes 블록 크기 (rate in bytes). e.g., SHA3-256의 경우 136.
     * @return 패딩이 적용된 바이트 배열
     */
    public static byte[] applyPadding(byte[] input, int rateBytes) {
        // 패딩 후 전체 길이는 rateBytes의 배수여야 함
        int totalLength = input.length + 1; // 최소 1바이트(0x06)는 추가됨
        int q = totalLength / rateBytes + 1;
        if (totalLength % rateBytes == 0) {
            q -= 1;
        }
        
        byte[] padded = new byte[q * rateBytes];
        
        // 1. 원본 메시지 복사
        System.arraycopy(input, 0, padded, 0, input.length);
        
        // 2. SHA-3의 구분자(01)와 10*1 패딩의 첫 1을 합친 0x06을 추가
        padded[input.length] = 0x06;
        
        // 3. 마지막 바이트에 0x80 (10000000)을 OR 연산하여 패딩의 끝을 표시
        padded[padded.length - 1] |= (byte)0x80;
        
        return padded;
    }
}

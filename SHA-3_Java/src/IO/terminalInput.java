package IO;

import java.util.Scanner;

public class terminalInput {
    public static String inputVIATermial() {
        System.out.print("암호화할 문장을 입력해주세요: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

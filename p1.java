import java.util.*;

class P1 {

    static String gen = "10001000000100001";

    static String divide(String data) {
        char[] a = data.toCharArray();

        for (int i = 0; i <= a.length - 17; i++) {
            if (a[i] == '1') {
                for (int j = 0; j < 17; j++) {
                    a[i + j] = (a[i + j] == gen.charAt(j)) ? '0' : '1';
                }
            }
        }

        return new String(a, a.length - 16, 16);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter binary data: ");
        String data = sc.next();

        String crc = divide(data + "0000000000000000");
        String code = data + crc;

        System.out.println("CRC: " + crc);
        System.out.println("Transmitted codeword: " + code);

        System.out.print("Enter received codeword: ");
        String received = sc.next();

        String rem = divide(received);

        System.out.println("Remainder: " + rem);

        if (rem.equals("0000000000000000"))
            System.out.println("No error detected");
        else
            System.out.println("Error detected");

        sc.close();
    }
}

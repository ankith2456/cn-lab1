// =============== PART 1: SENDER ===============

import java.io.*;
import java.net.*;

class Slide_Sender {

    public static void main(String[] args) throws Exception {

        Socket s = new Socket("localhost", 1500);

        DataOutputStream out =
            new DataOutputStream(s.getOutputStream());

        DataInputStream in =
            new DataInputStream(s.getInputStream());

        int base = 1, next = 1, total = 9, window = 3;

        s.setSoTimeout(3000);

        while (base <= total) {

            while (next < base + window && next <= total) {

                out.writeInt(next);
                out.writeUTF("Video_Data_" + next);
                out.flush();

                System.out.println("Frame " + next + " sent");
                next++;
            }

            try {
                int ack = in.readInt();

                System.out.println("ACK " + ack + " received");

                if (ack >= base)
                    base = ack + 1;

            } catch (SocketTimeoutException e) {

                System.out.println("Timeout! Retransmitting...");
                next = base;
            }
        }

        out.writeInt(-1);
        System.out.println("All frames sent.");

        s.close();
    }
}


// =============== PART 2: RECEIVER ===============

class Slide_Receiver {

    public static void main(String[] args) throws Exception {

        ServerSocket ss = new ServerSocket(1500);

        Socket s = ss.accept();

        DataInputStream in =
            new DataInputStream(s.getInputStream());

        DataOutputStream out =
            new DataOutputStream(s.getOutputStream());

        int expected = 1;
        boolean lost = false;

        while (true) {

            int frame = in.readInt();

            if (frame == -1)
                break;

            String data = in.readUTF();

            if (frame == 4 && !lost) {

                System.out.println("Frame 4 lost");
                lost = true;
                continue;
            }

            if (frame == expected) {

                System.out.println("Received: " + data);
                expected++;

            } else {

                System.out.println("Frame " + frame + " discarded");
            }

            out.writeInt(expected - 1);
            out.flush();

            System.out.println("ACK " + (expected - 1) + " sent");
        }

        System.out.println("All frames received.");

        s.close();
        ss.close();
    }
}

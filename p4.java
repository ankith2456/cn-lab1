import java.net.*;
import java.util.*;

// =============== PART 1: WEATHER SERVER ===============

class WeatherServer {

    public static void main(String[] args) {

        try (DatagramSocket s = new DatagramSocket();
             Scanner sc = new Scanner(System.in)) {

            s.setBroadcast(true);

            InetAddress addr =
                InetAddress.getByName("255.255.255.255");

            System.out.println("Weather server started");

            while (true) {

                System.out.print("Enter alert: ");
                String msg = sc.nextLine();

                if (msg.equalsIgnoreCase("exit"))
                    break;

                byte[] b = msg.getBytes();

                DatagramPacket p =
                    new DatagramPacket(b, b.length, addr, 5000);

                s.send(p);

                System.out.println("Alert sent");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}


// =============== PART 2: WEATHER STATION ===============

class WeatherStation {

    public static void main(String[] args) {

        try (DatagramSocket s = new DatagramSocket(5000)) {

            byte[] b = new byte[1024];

            System.out.println("Waiting for alerts...");

            while (true) {

                DatagramPacket p =
                    new DatagramPacket(b, b.length);

                s.receive(p);

                String msg =
                    new String(p.getData(), 0, p.getLength());

                System.out.println("Weather Alert: " + msg);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

package com.example.intellicoach;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class UdpReceiver extends Thread {
    private static final String TAG = "UdpReceiver";
    private static final int BUFFER_SIZE = 1024;

    private DatagramSocket socket;
    private OnMessageReceivedListener listener;
    private boolean isRunning;

    public UdpReceiver(OnMessageReceivedListener listener) {
        this.listener = listener;
    }

    public void startReceiver(String serverIp, int serverPort) {
        try {
            socket = new DatagramSocket(serverPort, InetAddress.getByName(serverIp));
            isRunning = true;
            start();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void stopReceiver() {
        isRunning = false;
        if (socket != null) {
            socket.close();
        }
    }

    @Override
    public void run() {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
        while (isRunning) {
            try {
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8);
                listener.onMessageReceived(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnMessageReceivedListener {
        void onMessageReceived(String message);
    }
}

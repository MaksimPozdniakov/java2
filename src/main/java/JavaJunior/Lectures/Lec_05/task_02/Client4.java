package main.java.JavaJunior.Lectures.Lec_05.task_02;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client4 {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String name;

    public Client4(Socket socket, String userName) {
        this.socket = socket;
        this.name = userName;
        try {
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(name);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String message = scanner.nextLine();
                bufferedWriter.write(name + ":" + message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenerForMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFormGroup;
                while (socket.isConnected()) {
                    try {
                        messageFormGroup = bufferedReader.readLine();
                        System.out.println(messageFormGroup);
                    } catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }

    private void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }

            if (bufferedWriter != null) {
                bufferedWriter.close();
            }

            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите свое имя: ");
        String name = scanner.nextLine();
        Socket socket = new Socket("localhost", 1300);
        Client4 client = new Client4(socket, name);
        client.listenerForMessage();
        client.sendMessage();
    }
}

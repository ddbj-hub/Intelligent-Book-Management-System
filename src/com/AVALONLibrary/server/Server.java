package com.AVALONLibrary.server;

import com.AVALONLibrary.model.Book;
import com.AVALONLibrary.util.FileUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<Book> books = new ArrayList<>();
    static volatile boolean running = true;

    static void main(String[] args) throws IOException {
        books = FileUtil.loadBooksFromFile("D:\\JavaProject\\IntelligentBookManagementSystem\\src\\Data\\books.txt");


        //后台时钟显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running){
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }

                    System.out.println("系统时间" + new java.util.Date());
                }
            }
        }).start();

        //后台自动保存文件
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        break;
                    }

                    try {
                        FileUtil.saveBooksToFile(books,"D:\\JavaProject\\IntelligentBookManagementSystem\\src\\Data\\books.txt");
                    } catch (IOException e) {
                        System.out.println("保存失败" + e.getMessage());
                    }
                }
            }
        }).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                FileUtil.saveBooksToFile(books, "D:\\JavaProject\\IntelligentBookManagementSystem\\src\\Data\\books.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));

        ServerSocket ss = new ServerSocket(10086);
        Socket socket;
        while (true){
            socket = ss.accept();

            new Thread(new ClientHandler(socket,books)).start();
        }
    }
}

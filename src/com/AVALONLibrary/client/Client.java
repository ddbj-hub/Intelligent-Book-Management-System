package com.AVALONLibrary.client;

import com.AVALONLibrary.util.FileUtil;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static com.AVALONLibrary.server.Server.books;

public class Client {
    static Scanner sc = new Scanner(System.in);

    static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1",10086);

        PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"),true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                    String line;
                    while ((line = br.readLine()) != null){
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while (true) {
            System.out.println("===== 智能图书管理系统 V6 =====\n" +
                    "1. 退出系统\n" +
                    "2. 显示所有图书\n" +
                    "3. 添加图书\n" +
                    "4. 删除图书\n" +
                    "5. 修改图书\n" +
                    "6. 查询图书\n" +
                    "7. 借阅图书\n" +
                    "8. 归还图书\n" +
                    "9. 执行远程命令\n" +
                    "请输入选择:");

            int selection = sc.nextInt();

            switch (selection){
                case 1->{
                    System.out.println("感谢使用，再见！");
                    sc.close();

                    pw.println("EXIT");
                    socket.close();
                    return;
                }
                case 2-> {
                    pw.println("LIST");
                }
                case 3->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要添加图书的书名：");
                    String bookname = sc.nextLine();
                    System.out.println("请输入作者：");
                    String author = sc.nextLine();
                    System.out.println("请输入ISBN:");
                    String isbn = sc.nextLine();

                    pw.println("ADD" + "|" + bookname + "|" + author + "|" + isbn);
                }
                case 4->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要删除图书的isbn:");
                    String isbn = sc.nextLine();

                    pw.println("REMOVE" + "|" + isbn);
                }
                case 5 ->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要修改图书的isbn：");
                    String isbn = sc.nextLine();

                    System.out.println("请输入新书名：");
                    String newBookName = sc.nextLine();
                    System.out.println("请输入新作者：");
                    String newAuthor = sc.nextLine();
                    System.out.println("请输入新ISBN：");
                    String newISBN = sc.nextLine();

                    pw.println("REVISE" + "|" + isbn + "|" + newBookName + "|" + newAuthor + "|" + newISBN);
                }
                case 6->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要查询图书的关键字(书名或作者)：");
                    String keywords = sc.nextLine();

                    pw.println("QUERY" + "|" + keywords);
                }
                case 7-> {
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要借阅图书的ISBN：");
                    String isbn = sc.nextLine();

                    pw.println("BORROW" + "|" + isbn);
                }
                case 8->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入要归还图书的ISBN：");
                    String isbn = sc.nextLine();

                    pw.println("RETURN" + "|" + isbn);
                }
                case 9->{
                    sc.nextLine();//读取残留的换行符

                    System.out.println("请输入类名：");
                    String classname = sc.nextLine();
                    System.out.println("请输入方法名：");
                    String methodname = sc.nextLine();

                    pw.println("REFLECT" + "|" + classname + "|" + methodname);
                }
                default -> {
                    System.out.println("无效选项");
                    break;
                }
            }
        }
    }
}

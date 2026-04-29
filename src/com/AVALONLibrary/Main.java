//这个文件忘记删了
package com.AVALONLibrary;

import com.AVALONLibrary.model.Book;
import com.AVALONLibrary.util.FileUtil;

import java.io.IOException;
import java.lang.constant.ClassDesc;
import java.util.ArrayList;
import java.util.Scanner;

import static com.AVALONLibrary.server.Server.books;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        while (true) {
            System.out.println("===== 智能图书管理系统 V4 =====\n" +
                    "1. 退出系统\n" +
                    "2. 显示所有图书\n" +
                    "3. 添加图书\n" +
                    "4. 删除图书\n" +
                    "5. 修改图书\n" +
                    "6. 查询图书\n" +
                    "7. 借阅图书\n" +
                    "8. 归还图书\n" +
                    "请输入选择:");

            int selection = sc.nextInt();

            switch (selection){
                case 1->{
                    System.out.println("感谢使用，再见！");
                    sc.close();

                    try {
                        FileUtil.saveBooksToFile(books,"D:\\JavaProject\\IntelligentBookManagementSystem\\src\\Data\\books.txt");
                    } catch (IOException e) {
                        System.out.println("文件保存失败" + e.getMessage());
                    }

                    System.exit(0);
                }
                case 2-> {
                    listBooks();
                }
                case 3->{
                    addBook();
                }
                case 4->{
                    removeBook();
                }
                case 5 ->{
                    reviseBook();
                }
                case 6->{
                    queryBook();
                }
                case 7->{
                    borrowBook();
                }
                case 8->{
                    returnBook();
                }
                default -> {
                    System.out.println("无效选项");
                    break;
                }
            }
        }
    }

    public static void listBooks(){
        synchronized (books) {
            if (books.isEmpty()){
                System.out.println("暂无图书！");
                return ;
            }

            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    public static void addBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要添加图书的书名：");
            String bookname = sc.nextLine();
            System.out.println("请输入作者：");
            String author = sc.nextLine();
            System.out.println("请输入ISBN:");
            String isbn = sc.nextLine();

            Book book = new Book(bookname,author,isbn);
            books.add(book);
        }
    }

    public static void removeBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要删除图书的isbn:");
            String isbn = sc.nextLine();

            int removeId = -1;
            for (int i = 0; i < books.size(); i++) {
                if (books.get(i).getIsbn().equals(isbn)){
                    removeId = i;
                    break;
                }
            }

            if (removeId != -1){
                books.remove(removeId);
                System.out.println("删除成功！");
            } else {
                System.out.println("未找到该图书，删除失败");
            }
        }
    }

    public static void reviseBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要修改图书的isbn：");
            String isbn = sc.nextLine();

            for (Book book : books) {
                if (book.getIsbn().equals(isbn)){
                    System.out.println("请输入新书名：");
                    String newBookName = sc.nextLine();
                    System.out.println("请输入新作者：");
                    String newAuthor = sc.nextLine();
                    System.out.println("请输入新ISBN：");
                    String newISBN = sc.nextLine();

                    if (!newBookName.isEmpty()){
                        book.setTitle(newBookName);
                    }
                    if (!newAuthor.isEmpty()){
                        book.setAuthor(newAuthor);
                    }
                    if (!newISBN.isEmpty()){
                        book.setIsbn(newISBN);
                    }

                    break;
                }
            }
        }
    }

    public static void queryBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要查询图书的关键字(书名或作者)：");
            String keywords = sc.nextLine();

            for (Book book : books) {
                if (book.getAuthor().contains(keywords) || book.getAuthor().contains(keywords) || book.getIsbn().contains(keywords)){
                    System.out.println(book.toString());
                    return;
                }
            }

            System.out.println("未查询到该图书！");
        }
    }

    public static void borrowBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要借阅图书的ISBN：");
            String isbn = sc.nextLine();

            for (Book book : books) {
                if (book.getIsbn().equals(isbn)){
                    if (book.isBorrowed()){
                        System.out.println("借阅失败，该图书已借出！");
                    } else {
                        book.setBorrowed(true);
                        System.out.println("借阅成功！");
                    }
                    return;
                }
            }

            System.out.println("未找到该图书！");
        }
    }

    public static void returnBook(){
        synchronized (books) {
            sc.nextLine();//读取残留的换行符

            System.out.println("请输入要归还图书的ISBN：");
            String isbn = sc.nextLine();

            for (Book book : books) {
                if (book.getIsbn().equals(isbn)){
                    if (book.isBorrowed()){
                        book.setBorrowed(false);
                        System.out.println("归还成功！");
                    } else {
                        System.out.println("归还失败！");
                    }
                    return;
                }
            }

            System.out.println("未找到该图书！");
        }
    }
}

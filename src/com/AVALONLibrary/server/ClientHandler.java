package com.AVALONLibrary.server;

import com.AVALONLibrary.admin.AdminUtil;
import com.AVALONLibrary.model.Book;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
    private Socket socket;
    private ArrayList<Book> books;

    public ClientHandler(Socket socket, ArrayList<Book> books) {
        this.socket = socket;
        this.books = books;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
             PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        ) {
            String line;
            while ((line = br.readLine()) != null){
                System.out.println("收到请求" + line);

                String []operators = line.split("\\|");

                switch (operators[0]){
                    case "EXIT"->{
                        synchronized (books) {
                            pw.println("感谢使用，再见！");
                            return;
                        }
                    }
                    case "LIST"->{
                        synchronized (books) {
                            if (books.isEmpty()) {
                                pw.println("OK|暂无图书");
                                return;
                            }

                            StringBuilder sb = new StringBuilder("OK|");
                            for (int i = 0; i < books.size(); i++) {
                                sb.append(books.get(i).toString());
                                if (i != books.size() - 1) sb.append(";;");
                            }

                            pw.println(sb.toString());
                        }
                    }
                    case "ADD"->{
                        synchronized (books) {
                            if (operators.length < 4){
                                pw.println("ERROR|参数不足");
                            } else {
                                Book book = new Book(operators[1],operators[2],operators[3]);
                                books.add(book);
                                pw.println("OK|添加成功");
                            }
                        }
                    }
                    case "REMOVE"->{
                        synchronized (books) {
                            if (operators.length < 2){
                                pw.println("ERROR|参数不足");
                            } else {
                                int id = -1;
                                for (int i = 0; i < books.size(); i++) {
                                    if (books.get(i).getIsbn().equals(operators[1])){
                                        id = i;
                                        break;
                                    }
                                }

                                if (id == -1){
                                    pw.println("ERROR|未找到该图书");
                                } else {
                                    books.remove(id);
                                    pw.println("OK|删除成功");
                                }
                            }
                        }
                    }
                    case "REVISE"->{
                        synchronized (books) {
                            if (operators.length < 5){
                                pw.println("ERROR|参数不足");
                            } else {
                                int id = -1;
                                for (int i = 0; i < books.size(); i++) {
                                    if (books.get(i).getIsbn().equals(operators[1])){
                                        id = i;
                                        break;
                                    }
                                }

                                if (id == -1){
                                    pw.println("ERROR|未找到该图书");
                                } else {
                                    if (!operators[2].isEmpty()) books.get(id).setTitle(operators[2]);
                                    if (!operators[3].isEmpty()) books.get(id).setAuthor(operators[3]);
                                    if (!operators[4].isEmpty()) books.get(id).setIsbn(operators[4]);

                                    pw.println("OK|修改成功");
                                }
                            }
                        }
                    }
                    case "QUERY"->{
                        synchronized (books) {
                            if (operators.length < 2){
                                pw.println("ERROR|参数不足");
                            } else {
                                StringBuilder information = new StringBuilder("");//查询到的信息
                                for (int i = 0; i < books.size(); i++) {
                                    if (books.get(i).getTitle().contains(operators[1]) || books.get(i).getAuthor().contains(operators[1]) || books.get(i).getIsbn().contains(operators[1])){
                                        information.append(books.get(i).toString() + ";;");
                                    }
                                }

                                if (information.length() > 0 && information.toString().endsWith(";;")) {
                                    information.delete(information.length() - 2, information.length());
                                }

                                if (information.isEmpty()){
                                    pw.println("ERROR|未查询到相关图书");
                                } else{
                                    pw.println("OK|" + information.toString());
                                }
                            }
                        }
                    }
                    case "BORROW"->{
                        synchronized (books) {
                            if (operators.length < 2){
                                pw.println("ERROR|参数不足");
                            } else {
                                int id = -1;
                                for (int i = 0; i < books.size(); i++) {
                                    if (books.get(i).getIsbn().equals(operators[1])){
                                        id = i;
                                        break;
                                    }
                                }

                                if (id == -1){
                                    pw.println("ERROR|未找到该图书");
                                } else {
                                    if (books.get(id).isBorrowed()){
                                        pw.println("ERROR|该图书已被借出");
                                    } else {
                                        books.get(id).setBorrowed(true);
                                        pw.println("OK|借阅成功");
                                    }
                                }
                            }
                        }
                    }
                    case "RETURN"->{
                        synchronized (books) {
                            if (operators.length < 2){
                                pw.println("ERROR|参数不足");
                            } else {
                                int id = -1;
                                for (int i = 0; i < books.size(); i++) {
                                    if (books.get(i).getIsbn().equals(operators[1])){
                                        id = i;
                                        break;
                                    }
                                }

                                if (id == -1){
                                    pw.println("ERROR|未找到该图书");
                                } else {
                                    if (books.get(id).isBorrowed()){
                                        books.get(id).setBorrowed(false);
                                        pw.println("OK|归还成功");
                                    } else {
                                        pw.println("ERROR|该图书未被借出");
                                    }
                                }
                            }
                        }
                    }
                    case "REFLECT" -> {
                        if (operators.length < 3) {
                            pw.println("ERROR|参数不足，格式：REFLECT|类名|方法名");
                        } else if (!AdminUtil.isAdmin) {
                            pw.println("ERROR|权限不足");
                        } else {
                            try {
                                String fullClassName = "com.AVALONLibrary.admin." + operators[1];
                                Class<?> clazz = Class.forName(fullClassName);
                                Method method = clazz.getDeclaredMethod(operators[2]);
                                Object instance = clazz.getDeclaredConstructor().newInstance();
                                method.invoke(instance);
                                pw.println("OK|执行成功");
                            } catch (ClassNotFoundException e) {
                                pw.println("ERROR|类不存在: " + operators[1]);
                            } catch (NoSuchMethodException e) {
                                pw.println("ERROR|方法不存在: " + operators[2]);
                            } catch (Exception e) {
                                pw.println("ERROR|执行失败: " + e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

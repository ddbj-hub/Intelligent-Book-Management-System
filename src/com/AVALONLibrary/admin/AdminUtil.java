package com.AVALONLibrary.admin;

public class AdminUtil {
    public static boolean isAdmin = true;

    public void deleteAllBooks() {
        System.out.println("[AdminUtil] 所有图书已清空！");
    }

    public void printSystemLog() {
        System.out.println("[AdminUtil] 系统日志已打印！");
    }
}
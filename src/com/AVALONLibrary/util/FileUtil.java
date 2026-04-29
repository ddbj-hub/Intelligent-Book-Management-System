package com.AVALONLibrary.util;

import com.AVALONLibrary.model.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileUtil {
    public static void saveBooksToFile(ArrayList<Book> books, String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // 确保目录存在
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            for (Book book : books) {
                String line = book.getTitle() + " | " +
                        book.getAuthor() + " | " +
                        book.getIsbn() + " | " +
                        book.isBorrowed();
                bw.write(line);
                bw.newLine();  // 关键：写入换行符
            }
        } // try-with-resources 自动 close()
    }

     public static ArrayList<Book> loadBooksFromFile(String filePath) throws IOException {
         ArrayList<Book> books = new ArrayList<>();

         File file = new File(filePath);
         if (!file.exists()){
            return books;
         }

         try (BufferedReader br = new BufferedReader(new FileReader(file))) {
             String line;
             while ((line = br.readLine()) != null) {
                 String []parts = line.split("\\|");
                 if (parts.length < 4) continue;
                 Book book = new Book(parts[0].trim(),parts[1].trim(),parts[2].trim());
                 if (parts[3].trim().equals("true")){
                     book.setBorrowed(true);
                 } else {
                     book.setBorrowed(false);
                 }

                 books.add(book);
             }
         }

         return books;
     }
}

package com.wei.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int bookId;
    private int borrowByBookId;
    private String bookName;
    private String bookAuthor;
    private String bookType;
    private String bookISBN;
    private String bookPrice;
    private String bookBeginNumber;
    private String bookNowNumber;


    public Book(String bookName, String bookAuthor, String bookType, String bookISBN, String bookPrice, String bookBeginNumber, String bookNowNumber) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookISBN = bookISBN;
        this.bookPrice = bookPrice;
        this.bookBeginNumber = bookBeginNumber;
        this.bookNowNumber = bookNowNumber;
    }

    public Book(int bookId, String bookName, String bookAuthor, String bookType, String bookISBN, String bookPrice, String bookBeginNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookISBN = bookISBN;
        this.bookPrice = bookPrice;
        this.bookBeginNumber = bookBeginNumber;
    }

    public Book(int bookId, String bookName, String bookAuthor, String bookType, String bookISBN, String bookPrice, String bookBeginNumber, String bookNowNumber) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookType = bookType;
        this.bookISBN = bookISBN;
        this.bookPrice = bookPrice;
        this.bookBeginNumber = bookBeginNumber;
        this.bookNowNumber = bookNowNumber;
    }
}


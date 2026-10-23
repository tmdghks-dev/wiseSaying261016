package com.back;

public class WiseSaying {
    // 필드 3개 (private): int id, String content, String author
    private int id;
    private String content;
    private String author;

    // 생성자: (int id, String content, String author) 받아서 this.필드 = 매개변수 3줄
    public WiseSaying(int id,String content, String author ) {
        this.content = content;
        this.author = author;
        this.id = id;
    }
    // getter 3개: getId, getContent, getAuthor → return 필드;
    public String getContent() {
        return content;
    }
    public String getAuthor() {
        return author;
    }
    public int getId() {
        return id;
    }
    // setter 2개: setContent, setAuthor → this.필드 = 매개변수;
    public void setContent(String content){
        this.content = content;
    }
    public void setAuthor(String author){
        this.author = author;
    }
    // (setId는 안 만듦. 번호는 안 바뀌니까)
}
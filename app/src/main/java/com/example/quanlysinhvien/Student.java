package com.example.quanlysinhvien;

public class Student {
    int codeStudent;
    int codeClass;
    String nameStudent;
    String nameClass;
    byte img[];

    public Student(int codeStudent, int codeClass, String nameStudent, byte[] img, String nameClass) {
        this.codeStudent = codeStudent;
        this.codeClass = codeClass;
        this.nameStudent = nameStudent;
        this.nameClass = nameClass;
        this.img = img;
    }
}

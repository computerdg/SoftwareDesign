package com.example.termproject;

import java.time.LocalDate;

public class Product {

    String productName; // 제품명
    LocalDate buyDate; // 구매 날짜
    LocalDate startDate; // 시작일
    LocalDate finishDate; // 종료일
    LocalDate noticeDate; // 알림일
    String imgPath; // 이미지 경로

    @Override
    public String toString(){
        String str = this.productName + "\n" + buyDate.toString() + "\n" + startDate.toString() + "\n"
                + finishDate.toString() + "\n" + noticeDate.toString() + "\n" + this.imgPath;
        return str;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Product() {};

    public Product(String productName, LocalDate buyDate, LocalDate startDate, LocalDate noticeDate, LocalDate finishDate, String imgPath) {
        this.productName = productName;
        this.buyDate = buyDate;
        this.startDate = startDate;
        this.noticeDate = noticeDate;
        this.finishDate = finishDate;
        this.imgPath = imgPath;
    }



    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public LocalDate getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        this.buyDate = buyDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getNoticeDate() {
        return noticeDate;
    }

    public void setNoticeDate(LocalDate noticeDate) {
        this.noticeDate = noticeDate;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}

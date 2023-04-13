package com.clu.hello.diary.model;

public class DiaryModel {
    int id;
    String fullName;
    String diaryDate;
    String diaryWeather;
    String diaryContent;

    public DiaryModel() {
    }

    public DiaryModel(int id, String fullName, String diaryDate, String diaryWeather, String diaryContent) {
        this.id = id;
        this.fullName = fullName;
        this.diaryDate = diaryDate;
        this.diaryWeather = diaryWeather;
        this.diaryContent = diaryContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getDiaryDate() {
        return diaryDate;
    }

    public void setDiaryDate(String diaryDate) {
        this.diaryDate = diaryDate;
    }

    public String getDiaryWeather() {
        return diaryWeather;
    }

    public void setDiaryWeather(String diaryWeather) {
        this.diaryWeather = diaryWeather;
    }

    public String getDiaryContent() {
        return diaryContent;
    }

    public void setDiaryContent(String diaryContent) {
        this.diaryContent = diaryContent;
    }

}

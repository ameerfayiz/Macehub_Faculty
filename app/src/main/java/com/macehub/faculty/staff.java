package com.macehub.faculty;

public class staff {
    String department;
    String details;
    int fav;
    String id;
    String imgloc;
    String name;

    public staff(String id, String name, String det, String imloc, String dep) {
        this.id = id;
        this.name = name;
        this.details = det;
        this.imgloc = imloc;
        this.department = dep;
        this.fav = 0;
    }

    public staff(String id, String name, String det, String imloc, String dep,int fav) {
        this.id = id;
        this.name = name;
        this.details = det;
        this.imgloc = imloc;
        this.department = dep;
        this.fav = fav;
    }

    public staff(String name, String det) {
        this.id = "";
        this.name = name;
        this.details = det;
        this.imgloc = "";
        this.department = "";
        this.fav = 0;
    }

    public staff() {
        this.id = "";
        this.name = "";
        this.details = "";
        this.imgloc = "";
        this.department = "";
        this.fav = 0;
    }

    public void setId(String str) {
        this.id = str;
    }

    public String getId() {
        return this.id;
    }

    public void setFav(int i) {
        this.fav = i;
    }

    public int getFav() {
        return this.fav;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getDetails() {
        return this.details;
    }

    public void setDetails(String str) {
        this.details = str;
    }

    public String getImgloc() {
        return this.imgloc;
    }

    public void setImgloc(String str) {
        this.imgloc = str;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String str) {
        this.department = str;
    }
}

package com.qtd.muabannhadat.model;

/**
 * Created by Dell on 4/18/2016.
 */
public class Notification {
    private int idApartment;
    private String image;
    private String content;
    private String time;
    private int price;

    public Notification(int idApartment, String image, String content, int price, String time) {
        this.idApartment = idApartment;
        this.image = image;
        this.content = "1 " + content + " đã được đăng lên";
        this.price = price;
        this.time = time;
    }

    public int getPrice() {
        return price;
    }

    public int getIdApartment() {
        return idApartment;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }
}

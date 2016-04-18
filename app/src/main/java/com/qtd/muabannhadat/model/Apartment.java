package com.qtd.muabannhadat.model;

import java.util.List;

/**
 * Created by Dell on 4/9/2016.
 */
public class Apartment {
    private int id;
    private User user = new User();
    private String status;
    private String kind;
    private float area;
    private String city;
    private String district;
    private String street;
    private String address;
    private int price;
    private String describe;
    private int numberOfRoom;
    private double latitude;
    private double longitude;
    private List<String> images;
    private String imageFirst;
    public boolean isLiked = false;

    public Apartment() {

    }

    public Apartment(int id, String address, String city, int price, String imageFirst) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.price = price;
        this.imageFirst = imageFirst;
    }
    public String getImageFirst() {
        return imageFirst;
    }

    public void setImageFirst(String imageFirst) {
        this.imageFirst = imageFirst;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }


    public Apartment(int id, String status, String kind, float area, String city, String district, String street, String address, int price, String describe, int numberOfRoom, float latitude, float longitude, String imageFirst) {
        this.id = id;
        this.status = status;
        this.kind = kind;
        this.area = area;
        this.city = city;
        this.district = district;
        this.street = street;
        this.address = address;
        this.price = price;
        this.describe = describe;
        this.numberOfRoom = numberOfRoom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageFirst = imageFirst;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getNumberOfRoom() {
        return numberOfRoom;
    }

    public void setNumberOfRoom(int numberOfRoom) {
        this.numberOfRoom = numberOfRoom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

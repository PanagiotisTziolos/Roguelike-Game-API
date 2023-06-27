package com.booleanuk.gameapi.models;

public class GameObject {
    public byte[] image;
    public int[] position;
    public double hp;

    public GameObject() {}

    public GameObject(byte[] image, int[] position, double hp) {
        this.image = image;
        this.position = position;
        this.hp = hp;
    }
}

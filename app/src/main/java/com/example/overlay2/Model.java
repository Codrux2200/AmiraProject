package com.example.overlay2;
import com.example.overlay2.R;

public class Model {

    private int id;
    private float postionX;
    private float positionY;

    public Model(int id, float positionY, float positionX){
        this.id=id;
        this.positionY=positionY;
        this.postionX=positionX;


    }

    public float getPositionY() {
        return positionY;
    }

    public float getPositionX() {
        return postionX;
    }

    public int getid() {
        return id;
    }



}
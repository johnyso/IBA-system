package de.ibs.app.room.processor;

import android.graphics.RectF;

/**
 * Created by johnyso on 25.11.14.
 */
public class Room {
    private int id;
    private String name;
    private int width;
    private int length;
    private int height;
    private int personX;
    private int personY;
    private int personHeight;
    private float paddingLeft;
    private double lengthInPixel;
    private double widthInPixel;

    public float getPaddingLeft() {
        return paddingLeft;
    }

    public double getLengthInPixel() {
        return lengthInPixel;
    }

    public void setPaddingLeft(float paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPersonX(int personX) {
        this.personX = personX;
    }

    public void setPersonY(int personY) {
        this.personY = personY;
    }

    public void setPersonHeight(int personHeight) {
        this.personHeight = personHeight;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public int getHeight() {
        return height;
    }

    public Integer getPersonX() {
        return personX;
    }

    public int getPersonY() {
        return personY;
    }

    public int getPersonHeight() {
        return personHeight;
    }

    private Room(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.width = builder.width;
        this.length = builder.length;
        this.height = builder.height;
        this.personX = builder.personX;
        this.personY = builder.personY;
        this.personHeight = builder.personHeight;
    }

    public RectF getRectInPixel(int height, int width) {
        RectF drawRoundRect = new RectF();
        // get 100%

        double pixelWidth = (double) width / (double) this.getWidth();
        double pixelLength = (double) height / (double) this.getLength();

        if (this.getLength() * pixelWidth < height){
            this.widthInPixel = (double) this.getWidth() * pixelWidth;
            this.lengthInPixel = (double) this.getLength() * pixelWidth;
        } else {
            this.widthInPixel = (double) this.getWidth() * pixelLength;
            this.lengthInPixel = (double) this.getLength() * pixelLength;
        }

        this.paddingLeft = (float) (width - this.widthInPixel) / 2;

        drawRoundRect.set(this.paddingLeft, 0, (int) this.widthInPixel + this.paddingLeft, (float) this.lengthInPixel);
        return drawRoundRect;
    }

    public static class Builder {
        private int id;
        private String name;
        private int width;
        private int length;
        private int height;
        private int personX;
        private int personY;
        private int personHeight;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder length(int length) {
            this.length = length;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder personX(int personX) {
            this.personX = personX;
            return this;
        }

        public Builder personY(int personY) {
            this.personY = personY;
            return this;
        }

        public Builder personHeight(int personHeight) {
            this.personHeight = personHeight;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}

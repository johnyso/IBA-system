package de.ibs.app.room.processor;

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

        public Builder name(String name){
            this.name = name;
            return this;
        }

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Builder length(int length){
            this.length = length;
            return this;
        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder personX(int personX){
            this.personX = personX;
            return this;
        }

        public Builder personY(int personY){
            this.personY = personY;
            return this;
        }

        public Builder personHeight(int personHeight){
            this.personHeight = personHeight;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}

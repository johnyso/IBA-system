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

    private Room(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.width = builder.width;
        this.length = builder.length;
        this.height = builder.height;
    }

    public static class Builder {
        private int id;
        private String name;
        private int width;
        private int length;
        private int height;


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

        public Room build() {
            return new Room(this);
        }
    }
}

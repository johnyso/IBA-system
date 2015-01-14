package de.ibs.app.speaker.processor;

/**
 * Created by johnyso on 26.11.14.
 */
public class Speaker {
    private int id;
    private String name;
    private String ip;
    private int positionX;
    private int positionY;
    private int alignment;
    private int positionHeight;
    private int horizontal;
    private int vertical;
    private int roomId;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getAlignment() {
        return alignment;
    }

    public int getPositionHeight() {
        return positionHeight;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public int getRoomId() {
        return roomId;
    }

    private Speaker(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.ip = builder.ip;
        this.positionX = builder.positionX;
        this.positionY = builder.positionY;
        this.alignment = builder.alignment;
        this.positionHeight = builder.positionHeight;
        this.horizontal = builder.horizontal;
        this.vertical = builder.vertical;
        this.roomId = builder.roomId;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public static class Builder {
        private int id;
        private String name;
        private String ip;
        private int positionX;
        private int positionY;
        private int alignment;
        private int positionHeight;
        private int horizontal;
        private int vertical;
        private int roomId;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder positionX(int positionX) {
            this.positionX = positionX;
            return this;
        }

        public Builder positionY(int positionY) {
            this.positionY = positionY;
            return this;
        }

        public Builder alignment(int alignment) {
            this.alignment = alignment;
            return this;
        }

        public Builder positionHeight(int positionHeight) {
            this.positionHeight = positionHeight;
            return this;
        }

        public Builder horizontal(int horizontal) {
            this.horizontal = horizontal;
            return this;
        }

        public Builder vertical(int vertical) {
            this.vertical = vertical;
            return this;
        }

        public Builder roomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Speaker build() {
            return new Speaker(this);
        }
    }
}

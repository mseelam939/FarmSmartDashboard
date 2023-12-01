package application;

import java.util.ArrayList;
import java.util.List;

public class FarmItemsContainer implements FarmComponent {
    private List<FarmComponent> children = new ArrayList<>();
    private String name;
    private double price;
    private int locationX;
    private int locationY;
    private int length;
    private int width;
    private int height;

    public FarmItemsContainer(String name, double price, int locationX, int locationY, int length, int width, int height) {
        this.name = name;
        this.price = price;
        this.locationX = locationX;
        this.locationY = locationY;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    @Override
    public void add(FarmComponent component) {
        children.add(component);
    }

    @Override
    public void remove(FarmComponent component) {
        children.remove(component);
    }

    @Override
    public List<FarmComponent> getChildren() {
        return children;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int getLocationX() {
        return locationX;
    }

    @Override
    public void setLocationX(int x) {
        this.locationX = x;
    }

    @Override
    public int getLocationY() {
        return locationY;
    }

    @Override
    public void setLocationY(int y) {
        this.locationY = y;
    }

    @Override
    public int getLength() {
        return length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }
}

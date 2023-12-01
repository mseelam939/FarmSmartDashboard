package application;

import java.util.ArrayList;

public class Singleton {
    private static Singleton instance;
    private ArrayList<FarmItem> farmItems;
    private ArrayList<FarmItemsContainer> farmItemsContainers;

    private Singleton() {
        // Initialize the lists for items and item-containers
        farmItems = new ArrayList<>();
        farmItemsContainers = new ArrayList<>();
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public void addItem(FarmItem item) {
        farmItems.add(item);
    }

    public void addItemContainer(FarmItemsContainer itemContainer) {
        farmItemsContainers.add(itemContainer);
    }

    public ArrayList<FarmItem> getFarmItems() {
        return farmItems;
    }

    public ArrayList<FarmItemsContainer> getFarmItemsContainers() {
        return farmItemsContainers;
    }
}

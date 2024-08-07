package main;

import main.item.Item;
import main.item.GeneralItemManager;

public class Main {
    public static void main(String[] args) {
        try {
            GeneralItemManager<Item> manager = new GeneralItemManager<>();


            Item newItem = new Item("112", "Test Item");
            newItem.setId(16);
            manager.insert(newItem);


            Item queryItem = new Item("112", "Test Item");
            queryItem.setId(16);
            var items = manager.select(queryItem);

            // Print the results
            for (Item item : items) {
                System.out.println(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

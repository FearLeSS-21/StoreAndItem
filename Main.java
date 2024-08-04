package main;

import main.item.Item;
import main.item.ItemManager;
import main.store.Store;
import main.store.StoreManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ItemManager itemManager = new ItemManager();
        StoreManager storeManager = new StoreManager();

        System.out.println("please select only 1 option -> (insert/select/search): ");
        String action = scanner.nextLine();

        System.out.println("please select only 1 option -> (item/store): ");
        String entity = scanner.nextLine();

        if (action.equalsIgnoreCase("insert")) {
            if (entity.equalsIgnoreCase("item")) {
                System.out.println("Enter item code: ");
                String itemCode = scanner.nextLine();
                System.out.println("Enter item name: ");
                String itemName = scanner.nextLine();

                Item item = new Item(null, null);
                item.setItemCode(itemCode);
                item.setItemName(itemName);

                itemManager.insert(item);

            } else if (entity.equalsIgnoreCase("store")) {
                System.out.println("Enter store code: ");
                String storeCode = scanner.nextLine();
                System.out.println("Enter store name: ");
                String storeName = scanner.nextLine();

                Store store = new Store(null, null);
                store.setStoreCode(storeCode);
                store.setStoreName(storeName);

                storeManager.insert(store);
            }
        } else if (action.equalsIgnoreCase("select")) {
            if (entity.equalsIgnoreCase("item")) {
                if (itemManager.isDatabaseEmpty()) {
                    System.out.println("Error: No data in the item table.");
                } else {
                    System.out.println("Enter item code: ");
                    String itemCode = scanner.nextLine();
                    List<Item> items = itemManager.select(itemCode);
                    if (!items.isEmpty()) {
                        for (Item item : items) {
                            System.out.println("Item ID: " + item.getItemId());
                            System.out.println("Item Code: " + item.getItemCode());
                            System.out.println("Item Name: " + item.getItemName());
                        }
                    } else {
                        System.out.println("Error: Item with code " + itemCode + " not found.");
                    }
                }
            } else if (entity.equalsIgnoreCase("store")) {
                if (storeManager.isDatabaseEmpty()) {
                    System.out.println("Error: No data in the store table.");
                } else {
                    System.out.println("Enter store code: ");
                    String storeCode = scanner.nextLine();
                    List<Store> stores = (List<Store>) storeManager.select(storeCode);
                    if (!stores.isEmpty()) {
                        for (Store store : stores) {
                            System.out.println("Store ID: " + store.getStoreId());
                            System.out.println("Store Code: " + store.getStoreCode());
                            System.out.println("Store Name: " + store.getStoreName());
                        }
                    } else {
                        System.out.println("Error: Store with code " + storeCode + " not found.");
                    }
                }
            }
        } else if (action.equalsIgnoreCase("search")) {
            if (entity.equalsIgnoreCase("item")) {
                System.out.println("Enter item name substring to search for: ");
                String nameSubstring = scanner.nextLine();
                List<Item> items = itemManager.findItemsByNameContains(nameSubstring);
                if (!items.isEmpty()) {
                    for (Item item : items) {
                        System.out.println("Item ID: " + item.getItemId());
                        System.out.println("Item Code: " + item.getItemCode());
                        System.out.println("Item Name: " + item.getItemName());
                    }
                } else {
                    System.out.println("No items found with name containing: " + nameSubstring);
                }
            }
        }

        scanner.close();
    }
}

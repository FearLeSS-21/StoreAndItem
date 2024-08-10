package org.example.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public List<Item> getByItemCode(String itemCode) {
        return itemRepository.findByItemCode(itemCode);
    }

    public List<Item> getByNameContains(String name) {
        return itemRepository.findByItemNameContaining(name);
    }
}

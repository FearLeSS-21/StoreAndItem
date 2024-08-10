package org.example.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemService.save(item);
    }

    @GetMapping("/{itemCode}")
    public List<Item> getItemsByCode(@PathVariable String itemCode) {
        return itemService.getByItemCode(itemCode);
    }

    @GetMapping("/search")
    public List<Item> searchItemsByName(@RequestParam String name) {
        return itemService.getByNameContains(name);
    }
}

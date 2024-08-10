package org.example.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItemCode(String itemCode);
    List<Item> findByItemNameContaining(String itemName);
}
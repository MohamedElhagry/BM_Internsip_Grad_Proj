package com.project.bm_internsip_grad_project.services;

import com.project.bm_internsip_grad_project.entities.Item;
import com.project.bm_internsip_grad_project.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepo itemRepo;

    public Optional<Item> addItem(Item item)
    {
        if(itemRepo.existsByName(item.getName()))
            return Optional.empty();
        try
        {
            Optional<Item> opt = Optional.of(itemRepo.save(item));
            return opt;
        }
        catch (Exception e)
        {
            return Optional.empty();
        }
    }
}

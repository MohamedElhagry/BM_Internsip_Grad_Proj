package com.project.bm_internsip_grad_project.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.project.bm_internsip_grad_project.entities.Item;
import com.project.bm_internsip_grad_project.repositories.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/browse")
public class ItemController {

    @Autowired
    ItemRepo itemRepo;

    @GetMapping("/getAllItems")
    public List<Item> getItems()
    {
        List<Item> items = itemRepo.findAll();
        return items;
    }

    @GetMapping("/findItem")
    public ResponseEntity<?> findItemByName(String name)
    {
        try
        {
            Optional<Item> item = itemRepo.findByName(name);
            if(item.isPresent())
                return ResponseEntity.ok().body(item.get());
        }
        catch (Exception e)
        {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.notFound().build();
    }



}

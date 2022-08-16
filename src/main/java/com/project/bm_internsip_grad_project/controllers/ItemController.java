package com.project.bm_internsip_grad_project.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.project.bm_internsip_grad_project.DTOs.AddItemDTO;
import com.project.bm_internsip_grad_project.entities.Item;
import com.project.bm_internsip_grad_project.repositories.ItemRepo;
import com.project.bm_internsip_grad_project.services.ItemService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/browse")
public class ItemController {

    @Autowired
    ItemRepo itemRepo;
    @Autowired
    ItemService itemService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/addItem")
    public ResponseEntity<?> addItem(@RequestBody AddItemDTO addItemDTO)
    {
        Item item = new Item();
        modelMapper.map(addItemDTO, item);

        try {
            Optional<Item> itemOptional = itemService.addItem(item);
            if (itemOptional.isPresent())
                return ResponseEntity.ok().body(itemOptional.get().toString());
            else
                return ResponseEntity.badRequest().body("Couldn't add item");
        }
        catch (Exception e)
        {
            return ResponseEntity.badRequest().body("Couldn't add item");
        }
    }

    @GetMapping("/getAllItems")
    public List<Item> getItems()
    {
        List<Item> items = itemRepo.findAll();
        return items;
    }

    @GetMapping("/findItem")
    public ResponseEntity<?> findItemByName(@RequestBody String name)
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

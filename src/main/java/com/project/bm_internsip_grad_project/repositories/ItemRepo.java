package com.project.bm_internsip_grad_project.repositories;

import com.project.bm_internsip_grad_project.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepo extends JpaRepository<Item, Long> {

    List<Item> findByCategory(String category);
    Boolean existsByName(String name);
    Optional<Item> findByName(String name);
}

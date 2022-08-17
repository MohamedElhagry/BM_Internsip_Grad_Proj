package com.project.bm_internsip_grad_project.DTOs;

import lombok.Data;

@Data
public class AddItemDTO {
    String name;
    Long price;
    Integer bough_items_count;
    String description;
    String image;
}

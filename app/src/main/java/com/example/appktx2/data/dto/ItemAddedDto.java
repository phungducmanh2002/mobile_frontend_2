package com.example.appktx2.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAddedDto {
    Integer id;
    String itemName;
    int quantity;
    int quantityInRoom;
    int quantityLeft;
    Integer idResource;
}

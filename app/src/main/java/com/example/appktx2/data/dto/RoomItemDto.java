package com.example.appktx2.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomItemDto {
    Integer idRoom;
    Integer idItem;
    int quantity;
}

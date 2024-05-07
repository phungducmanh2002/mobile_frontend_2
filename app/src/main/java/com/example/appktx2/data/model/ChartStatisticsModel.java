package com.example.appktx2.data.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChartStatisticsModel {
    String roomName;
    Integer totalPaidMoney;
    Integer totalMoney;
}

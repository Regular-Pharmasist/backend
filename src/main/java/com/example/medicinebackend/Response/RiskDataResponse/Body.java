package com.example.medicinebackend.Response.RiskDataResponse;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Body {

    private List<Item> items;

}



package kopo.poly.dto;

import lombok.Data;

import java.util.List;

@Data
public class MileDTO {
    private List<String> itemValue;
    private List<String> itemStartDate;
    private List<String> itemEndDate;
}

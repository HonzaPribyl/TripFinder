package cz.sa.tripfinder.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirportDTO {
    private Long id;
    private String iata;
    private String name;
}

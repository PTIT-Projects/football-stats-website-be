package vn.ptit.project.epl_web.dto.response.player;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ResponsePlayerDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDate dob;
    private int shirtNumber;
    private double marketValue;
    private String currentClub;
    private String imagePath;
    private List<String> citizenships;
    private List<String> positions;
    private List<ResponseCreateTransferHistoryDTO> transferHistories;
}

package vn.ptit.project.epl_web.dto.response.club;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;

import java.util.List;

@Getter
@Setter
public class ResponseClubDTO {
    private Long id;
    private String name;
    private String country;
    private String stadiumName;
    // Field to return the club logo image URL/path
    private String imagePath;

    private List<ResponseCreateTransferHistoryDTO> transferHistories;
    private CoachDTO currentCoach;
    private List<PlayerDTO> currentPlayerList;
}

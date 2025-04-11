package vn.ptit.project.epl_web.dto.response.coach;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.dto.response.coachclub.ResponseCoachClubDTO;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ResponseCoachDTO {
    private Long id;
    private String name;
    private int age;
    private LocalDate dob;
    // Field to return the coach image URL/path
    private String imagePath;
    private List<String> citizenships;
    public List<ResponseCoachClubDTO> coachClubs;
    private ClubDTO currentClub;
}
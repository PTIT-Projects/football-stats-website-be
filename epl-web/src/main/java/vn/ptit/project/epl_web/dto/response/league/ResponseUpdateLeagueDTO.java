package vn.ptit.project.epl_web.dto.response.league;

import lombok.Getter;
import lombok.Setter;
import vn.ptit.project.epl_web.dto.response.leagueseason.LeagueSeasonDTO;

import java.util.List;

@Getter
@Setter
public class ResponseUpdateLeagueDTO {
    private Long id;
    private String name;
    // Field to return the league logo image URL/path
    private String imagePath;
    private List<LeagueSeasonDTO> leagueSeasons;
}

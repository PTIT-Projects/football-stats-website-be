package vn.ptit.project.epl_web.dto.request.clubseasontable;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@Setter
public class RequestCreateClubSeasonTableDTO {
    @NotNull
    private int numWins,numLosses,numDraws,goalScores,goalConceded;
    @NotNull
    private Long season,club;

}

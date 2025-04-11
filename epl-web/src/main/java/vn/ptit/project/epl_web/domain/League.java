package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Table(name="leagues")
@Getter
@Setter
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    // Field to store the league logo image URL/path
    private String imagePath;
    
    @OneToMany(mappedBy = "league",cascade = CascadeType.ALL)
    private List<LeagueSeason> leagueSeasons;

}

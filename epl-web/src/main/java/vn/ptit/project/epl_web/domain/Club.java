package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Entity

@Table(name = "clubs")
@Data
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    private String stadiumName;
    
    // Field to store the club logo image URL/path
    private String imagePath;
    
    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<CoachClub> coachClubs;
    @OneToMany(mappedBy = "club")
    private List<ClubSeasonTable> clubSeasonTables;
    @OneToMany(mappedBy = "club")
    private List<TransferHistory> transferHistories;

}

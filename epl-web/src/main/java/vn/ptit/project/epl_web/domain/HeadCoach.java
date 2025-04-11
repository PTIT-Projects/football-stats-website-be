package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "head_coaches")
@Data
public class HeadCoach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dob;
    
    // Field to store the coach image URL/path
    private String imagePath;
    
    @ElementCollection
    @CollectionTable(name = "coach_citizenship", joinColumns = @JoinColumn(name = "head_coach_id"))
    @Column(name = "citizenship")
    private List<String> citizenships;
    @OneToMany(mappedBy = "headCoach",cascade = CascadeType.ALL)
    private List<CoachClub> coachClubs;

}

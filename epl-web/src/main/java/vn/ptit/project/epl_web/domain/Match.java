package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name="matches"
,uniqueConstraints=@UniqueConstraint(columnNames = {"host_id","away_id","season_id"}))
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="host_id")
    private Club host;
    @ManyToOne
    @JoinColumn(name="away_id")
    private Club away;
    @ManyToOne
    @JoinColumn(name="season_id")
    private LeagueSeason season;
    //MatchAction
//    @OneToMany(mappedBy = "hostTeam")
//    private Set<Club> hostClubs;
//    @OneToMany(mappedBy = "awayTeam")
//    private Set<Club> awayClubs;
    @OneToMany(mappedBy = "match")
    private Set<MatchAction> matchActions;


    private int round,awayScore,hostScore;
    private LocalDateTime date;



}

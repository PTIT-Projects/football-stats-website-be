package vn.ptit.project.epl_web.domain;

import jakarta.persistence.*;

@Entity
public class MatchAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private int minute;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

}

package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.LeagueSeason;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long>, JpaSpecificationExecutor<Club> {
    @Query("SELECT DISTINCT cst.season FROM ClubSeasonTable cst WHERE cst.club.id = :clubId ORDER BY cst.season.startDate DESC")
    List<LeagueSeason> findLeagueSeasonsByClubId(@Param("clubId") Long clubId);
}

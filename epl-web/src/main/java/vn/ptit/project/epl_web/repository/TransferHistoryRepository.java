package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.project.epl_web.domain.TransferHistory;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Query(value = """
    SELECT th.*
    FROM transfer_history th
    JOIN league_season ls 
      ON th.date BETWEEN DATE_SUB(ls.start_date, INTERVAL 4 MONTH) AND ls.end_date
    WHERE ls.id = :seasonId
      AND (
        th.club_id = :clubId
        OR EXISTS (
          SELECT 1 FROM transfer_history th2
          WHERE th2.player_id = th.player_id
            AND th2.club_id = :clubId
            AND th2.date < th.date
        )
      )
    ORDER BY th.date DESC
""", nativeQuery = true)
    List<TransferHistory> findAllTransfersByClubAndSeason(
            @Param("clubId") Long clubId,
            @Param("seasonId") Long seasonId
    );

}

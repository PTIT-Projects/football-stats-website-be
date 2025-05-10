package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.project.epl_web.domain.TransferHistory;

import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Query("""
        SELECT th
        FROM TransferHistory th
        JOIN vn.ptit.project.epl_web.domain.LeagueSeason ls 
          ON th.date BETWEEN ls.startDate AND ls.endDate
        WHERE ls.id = :seasonId
          AND (
            th.club.id = :clubId
            OR
            EXISTS (
              SELECT 1 FROM TransferHistory th2
              WHERE th2.player.id = th.player.id
                AND th2.club.id = :clubId
                AND th2.date < th.date
            )
          )
        ORDER BY th.date DESC
    """)
    List<vn.ptit.project.epl_web.domain.TransferHistory> findAllTransfersByClubAndSeason(
        @Param("clubId") Long clubId,
        @Param("seasonId") Long seasonId
    );
}

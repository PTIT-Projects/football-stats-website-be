package vn.ptit.project.epl_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.ptit.project.epl_web.domain.TransferHistory;

import java.time.LocalDate;
import java.util.List;

public interface TransferHistoryRepository extends JpaRepository<TransferHistory, Long> {
    @Query("""
        SELECT th
        FROM TransferHistory th
        JOIN LeagueSeason ls ON th.date BETWEEN ls.startDate AND ls.endDate
        WHERE (th.club.id = :clubId OR th.player.id IN (
            SELECT p.id
            FROM Player p
            JOIN p.transferHistories th2
            WHERE th2.club.id = :clubId
        ))
        AND ls.id = :seasonId
    """)
    List<vn.ptit.project.epl_web.domain.TransferHistory> findAllTransfersByClubAndSeason(
        @Param("clubId") Long clubId,
        @Param("seasonId") Long seasonId
    );

    @Query("""
        SELECT th
        FROM TransferHistory th
        WHERE th.date BETWEEN :from AND :to
          AND th.club.id <> :clubId
          AND EXISTS (
            SELECT 1
            FROM TransferHistory prev
            WHERE prev.player.id = th.player.id
              AND prev.club.id = :clubId
              AND prev.date <= :seasonStart
          )
        ORDER BY th.date DESC
    """)
    List<TransferHistory> findTransferOutsByClubAndPeriod(
        @Param("clubId") Long clubId,
        @Param("seasonStart") LocalDate seasonStart,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
    );
}

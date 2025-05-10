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
        WHERE th.date BETWEEN :from AND :to
          AND (
            th.club.id = :clubId
            OR
            EXISTS (
              SELECT 1 FROM TransferHistory prev
              WHERE prev.player.id = th.player.id
                AND prev.club.id = :clubId
                AND prev.date < th.date
            )
          )
        ORDER BY th.date DESC
    """)
    List<TransferHistory> findAllTransfersByClubAndSeason(
        @Param("clubId") Long clubId,
        @Param("from") LocalDate from,
        @Param("to") LocalDate to
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

package vn.ptit.project.epl_web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.domain.LeagueSeason;
@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    Page<League> findAll(Specification<League> spe, Pageable pageable);
}

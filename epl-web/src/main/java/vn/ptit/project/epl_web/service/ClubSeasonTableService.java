package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.ClubSeasonTable;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.request.clubseasontable.RequestUpdateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ResponseCreateClubSeasonTableDTO;
import vn.ptit.project.epl_web.dto.response.clubseasontable.ClubSeasonTablesDTO;
import vn.ptit.project.epl_web.repository.ClubSeasonTableRepository;

import java.util.Optional;

@Service
public class ClubSeasonTableService {
        private final ClubSeasonTableRepository clubSeasonTableRepository;
        private final ClubService clubService;
        private final LeagueSeasonService leagueSeasonService;
        private final ModelMapper modelMapper;
    public ClubSeasonTableService(ClubSeasonTableRepository clubSeasonTableRepository, ClubService clubService, @Lazy LeagueSeasonService leagueSeasonService, ModelMapper modelMapper) {
        this.clubSeasonTableRepository = clubSeasonTableRepository;
        this.clubService = clubService;
        this.leagueSeasonService = leagueSeasonService;
        this.modelMapper = modelMapper;
    }
    public ClubSeasonTablesDTO tableToClubSeasonTableDTO(ClubSeasonTable clubSeasonTable){
            ClubSeasonTablesDTO clubSeasonTablesDTO = modelMapper.map(clubSeasonTable, ClubSeasonTablesDTO.class);
            clubSeasonTablesDTO.setClub(this.clubService.clubToResponseClubDTO(clubSeasonTable.getClub()));
            clubSeasonTablesDTO.setSeason(this.leagueSeasonService.leagueSeasonToLeagueSeasonDTO(clubSeasonTable.getSeason()));
            return clubSeasonTablesDTO;
    }
    public ClubSeasonTable requestCreateClubSeasonTableDTOtoClubSeasonTable(RequestCreateClubSeasonTableDTO requestCreateClubSeasonTableDTO){
        ClubSeasonTable clubSeasonTable=modelMapper.map(requestCreateClubSeasonTableDTO, ClubSeasonTable.class);
        clubSeasonTable.setPoints(requestCreateClubSeasonTableDTO.getNumWins() * 3 + requestCreateClubSeasonTableDTO.getNumDraws() * 1);
        clubSeasonTable.setDiff(requestCreateClubSeasonTableDTO.getGoalScores() - requestCreateClubSeasonTableDTO.getGoalConceded());
        clubSeasonTable.setClub(clubService.getClubById(requestCreateClubSeasonTableDTO.getClub()).get());
        clubSeasonTable.setSeason(leagueSeasonService.findByLeagueSeasonId(requestCreateClubSeasonTableDTO.getSeason()));
        return clubSeasonTable;
    }
    public void handleCreateClubSeasonTable(ClubSeasonTable clubSeasonTable){
        clubSeasonTable.setRanked(0);
        ClubSeasonTable res = clubSeasonTableRepository.save(clubSeasonTable);
        this.leagueSeasonService.updateLeagueTableRankings(res.getSeason().getId());
    }
    public ResponseCreateClubSeasonTableDTO clubSeasonTabletoResponseCreateClubSeasonTableDTO(ClubSeasonTable clubSeasonTable){
        ResponseCreateClubSeasonTableDTO responseCreateClubSeasonTableDTO=modelMapper.map(clubSeasonTable, ResponseCreateClubSeasonTableDTO.class);
        responseCreateClubSeasonTableDTO.setClub(clubSeasonTable.getClub().getId());
        responseCreateClubSeasonTableDTO.setSeason(clubSeasonTable.getSeason().getId());
        return responseCreateClubSeasonTableDTO;
    }
    public ClubSeasonTable handleUpdateClubSeasonTable(RequestUpdateClubSeasonTableDTO dto){
        ClubSeasonTable clubSeasonTable=modelMapper.map(dto, ClubSeasonTable.class);
        clubSeasonTable.setClub(clubService.getClubById(dto.getClub()).get());
        clubSeasonTable.setSeason(leagueSeasonService.findByLeagueSeasonId(dto.getSeason()));
        clubSeasonTable.setRanked(0);
        clubSeasonTable.setPoints(dto.getNumWins() * 3 + dto.getNumDraws() * 1);
        clubSeasonTable.setDiff(dto.getGoalScores() - dto.getGoalConceded());
        ClubSeasonTable res =  clubSeasonTableRepository.save(clubSeasonTable);
        this.leagueSeasonService.updateLeagueTableRankings(res.getSeason().getId());
        return this.getClubSeasonTableById(res.getId()).get();
    }
    public void handleUpdateClubSeasonTable(ClubSeasonTable clubSeasonTable){
        clubSeasonTableRepository.save(clubSeasonTable);
    }
    public Optional<ClubSeasonTable> getClubSeasonTableById(Long id) {
        return this.clubSeasonTableRepository.findById(id);
    }
    public void handleDeleteClubSeasonTable(Long id) {
        this.clubSeasonTableRepository.deleteById(id);
    }
}

package vn.ptit.project.epl_web.service;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.*;
import vn.ptit.project.epl_web.dto.request.club.RequestCreateClubDTO;
import vn.ptit.project.epl_web.dto.request.club.RequestUpdateClubDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.club.*;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;
import vn.ptit.project.epl_web.repository.ClubSeasonTableRepository;
import vn.ptit.project.epl_web.repository.MatchRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClubService {
    private final ClubRepository clubRepository;
    private final ModelMapper modelMapper;
    private final TransferHistoryService transferHistoryService;
    private final CoachClubService coachClubService;
    private final ClubSeasonTableRepository clubSeasonTableRepository;
    private final MatchRepository matchRepository;

    public ClubService(ClubRepository clubRepository, ModelMapper modelMapper, TransferHistoryService transferHistoryService, CoachClubService coachClubService, ClubSeasonTableRepository clubSeasonTableRepository, MatchRepository matchRepository) {
        this.clubRepository = clubRepository;
        this.modelMapper = modelMapper;
        this.transferHistoryService = transferHistoryService;
        this.coachClubService = coachClubService;
        this.clubSeasonTableRepository = clubSeasonTableRepository;
        this.matchRepository = matchRepository;
    }

    public Club handleCreateClub(Club club) {
        return this.clubRepository.save(club);
    }
    public Club requestCreateClubToClub(RequestCreateClubDTO clubDTO) {
        return this.modelMapper.map(clubDTO, Club.class);
    }
    public ResponseCreateClubDTO clubToResponseCreateClubDTO(Club club) {
        return this.modelMapper.map(club, ResponseCreateClubDTO.class);
    }
    public Optional<Club> getClubById(Long id) {
        return this.clubRepository.findById(id);
    }
    public Club handleUpdateClub(Club club, RequestUpdateClubDTO clubDTO) {
        this.modelMapper.map(clubDTO, club);
        return this.clubRepository.save(club);
    }
    public ResponseUpdateClubDTO clubToResponseUpdateClub(Club club) {
        return this.modelMapper.map(club, ResponseUpdateClubDTO.class);

    }
    public ResultPaginationDTO fetchAllClubs(Specification<Club> spec, Pageable pageable) {
        Page<Club> clubPage = this.clubRepository.findAll(spec, pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(clubPage.getTotalPages());
        meta.setTotal(clubPage.getTotalElements());
        result.setMeta(meta);
        List<ResponseClubDTO> list = clubPage.getContent().stream().map(this::clubToResponseClubDTO).toList();
        result.setResult(list);
        return result;
    }
    public void handleDeleteClub(Long id) throws InvalidRequestException {
        Optional<Club> club = this.clubRepository.findById(id);
        if (club.isPresent()) {
            Club deletedClub = club.get();

            for (TransferHistory th : deletedClub.getTransferHistories()) {
                this.transferHistoryService.handleDeleteTransferHistory(th.getId());
            }
            for (CoachClub cc : deletedClub.getCoachClubs()) {
                this.coachClubService.handleDeleteCoachClubWithId(cc.getId());
            }
            for (ClubSeasonTable cst : deletedClub.getClubSeasonTables()) {
                this.clubSeasonTableRepository.delete(cst);
            }
            ArrayList<Match> matchesAsHost = new ArrayList<>(this.matchRepository.findByHost(deletedClub));
            for (Match match : matchesAsHost) {
                this.matchRepository.delete(match);
            }

            List<Match> matchesAsAway =new ArrayList<>(this.matchRepository.findByAway(deletedClub));;
            for (Match match : matchesAsAway) {
                this.matchRepository.delete(match);
            }
            this.clubRepository.delete(deletedClub);
        }

    }

    public ResponseClubDTO clubToResponseClubDTO(Club club) {
        ResponseClubDTO clubDTO = this.modelMapper.map(club, ResponseClubDTO.class);
        List<ResponseCreateTransferHistoryDTO> transferHistories = new ArrayList<>();
        for (TransferHistory th : club.getTransferHistories()) {
            transferHistories.add(this.transferHistoryService.transferHistoryToResponseCreateTransferHistoryDTO(th));
        }
        clubDTO.setTransferHistories(transferHistories);
        clubDTO.setCurrentCoach(findCurrentCoachByClub(club));
        return clubDTO;
    }
    public CoachDTO findCurrentCoachByClub(Club club)
    {
        List<CoachClub> coachClubList=club.getCoachClubs();
        List<CoachClub> sortedList = coachClubList.stream()
                .sorted(Comparator.comparing(CoachClub::getEndDate).reversed()) // Ngày gần nhất trước
                .toList();
        if(sortedList.isEmpty())
        {
            return null;
        }
        else
        {
            CoachClub coachClub=sortedList.get(0);
            HeadCoach coach=coachClub.getHeadCoach();
            return modelMapper.map(coach, CoachDTO.class);
        }

    }
//    public int numWinSeason (Long clubId)
//    {
//        Club club=clubRepository.findById(clubId).get();
//        int count = 0;
//        for(ClubSeasonTable cst:club.getClubSeasonTables())
//        {
//            if(cst.getRanked()==1)
//            {
//                count++;
//            }
//        }
//        return count;
//    }
}

package vn.ptit.project.epl_web.service;

import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.Request;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Club;
import vn.ptit.project.epl_web.domain.LeagueSeason;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.domain.TransferHistory;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestUpdateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.ClubRepository;
import vn.ptit.project.epl_web.repository.LeagueSeasonRepository;
import vn.ptit.project.epl_web.repository.PlayerRepository;
import vn.ptit.project.epl_web.repository.TransferHistoryRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class TransferHistoryService {
    private final TransferHistoryRepository repository;
    private final ModelMapper mapper;
    private final PlayerRepository playerRepository;
    private final ClubRepository clubRepository;
    private final LeagueSeasonRepository leagueSeasonRepository;

    public TransferHistoryService(TransferHistoryRepository repository, ModelMapper mapper, PlayerRepository playerRepository, ClubRepository clubRepository, LeagueSeasonRepository leagueSeasonRepository)
    {
        this.repository = repository;
        this.mapper = mapper;
        this.playerRepository = playerRepository;
        this.clubRepository = clubRepository;
        this.leagueSeasonRepository = leagueSeasonRepository;
    }
    public TransferHistory createTransferHistory(RequestCreateTransferHistoryDTO dto) throws InvalidRequestException {
        String transferType = dto.getType();
        if (transferType.equals("Free Transfer") || transferType.equals("End of loan") ||
                transferType.equals("Youth Promote") || transferType.equals("End of contract")) {
            dto.setFee(0f);
        }
        TransferHistory th = this.requestCreateTransferHistoryDTOtoTransferHistory(dto);
        return this.repository.save(th);
    }
    public ResponseCreateTransferHistoryDTO transferHistoryToResponseCreateTransferHistoryDTO(TransferHistory th) {
        ResponseCreateTransferHistoryDTO dto = this.mapper.map(th, ResponseCreateTransferHistoryDTO.class);
        dto.setPlayer(th.getPlayer().getName());
        if (th.getClub() != null) {
            dto.setClub(th.getClub().getName());
        } else {
            dto.setClub("Without club");
        }
        return dto;
    }
    public TransferHistory requestCreateTransferHistoryDTOtoTransferHistory(RequestCreateTransferHistoryDTO thDTO) throws InvalidRequestException {
        TransferHistory transferHistory = this.mapper.map(thDTO, TransferHistory.class);
        Long playerId = thDTO.getPlayer();
        Long clubId = thDTO.getClub();
        Optional<Player> player = this.playerRepository.findById(playerId);
        Optional<Club> club = Optional.empty();
        if (clubId != null) {
            club = this.clubRepository.findById(clubId);
        }
//        Optional<Club> club = this.clubRepository.findById(clubId);
        return this.mapPlayerAndClub(player, club, transferHistory, playerId, clubId);
    }
    public TransferHistory mapPlayerAndClub(Optional<Player> player, Optional<Club> club, TransferHistory transferHistory, Long playerId, Long clubId) throws InvalidRequestException {
        if (player.isPresent()) {
            transferHistory.setPlayer(player.get());
        } else {
            throw new InvalidRequestException("Player with id = " + playerId + " not found.");
        }
        String transferType = transferHistory.getType();
        boolean isSpecialTransfer = "End of contract".equals(transferType) ||
                "Retired".equals(transferType) ||
                "Contract terminated".equals(transferType);
        if (clubId == null && isSpecialTransfer ) {
            transferHistory.setClub(null);
        } else if (club.isPresent()) {
            transferHistory.setClub(club.get());
        } else {
        throw new InvalidRequestException("Club with id = " + clubId + " not found.");
    }

        return transferHistory;
    }
    public TransferHistory requestUpdateTransferHistoryDTOtoTransferHistory(RequestUpdateTransferHistoryDTO thDTO) throws InvalidRequestException {
        TransferHistory transferHistory = this.mapper.map(thDTO, TransferHistory.class);
        transferHistory.setId(thDTO.getId());
        Long playerId = thDTO.getPlayer();
        Long clubId = thDTO.getClub();
        Optional<Player> player = this.playerRepository.findById(playerId);
        Optional<Club> club = Optional.empty();
        if (clubId != null) {
            club = this.clubRepository.findById(clubId);
        }
        return this.mapPlayerAndClub(player, club, transferHistory, playerId, clubId);
    }
    public TransferHistory handleUpdateTransferHistory(TransferHistory th) {
        return this.repository.save(th);
    }

    public void handleDeleteTransferHistory(Long id) throws InvalidRequestException {
        Optional<TransferHistory> th = this.repository.findById(id);
        if (th.isEmpty()) {
            throw new InvalidRequestException("Transfer History with id = " + id + " not found");
        }
        TransferHistory transferHistory = th.get();
        transferHistory.setClub(null);
        transferHistory.setPlayer(null);
        this.repository.delete(transferHistory);
    }
    public List<ResponseCreateTransferHistoryDTO> getAllTransfersByClubAndSeason(Long clubId, Long seasonId) {
        LeagueSeason season = this.leagueSeasonRepository.findById(seasonId)
            .orElseThrow(() -> new EntityNotFoundException("Season not found"));
        LocalDate seasonStart = season.getStartDate();
        LocalDate from = seasonStart.minusMonths(2);
        LocalDate to = season.getEndDate();

        List<TransferHistory> inTransfers = repository.findAllTransfersByClubAndSeason(clubId, seasonId);
        List<TransferHistory> outTransfers = repository.findTransferOutsByClubAndPeriod(clubId, seasonStart, from, to);

        // Gộp 2 danh sách, loại bỏ trùng theo id, và sắp xếp theo date DESC
        List<TransferHistory> allTransfers = Stream.concat(inTransfers.stream(), outTransfers.stream())
            .collect(Collectors.collectingAndThen(
                Collectors.toMap(TransferHistory::getId, th -> th, (th1, th2) -> th1),
                m -> {
                    List<TransferHistory> list = new ArrayList<>(m.values());
                    list.sort(Comparator.comparing(TransferHistory::getDate).reversed());
                    return list;
                }
            ));

        // Tạo map lịch sử đầy đủ theo player
        Set<Long> playerIds = allTransfers.stream()
            .map(th -> th.getPlayer().getId())
            .collect(Collectors.toSet());

        Map<Long, List<TransferHistory>> completeHistoryByPlayer = new HashMap<>();
        for (Long pid : playerIds) {
            playerRepository.findById(pid)
                .map(Player::getTransferHistories)
                .ifPresent(hist -> completeHistoryByPlayer.put(pid, hist));
        }

        // Map sang DTO và xác định previousClub
        List<ResponseCreateTransferHistoryDTO> result = new ArrayList<>();
        for (TransferHistory th : allTransfers) {
            ResponseCreateTransferHistoryDTO dto = transferHistoryToResponseCreateTransferHistoryDTO(th);
            String prev = findPreviousClub(th, completeHistoryByPlayer.get(th.getPlayer().getId()));
            dto.setPreviousClub(prev);
            result.add(dto);
        }
        return result;
    }

    /**
     * Find the previous club for a transfer by looking at the player's complete transfer history
     */
    private String findPreviousClub(TransferHistory currentTransfer, List<TransferHistory> completeHistory) {
        if (completeHistory == null || completeHistory.isEmpty()) {
            return null;
        }

        // Sort all transfers by date
        List<TransferHistory> sortedHistory = completeHistory.stream()
                .sorted(Comparator.comparing(TransferHistory::getDate))
                .toList();

        // Find the transfer immediately before the current one
        TransferHistory previousTransfer = null;
        for (TransferHistory th : sortedHistory) {
            // If we've found current transfer, break the loop
            if (th.getId().equals(currentTransfer.getId())) {
                break;
            }
            previousTransfer = th;
        }

        // If we found a previous transfer, return its club name
        if (previousTransfer != null && previousTransfer.getClub() != null) {
            return previousTransfer.getClub().getName();
        }

        return null;
    }

}

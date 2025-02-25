package vn.ptit.project.epl_web.service;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.domain.TransferHistory;
import vn.ptit.project.epl_web.dto.request.player.RequestCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.transferhistory.RequestCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.transferhistory.ResponseCreateTransferHistoryDTO;
import vn.ptit.project.epl_web.repository.PlayerRepository;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final ModelMapper mapper;
    private final TransferHistoryService transferHistoryService;
    public PlayerService(PlayerRepository playerRepository, ModelMapper mapper, TransferHistoryService transferHistoryService) {
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.transferHistoryService = transferHistoryService;
    }

    public Player handleCreatePlayer(Player player) {
        return this.playerRepository.save(player);
    }

    public Player requestPlayerDTOtoPlayer(RequestCreatePlayerDTO playerDTO) {
        return this.mapper.map(playerDTO, Player.class);
    }
    public ResponseCreatePlayerDTO playerToResponseCreatePlayerDTO(Player player) {
        return this.mapper.map(player, ResponseCreatePlayerDTO.class);
    }
    public ResponseUpdatePlayerDTO playerToResponseUpdatePlayerDTO(Player player) {
        return this.mapper.map(player, ResponseUpdatePlayerDTO.class);
    }
    public Player handleUpdatePlayer(Player player, RequestUpdatePlayerDTO playerDTO) throws InvalidRequestException {
        this.mapper.map(playerDTO, player);
        //handle transfer history
        if (playerDTO.getTransferHistories() != null) {
            //TODO: implement transfer history created and query to db
            // lấy transferhistory dto từ cầu thủ (là thuộc tính cua cau thu)
            List<RequestCreateTransferHistoryDTO> transferHistories = new ArrayList<>(playerDTO.getTransferHistories());
            // tao moi list cua transferhistory tao ra
            List<TransferHistory> createdTransferHistories = new ArrayList<>();
            // tao transferhistory
            for (RequestCreateTransferHistoryDTO dto : transferHistories) {
                createdTransferHistories.add(this.transferHistoryService.createTransferHistory(dto));
            }
//            player.setTransferHistories(new HashSet<>(createdTransferHistories));
        }
//        System.out.println(player);
        return this.playerRepository.save(player);
    }
    @Transactional
    public Optional<Player> getPlayerById(Long id) {
        return this.playerRepository.findById(id);
    }


    public ResultPaginationDTO fetchAllPlayers(Specification<Player> spec, Pageable pageable) {
        Page<Player> pagePlayer = this.playerRepository.findAll(pageable);
        ResultPaginationDTO result = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pagePlayer.getTotalPages());
        meta.setTotal(pagePlayer.getTotalElements());
        result.setMeta(meta);
        List<ResponsePlayerDTO> list = pagePlayer.getContent().stream()
                .map(this::playerToResponsePlayerDTO)
                .collect(Collectors.toList());
        result.setResult(list);
        return result;
    }

    @Transactional
    public ResponsePlayerDTO playerToResponsePlayerDTO(Player player) {
        List<TransferHistory> transferHistoryList = player.getTransferHistories();
        ResponsePlayerDTO playerDTO = this.mapper.map(player, ResponsePlayerDTO.class);
        List<ResponseCreateTransferHistoryDTO> transferHistories = new ArrayList<>();
        for (TransferHistory th : transferHistoryList) {
            ResponseCreateTransferHistoryDTO newThDTO = this.transferHistoryService.transferHistoryToResponseCreateTransferHistoryDTO(th);
            transferHistories.add(newThDTO);
        }
        playerDTO.setTransferHistories(transferHistories);
        return playerDTO;
    }
    public void handleDeletePyer(Long id) {
        Optional<Player> player = this.playerRepository.findById(id);
        if (player.isPresent()) {
            Player deletedPlayer = player.get();
            //TODO delete all club related


        }


        this.playerRepository.deleteById(id);
    }

}
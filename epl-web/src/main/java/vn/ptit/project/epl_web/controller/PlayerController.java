package vn.ptit.project.epl_web.controller;


import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.project.epl_web.domain.Player;
import vn.ptit.project.epl_web.dto.request.player.RequestCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.request.player.RequestUpdatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseCreatePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponsePlayerDTO;
import vn.ptit.project.epl_web.dto.response.player.ResponseUpdatePlayerDTO;
import vn.ptit.project.epl_web.service.FileStorageService;
import vn.ptit.project.epl_web.service.PlayerService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final PlayerService playerService;
    private final FileStorageService fileStorageService;

    public PlayerController(PlayerService playerService, FileStorageService fileStorageService) {
        this.playerService = playerService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    @ApiMessage("Create player")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseCreatePlayerDTO> createPlayer(
            @Valid @RequestPart("data") RequestCreatePlayerDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {

        Player player = playerService.requestPlayerDTOtoPlayer(dto);

        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(imageFile, "player");
            player.setImagePath(imagePath);
        }

        player = playerService.handleCreatePlayer(player);
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.playerToResponseCreatePlayerDTO(player));
    }

    @PutMapping("/{id}")
    @ApiMessage("Update player")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseUpdatePlayerDTO> updatePlayer(
            @PathVariable Long id,
            @Valid @RequestPart("data") RequestUpdatePlayerDTO dto,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws InvalidRequestException {

        Optional<Player> player = playerService.getPlayerById(id);
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + id + " not found");
        }

        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Delete old image if exists
            if (player.get().getImagePath() != null) {
                fileStorageService.deleteImage(player.get().getImagePath());
            }

            // Store new image
            String imagePath = fileStorageService.storeImage(imageFile, "player");
            player.get().setImagePath(imagePath);
        }

        Player updatedPlayer = playerService.handleUpdatePlayer(player.get(), dto);
        return ResponseEntity.ok(playerService.playerToResponseUpdatePlayerDTO(updatedPlayer));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch a player")
    public ResponseEntity<ResponsePlayerDTO> fetchAPlayer
            (@PathVariable Long id,
             @RequestParam(required = false, defaultValue = "false") boolean sortTransferHistory
    ) throws InvalidRequestException {
        Optional<Player> player = this.playerService.getPlayerById(id);
        if (player.isEmpty()) {
            throw new InvalidRequestException("Player with id = " + id + " not found.");
        }
        if (sortTransferHistory) {
            return ResponseEntity.ok(this.playerService.playerToResponsePlayerDTOWithSortedTransferHistory(player.get()));
        }
        return ResponseEntity.ok(this.playerService.playerToResponsePlayerDTO(player.get()));
    }


    @GetMapping("")
    @ApiMessage("Fetch all players")
    public ResponseEntity<ResultPaginationDTO> fetchAllPlayers(
            @Filter Specification<Player> spec,
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "false") boolean sortTransferHistory
    ) {
        if (sortTransferHistory) {
            return ResponseEntity.ok(this.playerService.fetchAllPlayersWithSortedTransferHistories(spec, pageable));
        }
        return ResponseEntity.ok(this.playerService.fetchAllPlayers(spec, pageable));
    }

    @DeleteMapping("/{id}")
    @ApiMessage("Delete a player")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteAPlayer(@PathVariable Long id) throws InvalidRequestException {
        this.playerService.handleDeletePlayer(id);
        return ResponseEntity.ok(null);
    }

}
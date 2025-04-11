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
import vn.ptit.project.epl_web.domain.League;
import vn.ptit.project.epl_web.dto.request.league.RequestCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.request.league.RequestUpdateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.club.ClubWinDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseCreateLeagueDTO;
import vn.ptit.project.epl_web.dto.response.league.ResponseUpdateLeagueDTO;
import vn.ptit.project.epl_web.service.FileStorageService;
import vn.ptit.project.epl_web.service.LeagueService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.List;

@RestController
@RequestMapping("api/v1/leagues")
public class LeagueController {
    private final LeagueService leagueService;
    private final FileStorageService fileStorageService;

    public LeagueController(LeagueService leagueService, FileStorageService fileStorageService) {
        this.leagueService = leagueService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    @ApiMessage("Create new league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateLeagueDTO> createNewLeague(
            @Valid @RequestPart("data") RequestCreateLeagueDTO leagueDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        
        League league = leagueService.requestLeagueDTOtoLeague(leagueDTO);
        
        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(imageFile, "league");
            league.setImagePath(imagePath);
        }
        
        League newLeague = leagueService.handleCreateLeague(league);
        return ResponseEntity.status(HttpStatus.CREATED).body(leagueService.leagueToResponseCreateLeagueDTO(newLeague));
    }
    
    @PutMapping("/{id}")
    @ApiMessage("Update a league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateLeagueDTO> updateLeague(
            @PathVariable("id") Long id,
            @Valid @RequestPart("data") RequestUpdateLeagueDTO leagueDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws InvalidRequestException {
        
        League league = leagueService.findByLeagueId(id);
        if (league == null) {
            throw new InvalidRequestException("League with id = " + id + " not found.");
        }
        
        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Delete old image if exists
            if (league.getImagePath() != null) {
                fileStorageService.deleteImage(league.getImagePath());
            }
            
            // Store new image
            String imagePath = fileStorageService.storeImage(imageFile, "league");
            league.setImagePath(imagePath);
        }
        
        League updatedLeague = this.leagueService.handleUpdateLeague(league, leagueDTO);
        return ResponseEntity.ok().body(this.leagueService.leagueToResponseUpdateLeagueDTO(updatedLeague));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch a league")
    public ResponseEntity<ResponseUpdateLeagueDTO> findLeagueById(@PathVariable("id") Long id) throws InvalidRequestException {
        League league=this.leagueService.findByLeagueId(id);
        if (league==null) {
            throw new InvalidRequestException("League with id = " + id + " not found");
        }
        return ResponseEntity.ok().body(leagueService.leagueToResponseUpdateLeagueDTO(league));
    }
    @GetMapping("")
    @ApiMessage("fetch all leagues")
    public ResponseEntity<ResultPaginationDTO> fetchAllLeagues(@Filter Specification<League> spec, Pageable pageable) {
        return ResponseEntity.ok(this.leagueService.fetchAllLeagues(spec,pageable));
    }
    @DeleteMapping("{id}")
    @ApiMessage("Delete a league")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteLeague(@PathVariable("id") Long leagueId) throws InvalidRequestException {
            if(leagueService.findByLeagueId(leagueId)==null) {
                throw new InvalidRequestException("League with id = " + leagueId + " not found.");
            }
            this.leagueService.deleteLeague(leagueId);
            return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}/top-clubs-win")
    @ApiMessage("get top clubs that win the league most")
    public ResponseEntity<List<ClubWinDTO>> getTopClubWins(@PathVariable("id") Long leagueId) throws InvalidRequestException {
        List<ClubWinDTO> clubWinDTOS=leagueService.clubTables(leagueId);
        return ResponseEntity.ok(clubWinDTOS);
    }
}

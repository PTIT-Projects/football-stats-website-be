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
import vn.ptit.project.epl_web.domain.HeadCoach;
import vn.ptit.project.epl_web.dto.request.coach.RequestCreateCoachDTO;
import vn.ptit.project.epl_web.dto.request.coach.RequestUpdateCoachDTO;
import vn.ptit.project.epl_web.dto.response.ResultPaginationDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseCreateCoachDTO;
import vn.ptit.project.epl_web.dto.response.coach.ResponseUpdateCoachDTO;
import vn.ptit.project.epl_web.service.CoachService;
import vn.ptit.project.epl_web.service.FileStorageService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.exception.InvalidRequestException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/coaches")
public class CoachController {
    private final CoachService coachService;
    private final FileStorageService fileStorageService;
    
    public CoachController(CoachService coachService, FileStorageService fileStorageService) {
        this.coachService = coachService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("")
    @ApiMessage("Create a new head coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseCreateCoachDTO> createNewCoach(
            @Valid @RequestPart("data") RequestCreateCoachDTO coachDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        
        HeadCoach coach = this.coachService.requestCreateCoachDTOtoCoach(coachDTO);
        
        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = fileStorageService.storeImage(imageFile, "coach");
            coach.setImagePath(imagePath);
        }
        
        HeadCoach newCoach = this.coachService.handleCreateCoach(coach);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.coachService.coachToResponseCreateCoachDTO(newCoach));
    }

    @PutMapping("/{id}")
    @ApiMessage("Update a coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ResponseUpdateCoachDTO> updateACoach(
            @PathVariable Long id,
            @Valid @RequestPart("data") RequestUpdateCoachDTO coachDTO,
            @RequestPart(value = "image", required = false) MultipartFile imageFile) throws InvalidRequestException {
        
        Optional<HeadCoach> optCoach = this.coachService.getCoachById(id);
        if (optCoach.isEmpty()) {
            throw new InvalidRequestException("Coach with id = " + id + " not found");
        }
        
        HeadCoach coach = optCoach.get();
        
        // Handle image upload if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            // Delete old image if exists
            if (coach.getImagePath() != null) {
                fileStorageService.deleteImage(coach.getImagePath());
            }
            
            // Store new image
            String imagePath = fileStorageService.storeImage(imageFile, "coach");
            coach.setImagePath(imagePath);
        }
        
        HeadCoach updatedCoach = this.coachService.handleUpdateCoach(coach, coachDTO);
        return ResponseEntity.ok(this.coachService.coachToResponseUpdateCoachDTO(updatedCoach));
    }

    @GetMapping("/{id}")
    @ApiMessage("Fetch a coach")
    public ResponseEntity<ResponseCoachDTO> fetchACoach(@PathVariable Long id,
                                                        @RequestParam(required = false, defaultValue = "false") boolean sortCoachClubs)
            throws InvalidRequestException {
        Optional<HeadCoach> coach = this.coachService.getCoachById(id);
        if (coach.isEmpty()) {
            throw new InvalidRequestException("Coach with id = " + id + " not found");
        }
        if (sortCoachClubs) {
            return ResponseEntity.ok(this.coachService.coachToResponseCoachWithSortedTransferHistory(coach.get()));
        }
        return ResponseEntity.ok(coachService.coachToResponseCoachDTO(coach.get()));
    }
    @GetMapping("")
    @ApiMessage("Fetch all coaches")
    public ResponseEntity<ResultPaginationDTO> fetchAllCoaches(
            @Filter Specification<HeadCoach> spec,
            Pageable pageable
    ) {
        return ResponseEntity.ok(this.coachService.fetchAllCoaches(spec, pageable));
    }
    @DeleteMapping("/{id}")
    @ApiMessage("Delete a coach")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteACoach(@PathVariable Long id ) throws InvalidRequestException {
        this.coachService.handleDeleteCoach(id);
        return ResponseEntity.ok(null);
    }
}

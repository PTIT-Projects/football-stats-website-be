package vn.ptit.project.epl_web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.project.epl_web.dto.response.file.ResUploadFileDTO;
import vn.ptit.project.epl_web.service.FileService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;
import vn.ptit.project.epl_web.util.error.StorageException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    @ApiMessage("Upload single file")
    public ResponseEntity<ResUploadFileDTO> upload(
            @RequestParam(name = "file", required = false) MultipartFile file,
            @RequestParam("folder") String folder) throws URISyntaxException, IOException, StorageException {

        // Validate file
        if (file == null || file.isEmpty()) {
            throw new StorageException("File is empty. Please upload a file");
        }

        String fileName = file.getOriginalFilename();
        List<String> allowedExtensions = Arrays.asList("pdf", "jpg", "jpeg", "png", "doc", "docx");
        boolean isValid = allowedExtensions.stream().anyMatch(item -> fileName.toLowerCase().endsWith(item));
        if (!isValid) {
            throw new StorageException("Invalid file extension. Only allows " + allowedExtensions.toString());
        }

        // Store file in Cloudinary
        String cloudinaryUrl = this.fileService.store(file, folder);

        // Create response with the Cloudinary URL
        ResUploadFileDTO res = new ResUploadFileDTO(cloudinaryUrl, Instant.now());

        return ResponseEntity.ok().body(res);
    }
}
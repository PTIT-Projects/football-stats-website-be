package vn.ptit.project.epl_web.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ptit.project.epl_web.service.FileStorageService;
import vn.ptit.project.epl_web.util.annotation.ApiMessage;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final FileStorageService fileStorageService;

    public ImageController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @GetMapping("/{fileName:.+}")
    @ApiMessage("Fetch an image")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        // Load file as Resource
        Resource resource = fileStorageService.loadImageAsResource(fileName);

        // Try to determine file's content type
        String contentType;
        try {
            contentType = resource.getURL().openConnection().getContentType();
        } catch (IOException ex) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
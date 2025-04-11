package vn.ptit.project.epl_web.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.project.epl_web.util.exception.FileStorageException;
import vn.ptit.project.epl_web.util.exception.ResourceNotFoundException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Path publicImagesLocation;
    
    @Value("${file.image-base-url}")
    private String imageBaseUrl;

    public FileStorageService(
            @Value("${file.upload-dir}") String uploadDir,
            @Value("${file.public-images-dir}") String publicImagesDir) {
        
        // Create the file storage directory if it doesn't exist
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.publicImagesLocation = Paths.get(publicImagesDir).toAbsolutePath().normalize();
        
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.publicImagesLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    /**
     * Store an image file and return its relative path
     */
    public String storeImage(MultipartFile file, String entityType) {
        // Normalize file name and add UUID to prevent name collisions
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        
        // Check for invalid file names
        if (originalFileName.contains("..")) {
            throw new FileStorageException("Filename contains invalid path sequence: " + originalFileName);
        }

        // Get file extension
        String fileExtension = getFileExtension(originalFileName);
        
        // Create a unique filename with UUID
        String fileName = entityType + "_" + UUID.randomUUID() + fileExtension;
        
        // Define the target location for the file
        Path targetLocation = this.publicImagesLocation.resolve(fileName);
        
        try {
            // Copy the file to the target location, replacing existing file with the same name if it exists
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            
            // Return the relative path which can be stored in the database
            return imageBaseUrl + fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    
    /**
     * Retrieve an image file as a Resource
     */
    public Resource loadImageAsResource(String fileName) {
        try {
            Path filePath = this.publicImagesLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResourceNotFoundException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new ResourceNotFoundException("File not found: " + fileName, ex);
        }
    }
    
    /**
     * Delete an image file
     */
    public void deleteImage(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return;
        }
        
        // Extract filename from the image path
        String fileName = imagePath.substring(imagePath.lastIndexOf('/') + 1);
        
        try {
            Path file = this.publicImagesLocation.resolve(fileName).normalize();
            Files.deleteIfExists(file);
        } catch (IOException ex) {
            // Log but don't throw, since this is typically done on update operations
            System.err.println("Could not delete file: " + fileName);
        }
    }
    
    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}
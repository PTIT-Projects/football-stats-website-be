package vn.ptit.project.epl_web.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.ptit.project.epl_web.util.error.StorageException;

@Service
public class FileService {

    private final Cloudinary cloudinary;

    public FileService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String store(MultipartFile file, String folder) throws URISyntaxException,
            IOException, StorageException {
        if (file == null || file.isEmpty()) {
            throw new StorageException("File is empty");
        }
        try {
            // Generate a unique filename
            String originalFilename = file.getOriginalFilename();
            String uniqueFileName = System.currentTimeMillis() + "-" + originalFilename;

            // Upload to cloudinary
            Map params = ObjectUtils.asMap(
                    "public_id", folder + "/" + uniqueFileName.substring(0, uniqueFileName.lastIndexOf('.')),
                    "overwrite", true,
                    "resource_type", "auto"
            );

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params);

            // Return the secure URL or public ID as needed
            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new StorageException("Failed to store file in Cloudinary");
        }
    }
}
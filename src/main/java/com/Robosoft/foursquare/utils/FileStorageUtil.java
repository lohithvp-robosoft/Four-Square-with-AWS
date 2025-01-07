package com.Robosoft.foursquare.utils;

import com.Robosoft.foursquare.config.AwsConfig;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileStorageUtil {
    private final Path uploadDirectory = Path.of("uploads");

    @Autowired
    private AwsConfig awsConfig; // Get AWS config which contains S3 client and bucket name

    @Autowired
    private S3Client s3Client;

    private String s3Folder = "hotel-images/";

    public String storeFile(MultipartFile file, Long hotelId) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        // Generate a unique file name with hotel ID and the file extension
        String fileName = hotelId + "-hotel-Image-" + UUID.randomUUID() + fileExtension;
        String s3Key = s3Folder + fileName;

        // Determine content type based on the file extension
        String contentType = "application/octet-stream"; // Default type
        if (originalFileName != null) {
            if (fileExtension.equalsIgnoreCase(".jpg") || fileExtension.equalsIgnoreCase(".jpeg")) {
                contentType = "image/jpeg";
            } else if (fileExtension.equalsIgnoreCase(".png")) {
                contentType = "image/png";
            } else if (fileExtension.equalsIgnoreCase(".gif")) {
                contentType = "image/gif";
            } else if (fileExtension.equalsIgnoreCase(".bmp")) {
                contentType = "image/bmp";
            } else if (fileExtension.equalsIgnoreCase(".webp")) {
                contentType = "image/webp";
            }
        }

        // Upload the file with the correct content type
        try (InputStream inputStream = file.getInputStream()) {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(awsConfig.bucketName())
                    .key(s3Key)
                    .contentType(contentType) // Set content type here
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
        }

        // Generate the URL of the uploaded file (assuming the bucket is public)
        String fileUrl = "https://" + awsConfig.bucketName() + ".s3." + awsConfig.getAwsRegion() + ".amazonaws.com/" + s3Key;

        return fileUrl;
    }



//    // Method to retrieve the file from S3
//    public Resource retrieveFile(String fileName) throws IOException {
//        // Construct the full S3 key for the file
//        String s3Key = s3Folder + fileName;
//
//        // Create a GetObjectRequest to retrieve the file
//        software.amazon.awssdk.services.s3.model.GetObjectRequest getObjectRequest = software.amazon.awssdk.services.s3.model.GetObjectRequest.builder()
//                .bucket(awsConfig.bucketName()) // Bucket name from config
//                .key(s3Key) // The S3 key (path)
//                .build();
//
//        // Retrieve the object as an InputStream
//        InputStream s3InputStream = s3Client.getObject(getObjectRequest);
//
//        // Return as InputStreamResource
//        return new InputStreamResource(s3InputStream);
//    }
}

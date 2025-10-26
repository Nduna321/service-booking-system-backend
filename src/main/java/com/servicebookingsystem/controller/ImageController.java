package com.servicebookingsystem.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@CrossOrigin
public class ImageController {

    @GetMapping("/image/{adId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long adId){

        byte[] image=getImageFromID(adId);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Change to PNG if needed

        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


    private byte[] getImageFromID(long id){

        //String uploadDir = System.getProperty("user.dir") + "/uploads";

        //System.out.println(id);

        Path imagePath= Paths.get("uploads",id+".jpg");

        if (!Files.exists(imagePath)) {
            System.out.println("File not found");
        }

        // Read and return file content as byte array
        try {
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

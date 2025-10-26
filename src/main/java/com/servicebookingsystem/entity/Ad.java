package com.servicebookingsystem.entity;

import com.servicebookingsystem.dto.AdDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Entity
@Table(name = "ads")
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String serviceName;

    private String description;

    private Double price;

//    @Lob
//    @Column(columnDefinition = "longblob")
//    private byte[] img;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public AdDTO getAdDto(){
        AdDTO adDTO = new AdDTO();

        adDTO.setId(id);
        adDTO.setServiceName(serviceName);
        adDTO.setDescription(description);
        adDTO.setPrice(price);
        adDTO.setCompanyName(user.getName());
        adDTO.setReturnedImg(getImageFromID(id));

        return adDTO;
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

    public void setImg(byte[] fileBytes,long id) throws IOException {
        // Ensure uploads folder exists
        Path uploadPath = Paths.get("uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Build full file path
        Path imagePath= Paths.get("uploads",id+".jpg");

        // Write the bytes to the file
        Files.write(imagePath, fileBytes);


    }


}














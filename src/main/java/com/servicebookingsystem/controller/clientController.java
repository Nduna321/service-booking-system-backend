package com.servicebookingsystem.controller;

import com.servicebookingsystem.dto.AdDTO;
import com.servicebookingsystem.dto.ReservationDTO;
import com.servicebookingsystem.dto.ReviewDTO;
import com.servicebookingsystem.entity.Ad;
import com.servicebookingsystem.services.client.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/client")
@CrossOrigin
public class clientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/ads")
    public ResponseEntity<?> getAllAds() {
        try {
            List<AdDTO> ads = clientService.getAllAds();
            return ResponseEntity.ok(ads);
        } catch (Exception e) {
            // Logs the exact error in Railway logs
            log.error("Error fetching ads", e);
            // Return a clear message to the frontend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Failed to fetch ads"));
        }
    }


//    @GetMapping("/ads")
//    public ResponseEntity<?> getAllAds(){
//        return ResponseEntity.ok(clientService.getAllAds());
//    }


    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchAdByService(@PathVariable String name){
        return ResponseEntity.ok(clientService.searchAdByName(name));
    }

    @PostMapping("/book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO){
        boolean success = clientService.bookService(reservationDTO);
        if(success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else{
         return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdDetailsByAdId(@PathVariable Long adId){
        return ResponseEntity.ok(clientService.getAdDetailsById(adId));
    }


    @GetMapping("/my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId){
        return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO){
        Boolean success = clientService.giveReview(reviewDTO);

        if (success){
            return ResponseEntity.status(HttpStatus.OK).build();
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

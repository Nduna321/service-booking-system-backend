package com.servicebookingsystem.services.client;

import com.servicebookingsystem.dto.AdDTO;
import com.servicebookingsystem.dto.AdDetailsForClientDTO;
import com.servicebookingsystem.dto.ReservationDTO;
import com.servicebookingsystem.dto.ReviewDTO;

import java.util.List;

public interface ClientService {

    public List<AdDTO> getAllAds();

    public List<AdDTO> searchAdByName(String name);

    boolean bookService(ReservationDTO reservationDTO);

    AdDetailsForClientDTO getAdDetailsById(Long adId);

    List<ReservationDTO> getAllBookingsByUserId(Long userId);

    Boolean giveReview(ReviewDTO reviewDTO);
}

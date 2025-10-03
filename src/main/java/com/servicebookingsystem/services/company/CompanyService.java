package com.servicebookingsystem.services.company;


import com.servicebookingsystem.dto.AdDTO;
import com.servicebookingsystem.dto.ReservationDTO;

import java.io.IOException;
import java.util.List;

public interface CompanyService {

    boolean postAd(Long userId, AdDTO adDTO) throws IOException;

    List<AdDTO> getAllAds(Long userId);

    List<ReservationDTO> getAllAdBookings(Long companyId);

    boolean changeBookingStatus(Long bookingId, String Status);
}

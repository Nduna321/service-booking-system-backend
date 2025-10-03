package com.servicebookingsystem.services.company;

import com.servicebookingsystem.dto.AdDTO;
import com.servicebookingsystem.dto.ReservationDTO;
import com.servicebookingsystem.entity.Ad;
import com.servicebookingsystem.entity.Reservation;
import com.servicebookingsystem.entity.User;
import com.servicebookingsystem.enums.ReservationStatus;
import com.servicebookingsystem.repository.AdRepository;
import com.servicebookingsystem.repository.ReservationRepository;
import com.servicebookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdRepository adRepository;

    @Autowired
    ReservationRepository reservationRepository;


    public boolean postAd(Long userId, AdDTO adDTO) throws IOException {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            Ad ad = new Ad();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setImg(adDTO.getImg().getBytes());
            ad.setPrice(adDTO.getPrice());
            ad.setUser(optionalUser.get());


            adRepository.save(ad);
            return true;
        }
        return false;
    }


    public List<AdDTO> getAllAds(Long userId) {
        return adRepository.findAllByUserId(userId).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }


    public List<ReservationDTO> getAllAdBookings(Long companyId) {
        return reservationRepository.findAllByCompanyId(companyId)
                .stream().map(Reservation::getReservationDTO).collect(Collectors.toList());
    }

    public boolean changeBookingStatus(Long bookingId, String Status) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(bookingId);
        if (optionalReservation.isPresent()) {
            Reservation existingReservation = optionalReservation.get();
            if(Objects.equals(Status, "Approve" )){
                existingReservation.setReservationStatus(ReservationStatus.APPROVED);
            }else{
                existingReservation.setReservationStatus(ReservationStatus.REJECTED);
            }

            reservationRepository.save(existingReservation);

            return true;
        }
        return false;
    }

}


















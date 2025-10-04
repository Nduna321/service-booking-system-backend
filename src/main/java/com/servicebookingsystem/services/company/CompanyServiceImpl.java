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

    @Override
    public AdDTO getAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            return optionalAd.get().getAdDto();  // Assuming your Ad entity has getAdDto() to convert to DTO
        }
        return null; // or throw an exception if you prefer
    }

    @Override
    public boolean deleteAdById(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            adRepository.delete(optionalAd.get());
            return true; // successfully deleted
        }
        return false; // ad not found
    }

    @Override
    public boolean updateAd(Long adId, AdDTO adDTO) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        if (optionalAd.isPresent()) {
            Ad existingAd = optionalAd.get();

            existingAd.setServiceName(adDTO.getServiceName());
            existingAd.setDescription(adDTO.getDescription());
            if (adDTO.getImg() != null && !adDTO.getImg().isEmpty()) {
                try {
                    existingAd.setImg(adDTO.getImg().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                    return false; // fail if image can't be processed
                }
            }
            existingAd.setPrice(adDTO.getPrice());

            adRepository.save(existingAd);
            return true;
        }
        return false;
    }

}










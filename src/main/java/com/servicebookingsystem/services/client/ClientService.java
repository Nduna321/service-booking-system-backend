package com.servicebookingsystem.services.client;

import com.servicebookingsystem.dto.AdDTO;

import java.util.List;

public interface ClientService {

    public List<AdDTO> getAllAds();

    public List<AdDTO> searchAdByName(String name);
}

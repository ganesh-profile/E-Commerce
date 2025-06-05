package com.E_CommerceApplication.App.service;

import java.util.List;

import com.E_CommerceApplication.App.DTOs.AddressDTO;
import com.E_CommerceApplication.App.models.Address;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> getAddresses();

    AddressDTO getAddressDTO(Long addressId);

    AddressDTO updateAddress(Long AddressId, Address address);

    String deleteAddress(Long addressId);

}

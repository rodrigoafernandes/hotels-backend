package br.com.gigiodesenvolvimento.hotelsbackend.broker.client.impl;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;
import static org.apache.commons.lang3.math.NumberUtils.createLong;

import java.util.ArrayList;
import java.util.List;

import br.com.gigiodesenvolvimento.hotelsbackend.broker.client.BrokerHotelWSClient;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.HotelDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.PriceDTO;
import br.com.gigiodesenvolvimento.hotelsbackend.dto.RoomDTO;

public class BrokerHotelWSClientImpl implements BrokerHotelWSClient {

    private List<HotelDTO> hotels = new ArrayList<>();

    public BrokerHotelWSClientImpl() {
        hotels.addAll(
                asList(HotelDTO
                        .builder().id(LONG_ONE).name("Hotel Teste 1").cityCode(createLong(
                                "1032"))
                        .cityName("Porto Seguro").rooms(
                                asList(RoomDTO
                                        .builder().roomID(LONG_ONE).categoryName("Standard").price(PriceDTO.builder()
                                                .adult(createBigDecimal("1372.54")).child(
                                                        createBigDecimal("848.61"))
                                                .build())
                                        .build()))
                        .build(),
                        HotelDTO.builder().id(createLong("4")).name("Hotel Teste 4").cityCode(createLong("1032"))
                                .cityName("Porto Seguro")
                                .rooms(asList(
                                        RoomDTO.builder().roomID(LONG_ZERO).categoryName("Standard")
                                                .price(PriceDTO.builder().adult(createBigDecimal("341.76"))
                                                        .child(createBigDecimal("782.14")).build())
                                                .build(),
                                        RoomDTO.builder().roomID(LONG_ONE).categoryName("Luxo")
                                                .price(PriceDTO.builder().adult(createBigDecimal("483.02"))
                                                        .child(createBigDecimal("591.33")).build())
                                                .build()))
                                .build()));
    }

    @Override
    public List<HotelDTO> findHotelDetail(Long hotelCode) {
        return hotels.stream().filter(hotel -> hotel.getId().equals(hotelCode)).collect(toList());
    }

    @Override
    public List<HotelDTO> findAvailableByCity(Long cityCode) {
        return hotels.stream().filter(hotel -> hotel.getCityCode().equals(cityCode)).collect(toList());
    }

}
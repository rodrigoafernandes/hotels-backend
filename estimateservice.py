from brokerClient import HotelBrokerClient, Room
from hotelsconverter import Converter
from markup import MarkupCalculator
from requesthotelmodel import SearchDataRequest, SearchData
from responsehotelsmodel import HotelAvail, RoomAvail, PriceDetail
from typing import List


class Estimate:

    @staticmethod
    def estimate_city(search_data_request: SearchDataRequest):
        errors_constraint = []
        hotels = []
        search_data = Converter.to_search_data(search_data_request=search_data_request,
                                               errors_constraint=errors_constraint)
        availability_by_city = HotelBrokerClient.find_by_city_code(city_code=search_data.cityCode)

        for hotel in availability_by_city:
            rooms = Estimate.__available_rooms(search_data=search_data, avail_rooms=hotel['rooms'])
            hotels.append(HotelAvail(id=hotel['id'], cityName=hotel['cityName'], rooms=rooms))

        return hotels

    @staticmethod
    def __available_rooms(avail_rooms: List[Room], search_data: SearchData):
        rooms = []
        qtd_days = search_data.endDate - search_data.startDate
        for room in avail_rooms:
            room_adult_price_with_tax = MarkupCalculator.calculate(room['price']['adult'])
            room_child_price_with_tax = MarkupCalculator.calculate(room['price']['child'])
            total_price = (room_adult_price_with_tax * qtd_days.days) + (room_child_price_with_tax * qtd_days.days)
            price_detail = PriceDetail(pricePerDayAdult=room_adult_price_with_tax,
                                       pricePerDayChild=room_child_price_with_tax)
            rooms.append(RoomAvail(roomID=room['roomID'], categoryName=room['categoryName'], totalPrice=total_price,
                                   priceDetail=price_detail))

        return rooms

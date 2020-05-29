from multiprocessing import Process, Manager
from typing import List
from modules.models import HotelAvail, PriceDetail, RoomAvail
from modules.exceptions import HotelNotFound


class Estimate:

    def __init__(self, broker, converter, markup):
        self.__broker = broker
        self.__converter = converter
        self.__markup = markup
        self.__search_data = None

    def estimate_city(self, search_data_request):
        self.__search_data = None
        errors_constraint = []
        hotels = []

        self.__search_data = self.__converter.to_search_data(search_data_request=search_data_request,
                                                             errors_constraint=errors_constraint)
        availability_by_city = self.__broker.find_by_city_code(city_code=self.__search_data.cityCode)

        for hotel in availability_by_city:
            rooms = self.__available_rooms(avail_rooms=hotel['rooms'])
        hotels.append(HotelAvail(id=hotel['id'], cityName=hotel['cityName'], rooms=rooms))

        return hotels

    def estimate_hotel(self, search_data_request):
        self.__search_data = None
        errors_constraint = []
        hotels = []

        self.__search_data = self.__converter.to_search_data_hotel(search_data_request=search_data_request,
                                                                   errors_constraint=errors_constraint)
        hotel = self.__broker.find_by_hotel_code(self.__search_data.hotelCode)[0]

        if hotel['cityCode'] == self.__search_data.cityCode:
            rooms = self.__available_rooms(avail_rooms=hotel['rooms'])
            hotels.append(HotelAvail(id=hotel['id'], cityName=hotel['cityName'], rooms=rooms))

            return hotels
        else:
            raise HotelNotFound(f'Hotel com id: {self.__search_data.hotelCode} não localidado')

    def __available_rooms(self, avail_rooms):
        rooms = []
        qtd_days = self.__search_data.endDate - self.__search_data.startDate
        for room in avail_rooms:
            room_adult_price_with_tax = self.__markup.calculate(room['price']['adult'])
            room_child_price_with_tax = self.__markup.calculate(room['price']['child'])
            total_price = round(
                ((room_adult_price_with_tax * qtd_days.days) + (room_child_price_with_tax * qtd_days.days)), 4)
            price_detail = PriceDetail(pricePerDayAdult=room_adult_price_with_tax,
                                       pricePerDayChild=room_child_price_with_tax)
            rooms.append(RoomAvail(roomID=room['roomID'], categoryName=room['categoryName'], totalPrice=total_price,
                                   priceDetail=price_detail))

        return rooms

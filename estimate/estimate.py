import broker
import converter
import markup
from broker import Room
from models import HotelAvail, PriceDetail, RoomAvail, SearchDataRequest, SearchData
from typing import List


def estimate_city(search_data_request: SearchDataRequest):
    errors_constraint = []
    hotels = []
    search_data = converter.to_search_data(search_data_request=search_data_request,
                                           errors_constraint=errors_constraint)
    availability_by_city = broker.find_by_city_code(city_code=search_data.cityCode)

    for hotel in availability_by_city:
        rooms = __available_rooms(search_data=search_data, avail_rooms=hotel['rooms'])
        hotels.append(HotelAvail(id=hotel['id'], cityName=hotel['cityName'], rooms=rooms))

    return hotels


def __available_rooms(avail_rooms: List[Room], search_data: SearchData):
    rooms = []
    qtd_days = search_data.endDate - search_data.startDate
    for room in avail_rooms:
        room_adult_price_with_tax = markup.calculate(room['price']['adult'])
        room_child_price_with_tax = markup.calculate(room['price']['child'])
        total_price = (room_adult_price_with_tax * qtd_days.days) + (room_child_price_with_tax * qtd_days.days)
        price_detail = PriceDetail(pricePerDayAdult=room_adult_price_with_tax,
                                   pricePerDayChild=room_child_price_with_tax)
        rooms.append(RoomAvail(roomID=room['roomID'], categoryName=room['categoryName'], totalPrice=total_price,
                               priceDetail=price_detail))

    return rooms

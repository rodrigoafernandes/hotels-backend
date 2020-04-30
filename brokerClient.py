import json
import requests

from pydantic import BaseModel
from typing import List

broker_URL = "https://cvcbackendhotel.herokuapp.com"


class HotelBrokerClient:

    @staticmethod
    def find_by_city_code(city_code: int):
        endpoint = '{0}/hotels/avail/{1}'.format(broker_URL, city_code)
        response = requests.get(endpoint)

        if response.status_code == 200:
            data = json.loads(response.content.decode('utf-8'))
            return Hotel(**json.loads(data))
        else:
            return None

    @staticmethod
    def find_by_hotel_code(hotel_code: int):
        endpoint = '{0}/hotels/{1}'.format(broker_URL, hotel_code)
        response = requests.get(endpoint)

        if response.status_code == 200:
            data = json.loads(response.content.decode('utf-8'))
            return Hotel(**json.loads(data))
        else:
            return None


class Price(BaseModel):
    adult: float
    child: float

    def __init__(self, adult: float, child: float):
        super().__init__()
        self.adult = adult
        self.child = child


class Room(BaseModel):
    roomID: int
    categoryName: str
    price: Price

    def __init__(self, roomID: int, categoryName: str, price: Price):
        super().__init__()
        self.roomID = roomID
        self.categoryName = categoryName
        self.price = price


class Hotel(BaseModel):
    id: int
    name: str
    cityCode: int
    cityName: str
    rooms: List[Room]

    def __init__(self, id: int, name: str, cityCode: int, cityName: str, rooms: List[Room]):
        super().__init__()
        self.id = id
        self.name = name
        self.cityCode = cityCode
        self.cityName = cityName
        self.rooms = rooms

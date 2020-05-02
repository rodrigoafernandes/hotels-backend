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
            return response.json()
        else:
            return None

    @staticmethod
    def find_by_hotel_code(hotel_code: int):
        endpoint = '{0}/hotels/{1}'.format(broker_URL, hotel_code)
        response = requests.get(endpoint)

        if response.status_code == 200:
            return response.json()
        else:
            return None


class Price(BaseModel):
    adult: float
    child: float


class Room(BaseModel):
    roomID: int
    categoryName: str
    price: Price


class Hotel(BaseModel):
    id: int
    name: str
    cityCode: int
    cityName: str
    rooms: List[Room]

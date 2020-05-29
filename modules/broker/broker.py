from pydantic import BaseModel
from typing import List
import requests


class Broker:

    def __init__(self, broker_url):
        self.__broker_url = broker_url

    def find_by_city_code(self, city_code):
        endpoint = '{0}/hotels/avail/{1}'.format(self.__broker_url, city_code)
        response = requests.get(endpoint)

        if response.status_code == 200:
            return response.json()
        else:
            return None

    def find_by_hotel_code(self, hotel_code):
        endpoint = '{0}/hotels/{1}'.format(self.__broker_url, hotel_code)
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

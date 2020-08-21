from pydantic import BaseModel
from typing import List


class Broker:

    def __init__(self, broker_url, request_client):
        self.__broker_url = broker_url
        self.__request_client = request_client

    def find_by_city_code(self, city_code):
        endpoint = '{0}/hotels/avail/{1}'.format(self.__broker_url, city_code)
        response = self.__request_client.get(endpoint)

        if response.status_code == 200:
            return response.json()
        else:
            return None

    def find_by_hotel_code(self, hotel_code):
        endpoint = '{0}/hotels/{1}'.format(self.__broker_url, hotel_code)
        response = self.__request_client.get(endpoint)

        if response.status_code == 200:
            return response.json()
        else:
            return None


class Price(BaseModel):  # pragma: no cover
    adult: float
    child: float


class Room(BaseModel):  # pragma: no cover
    roomID: int
    categoryName: str
    price: Price


class Hotel(BaseModel):  # pragma: no cover
    id: int
    name: str
    cityCode: int
    cityName: str
    rooms: List[Room]

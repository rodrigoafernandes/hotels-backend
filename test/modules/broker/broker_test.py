from unittest import TestCase, main
from mockito import mock, unstub, when
from modules.broker.broker import Broker
from requests import Response
import requests


class BrokerTest(TestCase):

    def __init__(self, *args, **kwargs):
        super(BrokerTest, self).__init__(*args, **kwargs)
        self.__set_up()

    def test_given_hotels_found_when_search_by_city_then_should_returns_hotels_json(self):
        city_code = 1032
        endpoint = f'{self.__api_endpoint}/hotels/avail/{city_code}'
        response = mock({'status_code': 200}, spec=Response)
        json_hotels = [
            {
                "id": 1,
                "name": "Hotel Teste 1",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 1372.54,
                            "child": 848.61
                        }
                    }
                ]
            }
        ]

        when(response).json().thenReturn(json_hotels)
        when(self.__request_client).get(endpoint).thenReturn(response)

        hotels = self.__broker.find_by_city_code(city_code)

        self.assertIsNotNone(hotels)
        self.assertEqual(1, hotels[0]['id'])

    def test_given_hotels_not_found_when_search_by_city_then_should_returns_none(self):
        city_code = 1032
        endpoint = f'{self.__api_endpoint}/hotels/avail/{city_code}'
        response = mock({'status_code': 404}, spec=Response)
        when(self.__request_client).get(endpoint).thenReturn(response)

        hotels = self.__broker.find_by_city_code(city_code)

        self.assertIsNone(hotels)

    def test_given_hotel_found_when_search_by_hotel_then_should_returns_hotel_json(self):
        hotel_code = 1
        endpoint = f'{self.__api_endpoint}/hotels/{hotel_code}'
        response = mock({'status_code': 200}, spec=Response)
        json_hotels = [
            {
                "id": 1,
                "name": "Hotel Teste 1",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 1372.54,
                            "child": 848.61
                        }
                    }
                ]
            }
        ]

        when(response).json().thenReturn(json_hotels)
        when(self.__request_client).get(endpoint).thenReturn(response)

        hotel = self.__broker.find_by_hotel_code(hotel_code)

        self.assertIsNotNone(hotel)
        self.assertEqual(1032, hotel[0]['cityCode'])

    def test_given_hotel_not_found_when_search_by_hotel_then_should_returns_none(self):
        hotel_code = -1
        endpoint = f'{self.__api_endpoint}/hotels/{hotel_code}'
        response = mock({'status_code': 404}, spec=Response)

        when(self.__request_client).get(endpoint).thenReturn(response)

        hotel = self.__broker.find_by_hotel_code(hotel_code)

        self.assertIsNone(hotel)

    def __set_up(self):
        self.__api_endpoint = 'http://localhost:8080'
        self.__request_client = mock(requests)
        self.__broker = Broker(self.__api_endpoint, self.__request_client)

    def __exit__(self, exc_type, exc_val, exc_tb):
        unstub()


if __name__ == '__main__':
    main()

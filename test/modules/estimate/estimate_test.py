from unittest import TestCase, main
from mockito import any, mock, unstub, when
from modules import Estimate, Broker, Converter, Markup
from modules.models import SearchDataRequest, SearchData
from datetime import datetime


class EstimateTest(TestCase):

    def __init__(self, *args, **kwargs):
        super(EstimateTest, self).__init__(*args, **kwargs)
        self.__set_up()

    def test_given_when_then_should_returns_(self):
        search_data_request = SearchDataRequest(cityCodeRequest="1032", startDateRequest="2020-08-20",
                                                endDateRequest="2020-08-29", qtGrowUpRequest="2", qtChildRequest="1")
        search_data = SearchData(cityCode=1032, startDate=datetime.strptime("2020-08-20", self.__date_format),
                                 endDate=datetime.strptime("2020-08-29", self.__date_format), qtGrowUp=2, qtChild=1)
        hotels_response = self.__get_hotels_broker_response()

        when(self.__converter).to_search_data(search_data_request=search_data_request,
                                              errors_constraint=any()).thenReturn(search_data)
        when(self.__broker).find_by_city_code(city_code=1032).thenReturn(hotels_response)
        for hotel in hotels_response:
            for room in hotel['rooms']:
                when(self.__markup).calculate(room['price']['adult']).thenReturn(round((room['price']['adult'] / 7), 4))
                when(self.__markup).calculate(room['price']['child']).thenReturn(round((room['price']['child'] / 7), 4))

        hotels_avail = self.__estimate.estimate_city(search_data_request)

        self.assertIsNotNone(hotels_avail)

    def __get_hotels_broker_response(self):
        hotels_response = [
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
            },
            {
                "id": 4,
                "name": "Hotel Teste 4",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 341.76,
                            "child": 782.14
                        }
                    },
                    {
                        "roomID": 1,
                        "categoryName": "Luxo",
                        "price": {
                            "adult": 483.02,
                            "child": 591.33
                        }
                    }
                ]
            },
            {
                "id": 7,
                "name": "Hotel Teste 7",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 493.15,
                            "child": 650.02
                        }
                    },
                    {
                        "roomID": 1,
                        "categoryName": "Luxo",
                        "price": {
                            "adult": 824.63,
                            "child": 231.65
                        }
                    }
                ]
            },
            {
                "id": 11,
                "name": "Hotel Teste 11",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 613.52,
                            "child": 315.02
                        }
                    },
                    {
                        "roomID": 1,
                        "categoryName": "Luxo",
                        "price": {
                            "adult": 1609.8,
                            "child": 776.97
                        }
                    }
                ]
            },
            {
                "id": 16,
                "name": "Hotel Teste 16",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 1664.31,
                            "child": 949.55
                        }
                    },
                    {
                        "roomID": 1,
                        "categoryName": "Luxo",
                        "price": {
                            "adult": 367.58,
                            "child": 171.25
                        }
                    }
                ]
            },
            {
                "id": 20,
                "name": "Hotel Teste 20",
                "cityCode": 1032,
                "cityName": "Porto Seguro",
                "rooms": [
                    {
                        "roomID": 0,
                        "categoryName": "Standard",
                        "price": {
                            "adult": 668.32,
                            "child": 416.08
                        }
                    },
                    {
                        "roomID": 1,
                        "categoryName": "Luxo",
                        "price": {
                            "adult": 1688.01,
                            "child": 213.76
                        }
                    },
                    {
                        "roomID": 2,
                        "categoryName": "Triplo",
                        "price": {
                            "adult": 1887.5,
                            "child": 248.45
                        }
                    }
                ]
            }
        ]
        return hotels_response

    def __set_up(self):
        self.__broker = mock(Broker)
        self.__converter = mock(Converter)
        self.__markup = mock(Markup)
        self.__parallel_process = 4
        self.__date_format = '%Y-%m-%d'
        self.__estimate = Estimate(self.__broker, self.__converter, self.__markup, self.__parallel_process)

    def __exit__(self, exc_type, exc_val, exc_tb):
        unstub()


if __name__ == '__main__':
    main()

from brokerClient import HotelBrokerClient
from requesthotelmodel import SearchDataRequest, SearchData
import datetime


class Estimate:

    @staticmethod
    def estimate_city(search_data_request: SearchDataRequest):
        search_data = SearchData(cityCode=1, startDate=datetime.datetime.now(), endDate=datetime.datetime.now(),
                                 qtGrowUp=1, qtChild=1)
        HotelBrokerClient.find_by_city_code(search_data.cityCode)

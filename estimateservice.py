from brokerClient import HotelBrokerClient
from hotelsconverter import Converter
from markup import MarkupCalculator
from requesthotelmodel import SearchDataRequest, SearchData


class Estimate:

    @staticmethod
    def estimate_city(search_data_request: SearchDataRequest):
        errors_constraint = []
        search_data = Converter.to_search_data(search_data_request, errors_constraint)
        HotelBrokerClient.find_by_city_code(search_data.cityCode)

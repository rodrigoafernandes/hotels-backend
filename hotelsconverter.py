from requesthotelmodel import SearchDataRequest, SearchData
from responsehotelsmodel import ErrorMessage
from typing import List
import datetime

convert_int_error_message = "O campo {0} deve possuir valor númerico e inteiro"
convert_datetime_error_message = "O campo {0} deve possuir o formato {1}"


class Converter:

    @staticmethod
    def to_search_data(self, search_data_request: SearchDataRequest, errors_constraint: List[ErrorMessage]):
        city_code = self.convert_request_to_int(search_data_request.cityCodeRequest, "cityCode", errors_constraint)
        start_date = self.convert_request_to_date(search_data_request.startDateRequest, "startDate", errors_constraint)
        end_date = self.convert_request_to_date(search_data_request.endDateRequest, "endDate", errors_constraint)
        qt_grow_up = self.convert_request_to_int(search_data_request.qtGrowUpRequest, "qtGrowUp", errors_constraint)
        qt_child = self.convert_request_to_int(search_data_request.qtGrowUpRequest, "qtChild", errors_constraint)

        return SearchData(city_code=city_code, start_date=start_date, end_date=end_date, qt_grow_up=qt_grow_up,
                          qt_child=qt_child)

    @staticmethod
    def to_search_data_hotel(self, search_data_request: SearchDataRequest, errors_constraint: List[ErrorMessage]):
        search_data = self.to_search_data(search_data_request, errors_constraint)
        search_data.hotelCode = self.convert_request_to_int(search_data_request.hotelCodeRequest, "hotelCode",
                                                            errors_constraint)

        return search_data

    def __convert_request_to_int(self, number_request: str, property_name: str, errors_constraint: List[ErrorMessage]):
        if number_request is not None or not number_request.isspace():

            try:
                return int(str)
            except ValueError:
                errors_constraint.append(ErrorMessage(property_name=property_name,
                                                      message=convert_int_error_message.format(property_name)))
                return None
        else:
            return None

    def __convert_request_to_date(self, date_request: str, property_name: str, errors_constraint: List[ErrorMessage]):
        if date_request is not None or not date_request.isspace():
            date_format = '%Y-%m-%d'
            try:
                return datetime.datetime.strptime(date_request, date_format).date()
            except ValueError:
                errors_constraint.append(ErrorMessage(property_name=property_name,
                                                      message=convert_datetime_error_message.format(property_name,
                                                                                                    date_format)))
                return None
        else:
            return None

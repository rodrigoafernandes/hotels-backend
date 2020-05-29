from modules.models.request.requestsHotel import SearchData
from modules.models.response.responsesHotels import ErrorMessage
from datetime import datetime


class Converter:

    def __init__(self):
        self.__convert_int_error_message = "O campo {0} deve possuir valor númerico e inteiro"
        self.__convert_datetime_error_message = "O campo {0} deve possuir o formato {1}"

    def to_search_data(self, search_data_request, errors_constraint):
        city_code = self.__convert_request_to_int(number_request=search_data_request.cityCodeRequest,
                                                  property_name="cityCode", errors_constraint=errors_constraint)
        start_date = self.__convert_request_to_date(search_data_request.startDateRequest, "startDate",
                                                    errors_constraint)
        end_date = self.__convert_request_to_date(search_data_request.endDateRequest, "endDate", errors_constraint)
        qt_grow_up = self.__convert_request_to_int(search_data_request.qtGrowUpRequest, "qtGrowUp", errors_constraint)
        qt_child = self.__convert_request_to_int(search_data_request.qtGrowUpRequest, "qtChild", errors_constraint)

        return SearchData(cityCode=city_code, startDate=start_date, endDate=end_date, qtGrowUp=qt_grow_up,
                          qtChild=qt_child)

    def to_search_data_hotel(self, search_data_request, errors_constraint):
        search_data = self.to_search_data(search_data_request, errors_constraint)
        search_data.hotelCode = self.__convert_request_to_int(search_data_request.hotelCodeRequest, "hotelCode",
                                                              errors_constraint)

        return search_data

    def __convert_request_to_int(self, number_request, property_name, errors_constraint):
        if number_request is not None or not number_request.isspace():

            try:
                return int(number_request)
            except ValueError:
                errors_constraint.append(ErrorMessage(property=property_name,
                                                      message=self.__convert_int_error_message.format(property_name)))
                return None
        else:
            return None

    def __convert_request_to_date(self, date_request, property_name, errors_constraint):
        if date_request is not None or not date_request.isspace():
            date_format = '%Y-%m-%d'
            try:
                return datetime.strptime(date_request, date_format).date()
            except ValueError:
                errors_constraint.append(ErrorMessage(property=property_name,
                                                      message=self.__convert_datetime_error_message.format(
                                                          property_name, date_format)))
                return None
        else:
            return None

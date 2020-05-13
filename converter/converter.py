from models.request.requestsHotel import SearchDataRequest, SearchData
from models.response.responsesHotels import ErrorMessage
from typing import List
import datetime

convert_int_error_message = "O campo {0} deve possuir valor númerico e inteiro"
convert_datetime_error_message = "O campo {0} deve possuir o formato {1}"


def to_search_data(search_data_request: SearchDataRequest, errors_constraint: List[ErrorMessage]):
    city_code = __convert_request_to_int(number_request=search_data_request.cityCodeRequest,
                                         property_name="cityCode", errors_constraint=errors_constraint)
    start_date = __convert_request_to_date(search_data_request.startDateRequest, "startDate",
                                           errors_constraint)
    end_date = __convert_request_to_date(search_data_request.endDateRequest, "endDate", errors_constraint)
    qt_grow_up = __convert_request_to_int(search_data_request.qtGrowUpRequest, "qtGrowUp",
                                          errors_constraint)
    qt_child = __convert_request_to_int(search_data_request.qtGrowUpRequest, "qtChild", errors_constraint)

    return SearchData(cityCode=city_code, startDate=start_date, endDate=end_date, qtGrowUp=qt_grow_up,
                      qtChild=qt_child)


def to_search_data_hotel(search_data_request: SearchDataRequest, errors_constraint: List[ErrorMessage]):
    search_data = to_search_data(search_data_request, errors_constraint)
    search_data.hotelCode = __convert_request_to_int(search_data_request.hotelCodeRequest, "hotelCode",
                                                     errors_constraint)

    return search_data


def __convert_request_to_int(number_request: str, property_name: str, errors_constraint: List[ErrorMessage]):
    if number_request is not None or not number_request.isspace():

        try:
            return int(number_request)
        except ValueError:
            errors_constraint.append(ErrorMessage(property=property_name,
                                                  message=convert_int_error_message.format(property_name)))
            return None
    else:
        return None


def __convert_request_to_date(date_request: str, property_name: str, errors_constraint: List[ErrorMessage]):
    if date_request is not None or not date_request.isspace():
        date_format = '%Y-%m-%d'
        try:
            return datetime.datetime.strptime(date_request, date_format).date()
        except ValueError:
            errors_constraint.append(ErrorMessage(property=property_name,
                                                  message=convert_datetime_error_message.format(property_name,
                                                                                                date_format)))
            return None
    else:
        return None

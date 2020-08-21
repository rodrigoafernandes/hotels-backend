from unittest import TestCase, main
from modules import Converter
from modules.models import SearchDataRequest


class ConverterTest(TestCase):

    def __init__(self, *args, **kwargs):
        super(ConverterTest, self).__init__(*args, **kwargs)
        self.__set_up()

    def test_given_valid_request_data_when_convert_to_search_data_then_should_returns_no_error_constraint(self):
        errors_constraint = []
        search_data_request = SearchDataRequest(cityCodeRequest="1032", startDateRequest="2020-08-20",
                                                endDateRequest="2020-08-29", qtGrowUpRequest="2", qtChildRequest="1")

        search_data = self.__converter.to_search_data(search_data_request, errors_constraint)

        self.assertEqual(0, len(errors_constraint))
        self.assertIsNotNone(search_data)

    def test_given_invalid_data_request_when_convert_to_search_data_then_should_returns_errors_constraint(self):
        errors_constraint = []
        search_data_request = SearchDataRequest(cityCodeRequest="ABC", startDateRequest="",
                                                endDateRequest="", qtGrowUpRequest=" ", qtChildRequest="")

        search_data = self.__converter.to_search_data(search_data_request, errors_constraint)

        self.assertEqual(5, len(errors_constraint))
        self.assertIsNotNone(search_data)

    def test_given_valid_request_data_when_convert_to_search_data_hotel_then_should_returns_no_error_constraint(self):
        errors_constraint = []
        search_data_request = SearchDataRequest(cityCodeRequest="1032", startDateRequest="2020-08-20",
                                                endDateRequest="2020-08-29", qtGrowUpRequest="2", qtChildRequest="1",
                                                hotelCodeRequest="1")

        search_data = self.__converter.to_search_data_hotel(search_data_request, errors_constraint)

        self.assertEqual(0, len(errors_constraint))
        self.assertIsNotNone(search_data)

    def test_given_invalid_request_data_when_convert_to_search_data_hotel_then_should_returns_no_error_constraint(self):
        errors_constraint = []
        search_data_request = SearchDataRequest(cityCodeRequest="ABC", startDateRequest="",
                                                endDateRequest="", qtGrowUpRequest=" ", qtChildRequest="",
                                                hotelCodeRequest=" ")

        search_data = self.__converter.to_search_data_hotel(search_data_request, errors_constraint)

        self.assertEqual(6, len(errors_constraint))
        self.assertIsNotNone(search_data)

    def __set_up(self):
        self.__converter = Converter()


if __name__ == '__main__':
    main()

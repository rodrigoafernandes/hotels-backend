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

        when(self.__converter).to_search_data(search_data_request=search_data_request,
                                              errors_constraint=any()).thenReturn(search_data)
        pass

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

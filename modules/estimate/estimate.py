from multiprocessing import Process, Manager
from modules.models import HotelAvail
from modules.exceptions import HotelNotFound
from modules.estimate.processor import available_hotel, available_rooms, prepare_hotels_list


class Estimate:

    def __init__(self, broker, converter, markup, parallel_process):
        self.__broker = broker
        self.__converter = converter
        self.__markup = markup
        self.__search_data = None
        self.__parallel_process = parallel_process

    def estimate_city(self, search_data_request):
        self.__search_data = None
        errors_constraint = []

        self.__search_data = self.__converter.to_search_data(search_data_request=search_data_request,
                                                             errors_constraint=errors_constraint)
        availability_by_city = self.__broker.find_by_city_code(city_code=self.__search_data.cityCode)

        with Manager() as manager:
            hotels_parallel = manager.list()
            processes = []

            list_hotels = prepare_hotels_list(availability_by_city, self.__parallel_process)

            for hotels_to_process in list_hotels:
                p = Process(target=available_hotel, args=(hotels_parallel, hotels_to_process, self.__search_data,
                                                          self.__markup))
                p.start()
                processes.append(p)

            for p in processes:
                p.join()

            hotels = list(hotels_parallel)

            hotels.sort(key=self.__get_hotel_id)

            return hotels

    def estimate_hotel(self, search_data_request):
        self.__search_data = None
        errors_constraint = []
        hotels = []

        self.__search_data = self.__converter.to_search_data_hotel(search_data_request=search_data_request,
                                                                   errors_constraint=errors_constraint)
        hotel = self.__broker.find_by_hotel_code(self.__search_data.hotelCode)[0]

        if hotel['cityCode'] == self.__search_data.cityCode:
            rooms = available_rooms(avail_rooms=hotel['rooms'], search_data=self.__search_data, markup=self.__markup)
            hotels.append(HotelAvail(id=hotel['id'], cityName=hotel['cityName'], rooms=rooms))

            return hotels
        else:
            raise HotelNotFound(f'Hotel com id: {self.__search_data.hotelCode} não localidado')

    def __get_hotel_id(self, hotel):
        return hotel['id']

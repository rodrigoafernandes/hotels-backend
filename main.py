from fastapi import FastAPI
from modules import AppVariables, Broker, Converter, Estimate, Markup
from modules.models import HotelAvail, SearchDataRequest
from typing import List

app = FastAPI()
app_env_vars = AppVariables()
broker = Broker(app_env_vars.get_broker_url())
converter = Converter()
markup = Markup(app_env_vars.get_markup())
estimate = Estimate(broker, converter, markup, app_env_vars.get_parallel_process())


@app.get("/estimate/city/{city_code}", response_model=List[HotelAvail])
def home(city_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(cityCodeRequest=city_code, startDateRequest=startDate,
                                            endDateRequest=endDate, qtGrowUpRequest=qtGrowUp,
                                            qtChildRequest=qtChild)
    return estimate.estimate_city(search_data_request=search_data_request)


@app.get("/estimate/city/{city_code}/hotel/{hotel_code}", response_model=List[HotelAvail])
def home(city_code: str, hotel_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(cityCodeRequest=city_code, startDateRequest=startDate,
                                            endDateRequest=endDate, qtGrowUpRequest=qtGrowUp,
                                            qtChildRequest=qtChild, hotelCodeRequest=hotel_code)
    return estimate.estimate_hotel(search_data_request=search_data_request)

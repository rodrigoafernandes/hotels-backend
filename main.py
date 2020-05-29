from fastapi import FastAPI
from modules import AppVariables, Broker, Converter, Estimate, Markup
from modules.models import HotelAvail, SearchDataRequest
from typing import List

app = FastAPI()
app_env_vars = AppVariables()
broker = Broker(app_env_vars.get_broker_url())
converter = Converter()
markup = Markup(app_env_vars.get_markup())
estimate = Estimate(broker, converter, markup)


@app.get("/estimate/city/{city_code}")
def home(city_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(cityCodeRequest=city_code, startDateRequest=startDate,
                                            endDateRequest=endDate, qtGrowUpRequest=qtGrowUp,
                                            qtChildRequest=qtChild)
    return estimate.estimate_city(search_data_request=search_data_request)


@app.get("/estimate/city/{city_code}/hotel/{hotel_code}")
def home(city_code: str, hotel_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(cityCodeRequest=city_code, startDateRequest=startDate,
                                            endDateRequest=endDate, qtGrowUpRequest=qtGrowUp,
                                            qtChildRequest=qtChild, hotelCodeRequest=hotel_code)
    return estimate.estimate_city(search_data_request=search_data_request)

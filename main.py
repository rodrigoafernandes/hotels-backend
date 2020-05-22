from fastapi import FastAPI
from estimate import estimate_city
from models import HotelAvail, SearchDataRequest
from typing import List

app = FastAPI()


@app.get("/estimate/city/{city_code}", response_model=List[HotelAvail])
def home(city_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(cityCodeRequest=city_code, startDateRequest=startDate,
                                            endDateRequest=endDate, qtGrowUpRequest=qtGrowUp,
                                            qtChildRequest=qtChild)
    return estimate_city(search_data_request=search_data_request)

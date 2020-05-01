from fastapi import FastAPI
from estimateservice import Estimate
from requesthotelmodel import SearchDataRequest
from responsehotelsmodel import HotelAvail
from typing import List

app = FastAPI()


@app.get("/estimate/city/{city_code}", response_model=List[HotelAvail])
def home(city_code: str, startDate: str, endDate: str, qtGrowUp: str, qtChild: str):
    search_data_request = SearchDataRequest(city_code_request=city_code, start_date_request=startDate,
                                            end_date_request=endDate, qt_grow_up_request=qtGrowUp,
                                            qt_child_request=qtChild)
    return Estimate.estimate_city(search_data_request)

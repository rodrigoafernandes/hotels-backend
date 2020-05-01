from pydantic import BaseModel
import datetime


class SearchDataRequest(BaseModel):
    cityCodeRequest: str
    startDateRequest: str
    endDateRequest: str
    qtGrowUpRequest: str
    qtChildRequest: str
    hotelCodeRequest: str

    def __init__(self, city_code_request: str, start_date_request: str, end_date_request: str, qt_grow_up_request: str,
                 qt_child_request: str, hotel_code_request=None):
        super().__init__()
        self.cityCodeRequest = city_code_request
        self.startDateRequest = start_date_request
        self.endDateRequest = end_date_request
        self.qtGrowUpRequest = qt_grow_up_request
        self.qtChildRequest = qt_child_request
        self.hotelCodeRequest = hotel_code_request


class SearchData(BaseModel):
    cityCode: int
    startDate: datetime.date
    endDate: datetime.date
    qtGrowUp: int
    qtChild: int
    hotelCode: int

    def __init__(self, city_code: int, start_date: datetime.date, end_date: datetime.date, qt_grow_up: int,
                 qt_child: int, hotel_code=None):
        super().__init__()
        self.cityCode = city_code
        self.startDate = start_date
        self.endDate = end_date
        self.qtGrowUp = qt_grow_up
        self.qtChild = qt_child
        self.hotelCode = hotel_code

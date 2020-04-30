from pydantic import BaseModel
import datetime


class SearchDataRequest(BaseModel):
    cityCodeRequest: str
    startDateRequest: str
    endDateRequest: str
    qtGrowUpRequest: str
    qtChildRequest: str
    hotelCodeRequest: str

    def __init__(self, cityCodeRequest, startDateRequest, endDateRequest, qtGrowUpRequest, qtChildRequest,
                 hotelCodeRequest):
        super().__init__()
        self.cityCodeRequest = cityCodeRequest
        self.startDateRequest = startDateRequest
        self.endDateRequest = endDateRequest
        self.qtGrowUpRequest = qtGrowUpRequest
        self.qtChildRequest = qtChildRequest
        self.hotelCodeRequest = hotelCodeRequest


class SearchData(BaseModel):
    cityCode: int
    startDate: datetime
    endDate: datetime
    qtGrowUp: int
    qtChild: int
    hotelCode: int

    def __init__(self, cityCode, startDate, endDate, qtGrowUp, qtChild, hotelCode):
        super().__init__()
        self.cityCode = cityCode
        self.startDate = startDate
        self.endDate = endDate
        self.qtGrowUp = qtGrowUp
        self.qtChild = qtChild
        self.hotelCode = hotelCode

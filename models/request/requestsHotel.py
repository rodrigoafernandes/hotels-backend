from pydantic import BaseModel
import datetime


class SearchDataRequest(BaseModel):
    cityCodeRequest: str
    startDateRequest: str
    endDateRequest: str
    qtGrowUpRequest: str
    qtChildRequest: str
    hotelCodeRequest: str = None


class SearchData(BaseModel):
    cityCode: int
    startDate: datetime.date
    endDate: datetime.date
    qtGrowUp: int
    qtChild: int
    hotelCode: int = None


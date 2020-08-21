from pydantic import BaseModel
from typing import Optional
import datetime


class SearchDataRequest(BaseModel):  # pragma: no cover
    cityCodeRequest: str
    startDateRequest: str
    endDateRequest: str
    qtGrowUpRequest: str
    qtChildRequest: str
    hotelCodeRequest: str = None


class SearchData(BaseModel):  # pragma: no cover
    cityCode: int = None
    startDate: datetime.date = None
    endDate: datetime.date = None
    qtGrowUp: int = None
    qtChild: int = None
    hotelCode: int = None


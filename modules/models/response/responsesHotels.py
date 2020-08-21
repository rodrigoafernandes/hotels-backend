from pydantic import BaseModel
from typing import List


class PriceDetail(BaseModel):  # pragma: no cover
    pricePerDayAdult: float
    pricePerDayChild: float


class RoomAvail(BaseModel):  # pragma: no cover
    roomID: int
    categoryName: str
    totalPrice: float
    priceDetail: PriceDetail


class HotelAvail(BaseModel):  # pragma: no cover
    id: int
    cityName: str
    rooms: List[RoomAvail]


class ErrorMessage(BaseModel):  # pragma: no cover
    property: str
    message: str

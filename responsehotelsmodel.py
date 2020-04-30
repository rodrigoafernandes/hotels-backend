from pydantic import BaseModel
from typing import List


class PriceDetail(BaseModel):
    pricePerDayAdult: float
    pricePerDayChild: float


class RoomAvail(BaseModel):
    roomID: int
    categoryName: str
    totalPrice: float
    priceDetail: PriceDetail


class HotelAvail(BaseModel):
    id: int
    cityName: str
    rooms: List[RoomAvail]

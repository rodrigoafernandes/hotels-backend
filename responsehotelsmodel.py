from pydantic import BaseModel
from typing import List

class PriceDetailDTO(BaseModel):
    pricePerDayAdult: float
    pricePerDayChild: float

class RoomAvail(BaseModel):
    roomID: int
    categoryName: str
    totalPrice: float
    priceDetail: PriceDetailDTO

class HotelAvail(BaseModel):
    id: int
    cityName: str
    rooms: List[RoomAvail]

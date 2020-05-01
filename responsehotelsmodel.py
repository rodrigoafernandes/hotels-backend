from pydantic import BaseModel
from typing import List


class PriceDetail(BaseModel):
    pricePerDayAdult: float
    pricePerDayChild: float

    def __init__(self, price_per_day_adult: float, price_per_day_child: float):
        super().__init__()
        self.pricePerDayAdult = price_per_day_adult
        self.pricePerDayChild = price_per_day_child


class RoomAvail(BaseModel):
    roomID: int
    categoryName: str
    totalPrice: float
    priceDetail: PriceDetail

    def __init__(self, room_ID: int, category_name: str, total_price: float, price_detail: PriceDetail):
        super().__init__()
        self.roomID = room_ID
        self.categoryName = category_name
        self.totalPrice = total_price
        self.priceDetail = price_detail


class HotelAvail(BaseModel):
    id: int
    cityName: str
    rooms: List[RoomAvail]

    def __init__(self, ID: int, city_name: str, rooms: List[RoomAvail]):
        super().__init__()
        self.id = ID
        self.cityName = city_name
        self.rooms = rooms


class ErrorMessage(BaseModel):
    property: str
    message: str

    def __init__(self, property_name: str, message: str):
        super().__init__()
        self.property = property_name
        self.message = message

from fastapi import FastAPI
from responsehotelsmodel import HotelAvail

app = FastAPI()

@app.get("/estimate/city/{city_code}", response_model=HotelAvail)
def home(city_code: int, startDate: str, endDate: str, qtGrowUp: int, qtChild: int):
    return {"id": city_code,
            "cityName": "São Paulo",
            "rooms": [
                {"roomID": 0, "categoryName": "STD", "totalPrice": 100.5,
                 "priceDetail": {
                    "pricePerDayChild": 32.5,
                     "pricePerDayAdult": 77.5
                 }}
            ]}
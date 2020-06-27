def available_rooms(avail_rooms, search_data, markup):
    rooms = []
    qtd_days = search_data.endDate - search_data.startDate
    for room in avail_rooms:
        room_adult_price_with_tax = markup.calculate(room['price']['adult'])
        room_child_price_with_tax = markup.calculate(room['price']['child'])
        total_price = round(
            ((room_adult_price_with_tax * qtd_days.days) + (room_child_price_with_tax * qtd_days.days)), 4)
        price_detail = {'pricePerDayAdult': room_adult_price_with_tax, 'pricePerDayChild': room_child_price_with_tax}
        rooms.append({'roomID': room['roomID'], 'categoryName': room['categoryName'], 'totalPrice': total_price,
                      'priceDetail': price_detail})

    return rooms


def available_hotel(hotels_parallel, availability_hotel, search_data, markup):
    for hotel in availability_hotel:
        rooms = available_rooms(avail_rooms=hotel['rooms'], search_data=search_data, markup=markup)
        hotels_parallel.append({'id': hotel['id'], 'cityName': hotel['cityName'], 'rooms': rooms})


def prepare_hotels_list(available_hotels, size):
    for i in range(0, size):
        yield available_hotels[i::size]

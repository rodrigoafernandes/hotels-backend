from os import environ


default_markup = float(environ['DEFAULT_MARKUP'])


def calculate(value_to_markup: float):
    return round((value_to_markup / default_markup), 4)

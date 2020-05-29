class Markup:
    def __init__(self, markup_value):
        self.__markup_value = markup_value

    def calculate(self, value_to_markup):
        return round((value_to_markup / self.__markup_value), 4)

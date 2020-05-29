from os import environ


class AppVariables:

    def __init__(self):
        self.__markup = float(environ['DEFAULT_MARKUP'])
        self.__broker_url = environ['BROKER_URL']

    def get_markup(self):
        return self.__markup

    def get_broker_url(self):
        return self.__broker_url

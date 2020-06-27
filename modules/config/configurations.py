from os import environ


class AppVariables:

    def __init__(self):
        self.__markup = float(environ['DEFAULT_MARKUP'])
        self.__broker_url = environ['BROKER_URL']
        self.__parallel_process = int(environ['PARALLEL_PROCESS'])

    def get_markup(self):
        return self.__markup

    def get_broker_url(self):
        return self.__broker_url

    def get_parallel_process(self):
        return self.__parallel_process

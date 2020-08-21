class HotelNotFound(Exception):  # pragma: no cover

    def __init__(self, *args):
        if args:
            self.__mensagem = args[0]
        else:
            self.__mensagem = None

    def __str__(self):
        if self.__mensagem:
            print(self.__mensagem)
        else:
            print('Usuário já cadastrado')

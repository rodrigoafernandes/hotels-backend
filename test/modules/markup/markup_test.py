from unittest import TestCase, main
from modules.markup import Markup


class MarkupTest(TestCase):

    def __init__(self, *args, **kwargs):
        super(MarkupTest, self).__init__(*args, **kwargs)
        self.__set_up()

    def test_given_raw_value_when_markup_value_then_should_returns_raw_value_divided_markup_value(self):
        raw_value = 100
        expected_value = 142.8571

        markup_value = self.__markup.calculate(raw_value)

        self.assertEqual(expected_value, markup_value)

    def __set_up(self):
        self.__markup = Markup(0.7)


if __name__ == '__main__':
    main()

package com.company;

import java.math.BigInteger;
import java.util.Objects;

public class Main {

    private static final String REQUIRED_MSG = "Ожидается целое число больше нуля или ключевое слово `test`";
    private static final String ASSERT_ERROR_MSG = "Неверный ответ";

    // First argument should be a number for calculating factorial either keyword `test` for test
    public static void main(String[] args) {
        if (args[0].equalsIgnoreCase("test")) {
            test();
        } else {
            try {
                Integer value = parseArg(args);
                BigInteger factorial = getFactorial(value);
                System.out.format("Факторал числа %d равен %d \n", value, factorial);
                long zeros = countZeros(factorial);
                System.out.format("Количество нулей факториала заданного числа: %d", zeros);
            } catch (RuntimeException e) {
                System.out.format("Не удалось расчитать факториал: %s\n %s", e.getLocalizedMessage(), REQUIRED_MSG);
            }
        }
    }

    public static Integer parseArg(String[] args) throws RuntimeException {
        int arg = Integer.parseInt(args[0]);
        if (arg < 0) {
            throw new IllegalArgumentException(String
                    .format("Переданный аргумент должен быть больше или равен нулю: %d", arg));
        }
        return arg;
    }

    public static BigInteger getFactorial(Integer f) {
        if (f <= 1) {
            return BigInteger.valueOf(1);
        } else {
            return BigInteger.valueOf(f).multiply(getFactorial(f - 1));
        }
    }

    public static long countZeros(BigInteger value) {
        return String.valueOf(value)
                .chars()
                .mapToObj(c -> (char) c)
                .filter(character -> character.equals('0'))
                .count();
    }

    // tests
    private static void test() {
        parseArgTest();
        getFactorialTest();
        countZerosTest();
        System.out.println("Тесты пройдены");
    }

    private static void countZerosTest() {
        if (countZeros(BigInteger.ZERO) != 0L) throw new AssertionError(ASSERT_ERROR_MSG);
        if (countZeros(BigInteger.valueOf(10000000)) != 8L) throw new AssertionError(ASSERT_ERROR_MSG);
        if (countZeros(BigInteger.valueOf(-190023L)) == 2L) throw new AssertionError(ASSERT_ERROR_MSG);
    }

    private static void getFactorialTest() {
        if (!getFactorial(0).equals(BigInteger.ONE))
            throw new AssertionError(ASSERT_ERROR_MSG);
        if (!getFactorial(1).equals(BigInteger.ONE))
            throw new AssertionError(ASSERT_ERROR_MSG);
        if (!getFactorial(10).equals(BigInteger.valueOf(3628800L)))
            throw new AssertionError(ASSERT_ERROR_MSG);
    }

    private static void parseArgTest() {
        Integer integer = parseArg(new String[]{"14"});
        Objects.requireNonNull(integer);
        if (integer != 14) throw new AssertionError(ASSERT_ERROR_MSG);
        try {
            parseArg(new String[]{"word"});
        } catch (Exception e) {
            if (!(e instanceof NumberFormatException))
                throw new AssertionError("Исключение не соответствует целевому");
        }
        try {
            parseArg(new String[]{"-199"});
        } catch (Exception e) {
            if (!e.getLocalizedMessage().equals("Переданный аргумент должен быть больше или равен нулю: -199"))
                throw new AssertionError("Сообщение не соответствует исключению");
            if (!(e instanceof IllegalArgumentException))
                throw new AssertionError("Исключение не соответствует целевому");
        }
    }
}

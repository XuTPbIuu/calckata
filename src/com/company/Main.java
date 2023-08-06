package com.company;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (пример: 6 + 6 или IV + II)");

        try {
            // Чтение строки от пользователя
            System.out.print("Введите строку: ");
            String input = scanner.nextLine().trim();
            System.out.println("Вы ввели: " + input + " и результат равен: ");

            // Разделение строки на числа и оператор
            String[] parts = input.split("\\s+");
            if (parts.length != 3) {
                throw new IllegalArgumentException("Неверный формат ввода");
            }

            // Проверка разрешенных операторов
            String operator = parts[1];
            if (!operator.equals("+") && !operator.equals("-") && !operator.equals("*") && !operator.equals("/")) {
                throw new IllegalArgumentException("Неверный оператор");
            }

            // Проверка входных чисел
            int operand1 = parseNumber(parts[0]);
            int operand2 = parseNumber(parts[2]);

            // Проверка системы счисления
            if ((isRomanNumeralInput(parts[0]) && !isRomanNumeralInput(parts[2])) || (!isRomanNumeralInput(parts[0]) && isRomanNumeralInput(parts[2]))) {
                throw new IllegalArgumentException("Используются одновременно разные системы счисления");
            }

            // Вычисление и вывод результата
            int result;
            switch (operator) {
                case "+":
                    result = operand1 + operand2;
                    break;
                case "-":
                    result = operand1 - operand2;
                    break;
                case "*":
                    result = operand1 * operand2;
                    break;
                case "/":
                    result = operand1 / operand2;
                    break;
                default:
                    throw new IllegalArgumentException("Неправильный оператор");
            }

            if (isRomanNumeralInput(input)) {
                if (result < 1) {
                    throw new IllegalArgumentException("в римской системе нет отрицательных чисел");
                }
                System.out.println(toRomanNumeral(result));
            } else {
                System.out.println(result);
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат чисел");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            // Закрытие Scanner после использования
            scanner.close();
        }
    }

    // Проверка является ли вход число арабским или римским
    private static boolean isRomanNumeralInput(String input) {
        return input.matches(".*[IVXLC].*");
    }

    // Преобразование строки с арабским числом в целое число
    private static int parseNumber(String input) {
        if (isRomanNumeralInput(input)) {
            return fromRomanNumeral(input);
        }
        return Integer.parseInt(input);}

    // Преобразование целого числа в римское число
    private static String toRomanNumeral(int number) {
        if (number < 1) {
            throw new IllegalArgumentException("Число должно быть положительным");
        }
        String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C" };
        int[] arabicNumbers = { 1, 4, 5, 9, 10, 40, 50, 90, 100 };
        StringBuilder romanNumeral = new StringBuilder();
        for (int i = arabicNumbers.length - 1; i >= 0; i--) {
            while (number >= arabicNumbers[i]) {
                romanNumeral.append(romanNumerals[i]);
                number -= arabicNumbers[i];
            }
        }
        return romanNumeral.toString();
    }

    // Преобразование римского числа в целое число
    private static int fromRomanNumeral(String romanNumeral) {
        String[] romanNumerals = { "I", "IV", "V", "IX", "X", "XL", "L", "XC", "C" };
        int[] arabicNumbers = { 1, 4, 5, 9, 10, 40, 50, 90, 100 };
        int number = 0;
        int i = romanNumerals.length - 1;
        while (romanNumeral.length() > 0 && i >= 0) {
            if (romanNumeral.startsWith(romanNumerals[i])) {
                number += arabicNumbers[i];
                romanNumeral = romanNumeral.substring(romanNumerals[i].length());
            } else {
                i--;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException("Неверный формат римского числа");
        }
        return number;
    }
}

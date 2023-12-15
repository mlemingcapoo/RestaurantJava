package helper;

/**
 *
 * @author capoo
 */
public class NumberHelper {

    public static String formatNumber(String number) {
        String[] parts = number.split("\\.");
        String integerPart = parts[0];
        String decimalPart = parts.length > 1 ? "." + parts[1] : "";

        if (integerPart.equals("0")) {
            return "0" + decimalPart;
        }

        StringBuilder formatted = new StringBuilder();
        int count = integerPart.length();

        for (int i = 0; i < count; i++) {
            char ch = integerPart.charAt(i);
            formatted.append(ch);

            if ((i + 1) % 3 == count % 3 && (i + 1) != count) {
                formatted.append(".");
            }
        }

        String result = formatted.toString() + decimalPart;
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static String removeLast2Char(String toString) {

        String result = toString;
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static String removeLast3Char(String substring) {
        String result = substring;
        if (result.endsWith(".0")) {
            result = result.substring(0, result.length() - 3);
        }
        return result;
    }

}

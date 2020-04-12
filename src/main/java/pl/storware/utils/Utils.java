package pl.storware.utils;

import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static final String ADD = "add";
    public static final String DIVIDE = "divide";
    public static final String SUBTRACT = "subtract";
    public static final String MULTIPLY = "multiply";
    public static final String APPLY = "apply";
    public static final String NUMBER_SPLITTER = " ";
    public static final String FILE_PATH = "operations.txt";
    public static final String TEST_CORRECT_FILE_PATH = "test-correct.txt";
    public static final String TEST_INCORRECT_FILE_PATH = "test-incorrect.txt";

    public static boolean isStringDigit(String s) {
        return StringUtils.isNumeric(s);
    }
}

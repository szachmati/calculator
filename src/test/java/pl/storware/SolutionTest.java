package pl.storware;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.storware.exception.InvalidOperationException;
import pl.storware.utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SolutionTest {

    private List<String> mockCorrectOperationList;
    private List<String> correctOperationFileList;
    private Calculator calculator;

    @Before
    public void init() throws IOException {
        calculator = new Calculator();
        mockCorrectOperationList = Arrays.asList(
            "add 3", "multiply 9", "subtract 1", "add 2", "apply 12"
        );
        correctOperationFileList = Files.readAllLines(Paths.get(Utils.TEST_CORRECT_FILE_PATH));
    }

    @Test
    public void testCalculateOnMockListShouldGiveCorrectResult() {
        Integer result = calculator.calculate(mockCorrectOperationList);
        Assert.assertEquals( 136, (long) result);
    }

    @Test
    public void testCalculateOnOperationsFromCorrectInputFileShouldGiveCorrectResult() throws Exception {
        Assert.assertEquals(calculator.readFileData(Utils.TEST_CORRECT_FILE_PATH), correctOperationFileList);
        Assert.assertEquals(28, (long) calculator.calculate(correctOperationFileList));
    }

    @Test(expected = InvalidOperationException.class)
    public void testValidationOnIncorrectInputFileShouldThrowAnInvalidOperationException() throws Exception {
        calculator.validateFile(Utils.TEST_INCORRECT_FILE_PATH);
    }


}

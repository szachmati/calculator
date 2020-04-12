package pl.storware;


import pl.storware.exception.EmptyFileException;
import pl.storware.exception.InvalidOperationException;
import pl.storware.utils.Utils;

import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

public class Calculator {

    private static final Logger log = Logger.getLogger(Calculator.class.getName());

    public void performCalculationsFromFile() {
        log.info("*** performCalculationsFromFile() start");
        try {
            calculate(readFileData(Utils.FILE_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        };
        log.info("*** performCalculationsFromFile() end");
    }

    public Integer calculate(List<String> fileLines) {
        log.info("*** calculate() start");
        System.out.println("Input from file:");
        fileLines.forEach(System.out::println);
        LinkedList<String> linkedOperationList = new LinkedList(fileLines);
        linkedOperationList.addFirst(linkedOperationList.get(linkedOperationList.size() - 1));
        linkedOperationList.remove(linkedOperationList.size() - 1);
        Integer result = 0;
        StringBuilder explanationBuilder = new StringBuilder();
        for (String operation : linkedOperationList) {
            String [] split = operation.split(Utils.NUMBER_SPLITTER);
            if (split[0].equalsIgnoreCase(Utils.APPLY)) {
                result = Integer.parseInt(split[1]);
                explanationBuilder.append(split[1]);
            } else if (split[0].equalsIgnoreCase(Utils.ADD)) {
                result += Integer.parseInt(split[1]);
                explanationBuilder.append(" + "+split[1]);
            } else if (split[0].equalsIgnoreCase(Utils.SUBTRACT)) {
                result -= Integer.parseInt(split[1]);
                explanationBuilder.append(" - "+split[1]);
            } else if (split[0].equalsIgnoreCase(Utils.MULTIPLY)) {
                result *= Integer.parseInt(split[1]);
                explanationBuilder.append(" * "+split[1]);
            } else {
                if (Integer.parseInt(split[1]) == 0) {
                    throw new ArithmeticException("Cannot divide by 0");
                }
                explanationBuilder.append(" / "+split[1]);
                result /= Integer.parseInt(split[1]);
            }
        }
        System.out.println("Output: "+result);
        System.out.println("Explanation: "+explanationBuilder.toString()+" = "+result);
        log.info("*** calculate() end with result= "+result);
        return result;
    }

    public List<String> readFileData(String filePath) throws Exception {
        log.info("*** readFileData() start");
        List<String> fileLines = validateFile(filePath);
        log.info("*** readFileData() end returning list: "+fileLines.toString());
        return fileLines;
    }

    public List<String> validateFile(String filePath) throws Exception {
        if (Files.notExists(Paths.get(filePath.toLowerCase()))) {
            log.warning("*** readFileData() cannot find file "+filePath+" in root folder");
            throw new FileNotFoundException("File "+filePath+" was not found");
        }
        List<String> lines = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        if (lines.size() == 0) {
            log.warning("*** readFileData() file cannot be empty");
            throw new EmptyFileException("File has no operations to perform calculations");
        } else {
           checkIfLinesAreCorrect(lines);
        }
        return lines;
    }

    private void checkIfLinesAreCorrect(List<String> lines) throws InvalidOperationException {
        if (lines.size() == 1) {
            String[] tab = lines.get(0).split(Utils.NUMBER_SPLITTER);
            if (tab.length < 2) {
                log.warning("**checkIfLinesAreCorrect() file line is not splitted");
                throw new InvalidOperationException("Given operation was not splitted!");
            } else {
                if (!tab[0].equalsIgnoreCase(Utils.APPLY)) {
                    log.warning("*** checkIfLinesAreCorrect() file operation is invalid");
                    throw new InvalidOperationException("Given operations is not valid! It should be: "+Utils.APPLY);
                } else {
                    if (!Utils.isStringDigit(tab[1])) {
                        log.warning("*** checkIfLinesAreCorrect() char sequence is not a digit");
                        throw new NumberFormatException("Char sequence must be a digit!");
                    }
                }
            }
        } else {
            int iterator = 0;
            for (String line : lines) {
                if (line.trim().equals("")) {
                    log.warning("*** checkIfLinesAreCorrect() line "+(iterator+1)+" is empty");
                    throw new InvalidOperationException("File line "+(iterator+1)+" cannot be empty");
                }
                String[] tab = line.split(Utils.NUMBER_SPLITTER);
                if (tab.length < 2) {
                    log.warning("***checkIfLinesAreCorrect() file line number:"+ (iterator+1) +" is not splitted");
                    throw new InvalidOperationException("Given operation at line "+ (iterator+1) +" was not splitted!");
                } else {
                    if (!tab[0].equalsIgnoreCase(Utils.APPLY) && !tab[0].equalsIgnoreCase(Utils.MULTIPLY) && !tab[0].equalsIgnoreCase(Utils.DIVIDE)
                        && !tab[0].equalsIgnoreCase(Utils.ADD) && !tab[0].equalsIgnoreCase(Utils.SUBTRACT)) {
                        log.warning("*** checkIfLinesAreCorrect() file operation is invalid");
                        throw new InvalidOperationException("Given operation at line "+ (iterator+1) +" is not valid!");
                    } else {
                        if (tab[0].equalsIgnoreCase(Utils.APPLY)) {
                            if (iterator != lines.size() - 1) {
                                log.warning("*** checkIfLinesAreCorrect() apply operation must");
                                throw new InvalidOperationException("Apply operation must occur only once in file as the last operation!");
                            }
                        }
                        if (!Utils.isStringDigit(tab[1])) {
                            log.warning("*** checkIfLinesAreCorrect() char sequence is not a digit");
                            throw new NumberFormatException("Char sequence at line "+ (iterator+1) +" must be a digit!");
                        }
                    }
                }
                iterator++;
            }
        }
        log.info("*** checkIfLinesAreCorrect() validation process end with success");
    }

}

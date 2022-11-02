package service;

import model.ValidationInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.nio.file.Files.createFile;

public class FileService {

    private final String FINISH_STRING = "0";
    private final Path PATH_TO_GENERAL_FILE = Path.of("C:\\Users\\user\\Desktop\\JAVA_19_09\\documents\\General.txt");

    private Validator validator;

    public FileService() {
        this.validator = new Validator();
    }

    public List<String> fillListWithFiles() {
        List<String> listOfPathsToFiles = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("First of all we need to fill list of files. If you finish input \"" + FINISH_STRING + "\"  and press ENTER.");
        String filePath = "";
        while (!isInputFinished(filePath)) {
            System.out.println("Input full file's name: ");
            filePath = scanner.nextLine();
            if (isInputFinished(filePath)) {
                return listOfPathsToFiles;
            }
            boolean isCorrectFilePath = validator.validatePath(filePath);
            if (!isCorrectFilePath) {
                System.out.println("Error: incorrect path to file. Repeat input: ");
                filePath = scanner.nextLine();
            } else {
                listOfPathsToFiles.add(filePath);
            }
        }
        return listOfPathsToFiles;
    }

    private boolean isInputFinished(String input) {
        return input.equals(FINISH_STRING);
    }

    public void handleFiles(List<String> pathsToFiles) {
        try {
        List<Map<String, ValidationInfo>> list = new ArrayList<>();
        for (String pathToFile : pathsToFiles) {
            Map<String, ValidationInfo> stringValidationInfoMap = handleSingleDocument(pathToFile);
            list.add(stringValidationInfoMap);
        }
        Path generalFilePath = createFile(PATH_TO_GENERAL_FILE);
        writeDataToFile(generalFilePath, list);
        } catch (Exception exception) {
            System.out.println("Exception while handling files: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void writeDataToFile(Path fileToWritePath, List<Map<String, ValidationInfo>> data) throws IOException {
        List<String> strings = new ArrayList<>();
        for (Map<String, ValidationInfo> element : data) {
            String s = convertValidationMapToStrings(element);
            strings.add(s);
        }
        Files.writeString(fileToWritePath, collapseToOneString(strings));
    }

    private String collapseToOneString(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }

    private String convertValidationMapToStrings(Map<String, ValidationInfo> map) {
        StringBuilder stringBuilder = new StringBuilder();
        map.keySet()
                .forEach(line -> stringBuilder.append(line)
                        .append(" -> ")
                        .append(map.get(line).toString())
                        .append("\n"));
        return stringBuilder.toString();
    }

    private Map<String, ValidationInfo> handleSingleDocument(String filePath) {
        Set<String> fileLines = readAllLinesToSet(filePath);
        return putValidLinesToMap(fileLines);
    }

    private Map<String, ValidationInfo> putValidLinesToMap(Set<String> fileLines) {
        Map<String, ValidationInfo> map = new HashMap<>();
        for (String line : fileLines) {
            ValidationInfo validationInfo = validator.isValidLine(line);
            map.put(line,  validationInfo);
        }
        return map;
    }

    private Set<String> readAllLinesToSet(String filePath) {
        try (Stream<String> lines = Files.lines(Path.of(filePath))) {
            return lines.collect(Collectors.toSet());
        } catch (IOException exception) {
            System.out.println("Exception while reading information from file: " + filePath);
            exception.printStackTrace();
        }
        return Set.of();
    }
}

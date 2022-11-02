package service;

import model.ValidationInfo;

import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {
    private static final String DOCNUM = "docnum";
    private static final String KONTRACT = "kontract";

    private static final int VALID_LINE_LENGTH = 15;

    public boolean validatePath(String fullPath) {
        Path pathToFile = Path.of(fullPath);
        boolean isExists = Files.exists(pathToFile);
        boolean isDirectory = Files.isDirectory(pathToFile);
        boolean isCorrect = isExists && !isDirectory;
        return isCorrect;
    }

    public ValidationInfo isValidLine(String line) {
        boolean startsWithDocNum = line.startsWith(DOCNUM);
        boolean startsWithKontract = line.startsWith(KONTRACT);
        String description = ValidationInfo.EMPTY_DESCRIPTION;

        if ((startsWithDocNum || startsWithKontract)) {
            if (line.length() != VALID_LINE_LENGTH) {
                description = "Line has NOT valid length.";
                return new ValidationInfo(false, description);
            }
        } else {
            description = "Line starts NOT with one of allowable phrases.";
            return new ValidationInfo(false, description);
        }
        return new ValidationInfo(true, description);
    }
}

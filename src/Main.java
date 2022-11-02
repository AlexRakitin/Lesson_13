import service.FileService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FileService fileService = new FileService();
        List<String> pathsToFilesList = fileService.fillListWithFiles();
        fileService.handleFiles(pathsToFilesList);
    }
}
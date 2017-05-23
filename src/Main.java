

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;




import static org.junit.Assert.*;

/**
 * Created by 1081_1 on 16.05.17.
 */



public class Main {
    static public SearchResult SearchInFolder(String dir, String filename, boolean searchInSubFolders) {
        File folder = new File(dir);
        if (folder.isDirectory() && folder != null && folder.listFiles() != null) {
            for (int i = 0; i < folder.listFiles().length; i++) {
                if (folder.listFiles()[i].isDirectory() && SearchInFolder(folder.listFiles()[i].getPath(), filename, searchInSubFolders).found)
                    return new SearchResult(folder.listFiles()[i].getPath(), true);
                if (folder.listFiles()[i].isFile() && folder.listFiles()[i].getName().equals(filename))
                    return new SearchResult(folder.getPath(), true);
            }
        }
        return new SearchResult();
    }

    static public SearchResult Find(String[] args) {
        SearchResult found = new SearchResult();
        System.out.println("Lab 2 started!");
        if (args.length == 4) {
            if (args[0] == "-r" && args[1] == "-d") {
                String dir = args[2], filename = args[3];
                File file = new File(dir);
                return SearchInFolder(file.getPath(), filename, true);

            }
        } else if (args.length == 3) {
            if (args[0] == "-d") {
                return SearchInFolder(args[1], args[2], false);
            }
        } else if (args.length == 1) {
            Path currentRelativePath = Paths.get("");
            String dir = currentRelativePath.toAbsolutePath().toString();
            return SearchInFolder(dir, args[0], false);
        } else {
            System.out.println("Неверное указаны параметры!");
        }
        return found;
    }


    public static void main(String[] args) {
        System.out.print(Find(args).Print());
    }
}

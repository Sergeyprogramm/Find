
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static org.junit.Assert.*;

/**
 * Created by Александр on 11.05.2017.
 */
public class MainTest {
    String filename = "";
    String dir = "";
    String dirs[];

    public void createFile(String dir, String fileName) throws Exception {
        File file = new File(dir + "\\" + fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File named " + fileName
                        + " created successfully !");
            } else {
                System.out.println("File with name " + fileName
                        + " already exixts !");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDir(String dir) throws Exception {
        File file = new File(dir);
        if (!file.exists()) {
            if (file.mkdir()) {
                System.out.println("Directory is created!");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
    }

    public void deleteDir(String dir) throws Exception {
        try {
            File file = new File(dir);
            Path fp = file.toPath();
            String[] entries = file.list();
            for (String s : entries) {
                File currentFile = new File(file.getPath(), s);
                currentFile.delete();
            }
            Files.delete(fp);
            System.out.println("Directory is removed!");
        } catch (NoSuchFileException x) {
            System.err.format("%s: no such" + " file or directory%n", dir);
        } catch (DirectoryNotEmptyException x) {
            System.err.format("%s not empty%n", dir);
        } catch (IOException x) {
            System.err.println(x);
        }
    }

    public void deleteFile(String dir, String fileName) throws Exception {
        try {
            File fileToDelete = new File(dir + fileName);
            if (fileToDelete.delete()) {
                System.out.println("File deleted successfully !");
            } else {
                System.out.println("File delete operation failed !");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @org.junit.Before
    public void setUp() throws Exception {
        filename = "target.txt";
        dir = "C:\\Users\\Сергей\\Desktop\\";
        dirs = new String[]
                {
                        "C:\\Users\\Сергей\\Desktop\\N1",
                        "C:\\Users\\Сергей\\Desktop\\N2",
                        "C:\\Users\\Сергей\\Desktop\\N3",
                        "C:\\Users\\Сергей\\Desktop\\N4"
                };
    }

    @org.junit.After
    public void exit() throws Exception {
        for (int i = 0; i < dirs.length; i++) {
            File file = new File(dirs[i]);
            if (file.exists())
                deleteDir(dirs[i]);
        }
        File file = new File(dir + filename);
        if (file.exists())
            deleteFile(dir, filename);
    }

    @org.junit.Test
    public void searchInFolder() throws Exception {
        for (int i = 0; i < dirs.length; i++) {
            createDir(dirs[i]);
            createFile(dirs[i], filename);
            org.junit.Assert.assertEquals(Main.SearchInFolder(dirs[i], filename, false).found, true);
        }
    }

    @org.junit.Test
    public void find() throws Exception {
        org.junit.Assert.assertEquals(Main.Find(new String[]{"target.txt"}).Print(), "Файл не найден");
        createFile(dir, filename);
        org.junit.Assert.assertEquals(Main.Find(new String[]{"-d", "C:\\Users\\Сергей\\Desktop", "target.txt"}).Print(), "Файл найден: C:\\Users\\Сергей\\Desktop");
        deleteFile(dir,filename);
        createDir(dirs[0]);
        createFile(dirs[0], filename);
        org.junit.Assert.assertEquals(Main.Find(new String[]{"-r", "-d", "C:\\Users\\Сергей\\Desktop", "target.txt"}).Print(), "Файл найден: C:\\Users\\Сергей\\Desktop\\N1");
    }

}
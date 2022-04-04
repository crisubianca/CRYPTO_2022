package tema3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class HandleFiles {

    public HandleFiles() {

    }

    public void zipFile(String sourceFile, FileOutputStream fos) throws IOException {
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);

        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
    }

    public long getSize(String filePath){
        Path path = Paths.get(filePath);
        long bytes = 0;
        try {
            bytes = Files.size(path);
        } catch (IOException e) {
            System.out.println("Couldn't display file size.");
        }
        return bytes;
    }

    public String sizeToString(String filePath, long fileSize){
        String s = "";
        s = s + String.valueOf(fileSize) + " bytes for " + filePath;
        return s;
    }
}

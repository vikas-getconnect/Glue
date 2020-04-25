package com.atf.helpers;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.gargoylesoftware.htmlunit.javascript.host.canvas.WebGLRenderingContext.BUFFER_SIZE;

public class DownloadHelper {
    private static final String chromeUrl_mac="https://chromedriver.storage.googleapis.com/2.42/chromedriver_mac64.zip";
    private static final String downlaoadPath="/Users/vikaschaudhary/workspace/Glue/src/main/resources/drivers/";

    public void downloadDriver(){
        try {
            URL url = new URL(chromeUrl_mac);
            URLConnection conn = url.openConnection();
            InputStream in = conn.getInputStream();
            FileOutputStream out = new FileOutputStream(downlaoadPath + "chromedriver.zip");
            byte[] b = new byte[1024];
            int count;
            while ((count = in.read(b)) >= 0) {
                out.write(b, 0, count);
            }
            out.flush(); out.close(); in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extracts a zip entry (file entry)
     * @param zipIn
     * @param filePath
     * @throws IOException
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    public void makeFileExecutable(String fileName){
        File file = new File(fileName);
        if(file.exists()) {
            file.setExecutable(true);
            file.setReadable(true);
            file.setWritable(true);
        }
    }

    public static void main(String[] args) throws IOException {
        DownloadHelper dh=new DownloadHelper();
        dh.downloadDriver();
        dh.unzip(downlaoadPath + "chromedriver.zip",downlaoadPath);
        dh.makeFileExecutable(downlaoadPath + "chromedriver");
    }

}

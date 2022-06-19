package com.github.bambrikii.gradle.virtualization.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

public class IOUtils {
    public static String toString(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is)) {
            return toString(isr);
        }
    }

    public static String toString(InputStreamReader inputStreamReader) {
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(inputStreamReader)) {
            int c;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to read stream", ex);
        }
        return textBuilder.toString();
    }

    public static void toFile(String template, File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             OutputStreamWriter osw = new OutputStreamWriter(fos)
        ) {
            osw.write(template);
        }
    }
}

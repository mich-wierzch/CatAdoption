package com.CatShelter.CatShelter.data;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

@Getter
public class CatImageFileTestData {
    public static final String imageFile;

    static {
        try (InputStream inputStream = CatImageFileTestData.class.getResourceAsStream("/catTestImage.txt");
             Scanner scanner = new Scanner(inputStream, "UTF-8")) {
            imageFile = scanner.useDelimiter("\\A").next();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
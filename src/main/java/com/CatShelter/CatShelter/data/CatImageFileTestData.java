package com.CatShelter.CatShelter.data;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Getter
public class CatImageFileTestData {
    public static final String imageFile;

    static {
        try (InputStream inputStream = CatImageFileTestData.class.getResourceAsStream("/catTestImage.txt")) {
            assert inputStream != null;
            try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
                imageFile = scanner.useDelimiter("\\A").next();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
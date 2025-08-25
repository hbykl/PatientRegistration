package com.example.PatientRegistration.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.PatientRegistration.domain.model.Patients;

public class FileService {
    private final Path baseDir = Paths.get(System.getProperty("user.dir"), "var", "data");

    public Path getBaseDir() {
        return baseDir;
    }

    public boolean ensureFile(String fileName) {
        Path filePath = baseDir.resolve(fileName);
        File file = filePath.toFile();

        try {
            File parent = file.getCanonicalFile().getParentFile();
            if (parent != null) {
                if (parent.exists()) {
                    if (!parent.isDirectory()) {
                        System.out.println("Parent bir dizin değil: " + parent.getAbsolutePath());
                        return false;
                    }
                } else if (!parent.mkdirs()) {
                    System.out.println("Klasör(ler) oluşturulamadı: " + parent.getAbsolutePath());
                    return false;
                }
            }

            if (file.exists()) {
                if (file.isDirectory()) {
                    System.out.println("Aynı isimde klasör var: " + file.getAbsolutePath());
                    return false;
                }
                return true;
            }

            if (file.createNewFile()) {
                System.out.println("Dosya oluşturuldu: " + file.getAbsolutePath());
                return true;
            } else {
                System.out.println("Dosya oluşturulamadı: " + file.getAbsolutePath());
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void appendPatientToFile(String fileName, HashMap<String, Patients> patients) {
        if (!ensureFile(fileName)) {
            System.out.println("Dosya oluşturulamadı.");
            return;
        }

        Path path = baseDir.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {

            for (Map.Entry<String, Patients> entry : patients.entrySet()) {
                String json = PatientJsonCodec.toNdjson(entry.getValue());
                writer.write(json);
                writer.newLine();
                System.out.println("Hasta dosyaya yazıldı: " + entry.getKey());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> readAllLinesFromFile(String fileName) {

        Path filePath = baseDir.resolve(fileName);

        if (!Files.exists(filePath)) {
            System.out.println("Dosya bulunamadı: " + filePath);
            ensureFile(fileName);
            return Collections.emptyList();
        }

        try {
            return Files.readAllLines(filePath, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void clearFile(String fileName) throws IOException {
        Path filePath = baseDir.resolve(fileName);

        if (!Files.exists(filePath)) {
            System.out.println("Dosya bulunamadı: " + filePath);
            return;
        }
        Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING).close();

    }

}

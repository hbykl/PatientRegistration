package com.example.PatientRegistration.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

public class FileService {
    String userDir = System.getProperty("user.dir");
    String directoryPath = userDir + "/var/data/";
    PatientJsonCodec codec;

    public boolean ensureFile(String directoryPath, String fileName) {
        File file = new File(directoryPath, fileName);
        try {
            File parent = file.getCanonicalFile().getParentFile(); // gerçek üst dizin
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
                System.out.println("Dosya zaten mevcut: " + file.getAbsolutePath());
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

    public void appendPatientToFile(String fileName, HashMap<String, String> patient) {

        if (!ensureFile(directoryPath, fileName)) {
            System.out.println("Kullanıcı işlemi gerçekleştirilemedi.");
            return;
        }
        Path baseDir = Paths.get(System.getProperty("user.dir"), "var", "data");
        Path path = baseDir.resolve(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                path,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND))

        {
            for (Map.Entry<String, String> entry : patient.entrySet()) {
                writer.write(entry.getValue());
                writer.newLine();
                System.out.println("Kullanıcı dosyaya yazıldı");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

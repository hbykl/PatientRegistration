package com.example.PatientRegistration.application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.PatientRegistration.domain.model.Patients;

public class FileService {
    String userDir = System.getProperty("user.dir");
    String directoryPath = userDir + "/var/data/";

    public boolean createFile(String fileName) {

        try {
            File file = new File(directoryPath);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    System.out.println(fileName + " dosyası oluşturuldu");
                    return true;
                } else {
                    System.out.println("Dosya oluşturulamadı");
                    return false;
                }
            } else {
                System.out.println(fileName + " dosyası mevcut");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}

package com.example.PatientRegistration.application;

import java.util.HashMap;
import java.util.UUID;

import com.example.PatientRegistration.domain.model.Patients;

public class PatientsService {
    HashMap<String, String> patientsMap = new HashMap<String, String>();
    FileService fileService = new FileService();
    PatientJsonCodec patientJsonCodec;

    public void createPatient(String fileName, String name, String surname, int age, String phone, String email) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        Patients patient = new Patients(uuidAsString, name, surname, age, phone, email);
        patientsMap.put(uuidAsString, PatientJsonCodec.toNdjson(patient));
    }

    public HashMap<String, String> getPatiens() {
        return patientsMap;
    }

}

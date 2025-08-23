package com.example.PatientRegistration.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.example.PatientRegistration.domain.model.Patients;

public class PatientsService {
    private HashMap<String, Patients> patientsMap = new HashMap<String, Patients>();

    public void loadPatientsFromFile(String filename) {
        FileService fileService = new FileService();
        List<String> lines = fileService.readAllLinesFromFile(filename);
        for (String line : lines) {
            try {
                Patients patient = PatientJsonCodec.fromNdjson(line);
                patientsMap.put(patient.getId(), patient);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createPatient(String name, String surname, int age, String phone, String email) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        Patients patient = new Patients(uuidAsString, name, surname, age, phone, email);
        patientsMap.put(uuidAsString, patient);
    }

    public void deletePatient(String id) {
        if (!patientsMap.containsKey(id)) {
            System.out.println("Verilen değere ait hasta bulunamadı");
            return;
        }
        patientsMap.remove(id);
    }

    public List<Patients> getAllPatients() {
        return new ArrayList<>(patientsMap.values());
    }

    public void updatePatient(Patients patient,
            String name,
            String surname,
            int age,
            String phone,
            String email) {
        if (!patientsMap.containsKey(patient.getId())) {
            System.out.println("Verilen değere ait hasta bulunamadı");
            return;
        }
        Patients updatedPatient = new Patients(patient.getId(), name, surname, age, phone, email);
        patientsMap.put(patient.getId(), updatedPatient);

    }

}

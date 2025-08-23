package com.example.PatientRegistration.application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.PatientRegistration.domain.model.Patients;

@Service
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

    public void savePatientsToFile(String filename) {
        FileService fs = new FileService();
        fs.ensureFile(filename);
        try {
            fs.clearFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fs.appendPatientToFile(filename, patientsMap);
        patientsMap.clear();
    }

    public Patients createPatient(String name, String surname, int age, String phone, String email) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        Patients patient = new Patients(uuidAsString, name, surname, age, phone, email);
        patientsMap.put(uuidAsString, patient);
        return patient;
    }

    public boolean deletePatient(String id) {
        if (!patientsMap.containsKey(id)) {
            System.out.println("Verilen değere ait hasta bulunamadı");
            return false;
        }
        patientsMap.remove(id);
        return true;
    }

    public List<Patients> getAllPatients() {
        return new ArrayList<>(patientsMap.values());
    }

    public Optional<Patients> getPatientById(String id) {
        return Optional.ofNullable(patientsMap.get(id));
    }

    public Patients updatePatient(String id, String name, String surname, int age, String phone, String email) {
        Patients cur = patientsMap.get(id);
        if (cur == null)
            return null;
        Patients updated = new Patients(id, name, surname, age, phone, email);
        patientsMap.put(id, updated);
        return updated;
    }

}

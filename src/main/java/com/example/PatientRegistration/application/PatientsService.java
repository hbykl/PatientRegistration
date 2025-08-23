package com.example.PatientRegistration.application;

import java.util.HashMap;
import java.util.UUID;

import com.example.PatientRegistration.domain.model.Patients;

public class PatientsService {
    HashMap<String, Patients> Patients = new HashMap<String, Patients>();

    public void createPatient(String name, String surname, String phone, String email) {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        Patients patient = new Patients(name, surname, 0, phone, email);
        Patients.put(uuidAsString, patient);

    }

    public void modelToText(Patients patient) {

    }

}

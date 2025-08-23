package com.example.PatientRegistration.application;

import com.example.PatientRegistration.domain.model.Patients;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class PatientJsonCodec {
    private static final ObjectMapper MAPPER = new ObjectMapper().findAndRegisterModules();

    public PatientJsonCodec() {
    }

    public static String toNdjson(Patients p) {
        try {
            return MAPPER.writeValueAsString(p);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Patients JSON serileştirme hatası", e);
        }
    }

    public static Patients fromNdjson(String line) {
        try {
            return MAPPER.readValue(line, Patients.class);
        } catch (Exception e) {
            throw new IllegalStateException("Patients JSON parse hatası", e);
        }
    }
}

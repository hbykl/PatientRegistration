package com.example.PatientRegistration.web.controller;

import com.example.PatientRegistration.application.PatientsService;
import com.example.PatientRegistration.domain.model.Patients;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientsController {

    private final PatientsService patientsService;

    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @PostMapping
    public Patients createPatient(@RequestBody Patients patientRequest) {
        return patientsService.createPatient(
                patientRequest.getName(),
                patientRequest.getSurname(),
                patientRequest.getAge(),
                patientRequest.getPhone(),
                patientRequest.getEmail());
    }

    @GetMapping
    public List<Patients> getAllPatients() {
        return patientsService.getAllPatients();
    }

    @GetMapping("/{id}")
    public Patients getPatientById(@PathVariable String id) {
        return patientsService.getPatientById(id)
                .orElseThrow(() -> new RuntimeException("Hasta bulunamadı: " + id));
    }

    @PutMapping("/{id}")
    public Patients updatePatient(@PathVariable String id, @RequestBody Patients patientRequest) {
        return patientsService.updatePatient(
                id,
                patientRequest.getName(),
                patientRequest.getSurname(),
                patientRequest.getAge(),
                patientRequest.getPhone(),
                patientRequest.getEmail());
    }

    @DeleteMapping("/{id}")
    public String deletePatient(@PathVariable String id) {
        boolean deleted = patientsService.deletePatient(id);
        if (!deleted) {
            throw new RuntimeException("Hasta bulunamadı: " + id);
        }
        return "Hasta silindi: " + id;
    }

    @PostMapping("/load")
    public String loadPatients(@RequestParam String fileName) {
        patientsService.loadPatientsFromFile(fileName);
        return "Veriler dosyadan yüklendi.";
    }

    @PostMapping("/save")
    public String savePatients(@RequestParam String fileName) {
        patientsService.savePatientsToFile(fileName);
        return "Veriler dosyaya kaydedildi.";
    }
}

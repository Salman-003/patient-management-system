package com.pm.appointment_service.client;

@FeignClient(name = "patient-service")
public interface PatientClient {

    @GetMapping("/patients/{id}")
    Patient getPatientById(@PathVariable("id") Long id);
}

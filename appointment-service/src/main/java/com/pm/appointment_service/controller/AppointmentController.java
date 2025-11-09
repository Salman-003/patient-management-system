package com.pm.appointment_service.controller;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired private AppointmentRepository repo;
    @Autowired private PatientClient patientClient;

    @GetMapping
    public List<Appointment> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public AppointmentDetail getById(@PathVariable Long id) {
        Appointment appt = repo.findById(id).orElse(null);
        if (appt == null) return null;
        Patient patient = patientClient.getPatientById(appt.getPatientId());
        // Return combined data
        return new AppointmentDetail(appt.getId(), patient.getName(), appt.getDate());
    }

    @PostMapping
    public Appointment create(@RequestBody Appointment appt) {
        return repo.save(appt);
    }

    @PutMapping("/{id}")
    public Appointment update(@PathVariable Long id, @RequestBody Appointment appt) {
        appt.setId(id);
        return repo.save(appt);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repo.deleteById(id);
    }

    // DTO class for combined response
    static class AppointmentDetail {
        public Long appointmentId;
        public String patientName;
        public String date;
        public AppointmentDetail(Long aId, String name, String date) {
            this.appointmentId = aId;
            this.patientName = name;
            this.date = date;
        }
    }
}

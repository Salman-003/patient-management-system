package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExistsException;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    //get patient records
    public List<PatientResponseDTO> getPatients(){
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponses = patients.stream().map(patient -> PatientMapper.toDTO(patient)).toList();
        return patientResponses;
    }

    //create patient record
    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toDTO(newPatient);
    }

    //update patient record
    public PatientResponseDTO updatePatient(UUID id, PatientRequestDTO patientRequestDTO){

        //validation - while updating patient we shouldnot allow duplicate email with different id
        if(patientRepository.existsByEmailAndIdNot(patientRequestDTO.getEmail(), id)){
            throw new EmailAlreadyExistsException("A patient with this email already exists" + patientRequestDTO.getEmail());
        }
       Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException("Patient not found with ID: " +
               id));

       patient.setName(patientRequestDTO.getName());
       patient.setEmail(patientRequestDTO.getEmail());
       patient.setAddress(patientRequestDTO.getAddress());
       patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDateOfBirth()));
       Patient updatePatient = patientRepository.save(patient);
       return PatientMapper.toDTO(updatePatient);
    }

    //delete patient record
    public void deletePatient(UUID id){
        patientRepository.deleteById(id);
    }

}

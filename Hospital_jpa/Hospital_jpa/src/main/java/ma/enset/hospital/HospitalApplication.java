package ma.enset.hospital;

import ma.enset.hospital.entities.*;
import ma.enset.hospital.repositories.ConsulttaionRepository;
import ma.enset.hospital.repositories.MedecinRepository;
import ma.enset.hospital.repositories.PatientRepository;
import ma.enset.hospital.repositories.RendezVousRepository;
import ma.enset.hospital.service.HospitalServiceImpl;
import ma.enset.hospital.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;
import java.lang.Math.*;


@SpringBootApplication
public class HospitalApplication  {

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}
@Bean
	CommandLineRunner start(IHospitalService hospitalService,PatientRepository patientRepository,
							RendezVousRepository rendezVousRepository,MedecinRepository medecinRepository){
		return args -> {
			Stream.of("Ouaklim","Bikki","Moustakim")
					.forEach(name->{
								Patient patient=new Patient();
								patient.setNom(name);
								patient.setMalade(false);
								patient.setDateNaissance(new Date());
								patient.setScore(1);
								patient.setRendezVous(null);
								hospitalService.savePatient(patient);
							});

			Stream.of("Houmam","Bouafia","Abofariss")
					.forEach(name->{
						Medecin medecin=new Medecin();
						medecin.setNom(name);
						medecin.setEmail(name+"@gmail.com");
						medecin.setSpecialte(Math.random()>0.5?"Génicologue":"Dentiste");
						hospitalService.saveMedecin(medecin);
					});
			Patient patient=patientRepository.findById(1L).orElse(null);
			Patient patient1=patientRepository.findByNom("Ouaklim");

			Medecin medecin=medecinRepository.findById(1L).orElse(null);

			RendezVous rendezVous=new RendezVous();
			rendezVous.setStatus(StatusRDV.PENDING);
			rendezVous.setDate(new Date());
			rendezVous.setMedecin(medecin);
			rendezVous.setPatient(patient);
			RendezVous saveRDV =hospitalService.saveRendezVous(rendezVous);
			System.out.println(saveRDV.getId());


			RendezVous rendezVous1=rendezVousRepository.findAll().get(0);
			Consultation consultation=new Consultation();
			consultation.setDateConsultation(new Date());
			consultation.setRendezVous(rendezVous1);
			consultation.setRapport("le rapport du consultation.........");
			hospitalService.saveConsultation(consultation);


		};
}

}
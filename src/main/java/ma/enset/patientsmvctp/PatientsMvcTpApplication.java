package ma.enset.patientsmvctp;

import ma.enset.patientsmvctp.entities.Patient;
import ma.enset.patientsmvctp.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcTpApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientsMvcTpApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"eraoui ahmed",new Date(),false,21));
            patientRepository.save(new Patient(null,"yassine el hanafi",new Date(),true,31));
            patientRepository.save(new Patient(null,"salah zekara",new Date(),false,26));
            patientRepository.findAll().forEach(p-> System.out.println(p.getName()));
        };
    }

}

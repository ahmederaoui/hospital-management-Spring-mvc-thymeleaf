package ma.enset.patientsmvctp.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.patientsmvctp.entities.Patient;
import ma.enset.patientsmvctp.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller @AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page",defaultValue = "0") int page,
                           @RequestParam(name = "size",defaultValue = "5") int size,
                           @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepository.findByNameContains(keyword,PageRequest.of(page,size));
        model.addAttribute("listPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping(path = "/admin/delete")
    public String delete(long id, String keyword, int page ){
        patientRepository.deleteById(id);
        return "redirect:/user/index?keyword="+keyword+"&page="+String.valueOf(page);
    }
    @GetMapping(path = "/admin/formPatients")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/save")
    public String save(Model model, @Valid Patient patient, BindingResult bindingResult,
                       @RequestParam(name = "keyword",defaultValue = "") String keyword
            , @RequestParam(name = "page", defaultValue = "0")int page){
        if(bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);
        return "redirect:/user/index?keyword="+keyword+"&page="+page;
    }
    @GetMapping("/admin/editPatients")
    public String edit(Model model,Long id,String keyword, int page){
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) return "redirect:/index";
        model.addAttribute("patient",patient);
        model.addAttribute("keyword",keyword);
        model.addAttribute("page",page);
        return "editPatients";
    }
    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}

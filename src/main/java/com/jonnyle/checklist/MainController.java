package com.jonnyle.checklist;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class MainController {
    @Autowired
    private CheckInRepo checkInRepo;

    @Autowired
    private SchoolRepo schoolRepo;

    @ModelAttribute(name = "unchecked")
    public List<School> allUnchecked(){
        List<School> unchecked = new ArrayList<>();
        for(School school : this.schoolRepo.findAll()){
            if(this.checkInRepo.findById(school.getTea()).isPresent())
                continue;
            unchecked.add(school);
        }
        return unchecked;
    }

    @ModelAttribute(name = "schools")
    public List<School> allSchool(){
        return (List<School>) this.schoolRepo.findAll();
    }

    @ModelAttribute(name = "checkedIn")
    public List<CheckIn> allCheckIn(){
        return (List<CheckIn>) this.checkInRepo.findAll();
    }

    @PostMapping(value = "/aci")
    public String checkInSchool(@RequestParam(name = "schools") String school,
                                @Valid @ModelAttribute CheckIn checkIn,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors())
            return "aci";
        if(checkIn.getBox()==null)
            checkIn.setBox(0);
        if(checkIn.getEnvelope()==null)
            checkIn.setEnvelope(0);
        if(checkIn.getBox() == 0 && checkIn.getEnvelope() == 0)
            return "aci";
        if(school.equals(""))
            return "aci";
        String parse = school.replaceAll("[()]","");
        String[] token = parse.split(" ");
        checkIn.setSchool(schoolRepo.findById(Integer.parseInt(token[0])).get());
        checkInRepo.save(checkIn);
        return "redirect:/aci";
    }

    @GetMapping(value = "/aci")
    public String home(@ModelAttribute CheckIn checkIn){
        checkIn.setBox(0);
        checkIn.setEnvelope(0);
        return "aci";
    }

    @GetMapping(value = "/addSchool")
    public String insert(@ModelAttribute School school){
        return "insert";
    }

    @PostMapping(value = "/addSchool")
    public String newSchool(@Valid @ModelAttribute School school, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "insert";
        }
        schoolRepo.save(school);
        return "redirect:/addSchool";
    }

    @GetMapping(value = "/editCheckIn")
    public String manageCheckIn(){
        return "edit";
    }
    
    @GetMapping(value = "/editCheckIn/{tea}")
    public String showEditCheckIn(@PathVariable Integer tea, Model model) {
    	model.addAttribute("checkIn", checkInRepo.findById(tea).get());
    	return "editTea";
    }
    
    @PostMapping(value = "/editCheckIn/{tea}")
    public String editCheckIn(@Valid @ModelAttribute CheckIn checkIn, BindingResult bindingResult, Model model, @PathVariable Integer tea) {
    	if(bindingResult.hasErrors()) {
    		model.addAttribute("checkIn", checkInRepo.findById(tea).get());
    		return "editTea";
    	}
    	checkInRepo.save(checkIn);
    	return "redirect:/editCheckIn";
    }
    
    @PostMapping(value = "/editCheckIn", params = "deleteAll")
    public String deleteAllChecked() {
    	checkInRepo.deleteAll();
    	return "redirect:/editCheckIn";
    }
    
    @GetMapping(value = "/delete/{tea}")
    public String deleteCheckIn(@PathVariable Integer tea) {
    	checkInRepo.deleteById(tea);
    	return "redirect:/editCheckIn";
    }
}
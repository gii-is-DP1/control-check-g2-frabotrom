package org.springframework.samples.petclinic.feeding;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/feeding")
public class FeedingController {

    @Autowired
    private FeedingService feedingService;

    @Autowired
    private PetService petService;

    private static final String VIEWS_FEEDING_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";
    
    @GetMapping(path = "/create")
	public String initCreationForm(Feeding feeding, ModelMap model) {
		String view = VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
        model.addAttribute("feeding", new Feeding());
        model.addAttribute("feedingType", feedingService.getAllFeedingTypes());
        model.addAttribute("pets", petService.getAllPets());
		return view;
	}
    @PostMapping(path = "/create")
	public String processCreationForm(@Valid Feeding feeding, BindingResult result,ModelMap model) throws UnfeasibleFeedingException {
        if(result.hasErrors()) {
            model.addAttribute("feeding", new Feeding());
            model.addAttribute("feedingType", feedingService.getAllFeedingTypes());
            model.addAttribute("pets", petService.getAllPets());
        } else {
            feedingService.save(feeding);
            model.addAttribute("message", "Feeding succesfully saved!");
            return VIEWS_FEEDING_CREATE_OR_UPDATE_FORM;
        }
		return "redirect:/welcome";
	}

}

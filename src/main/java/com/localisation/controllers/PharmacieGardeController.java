package com.localisation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.localisation.Services.PharmacieService;
import com.localisation.entities.PharmacieGarde;
import com.localisation.services.PharmacieGardeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/pharmacies/garde")
public class PharmacieGardeController {
	  @Autowired
	    private PharmacieGardeService service;
	   // @Autowired
	   //private PharmacieService pharmacieService;

	    @GetMapping("/all")
	    public List<PharmacieGarde> getAllPharmaciesDeGarde(){
	    	return service.getAllPharmaciesDeGarde();
	    }
	    
	    @GetMapping("/allDispoPharmacies")
	    public List<PharmacieGarde> getAllPharmaciesDeGardeDispo(){
	    	return service.getAllPharmaciesDeGardeDispo();
	    	
	    }
	    @GetMapping("/allDispoPharmacies/garde/{garde_id}")
	    public List<PharmacieGarde> getAllPharmaciesDeGardeDispoByGarde(@PathVariable String garde_id){
	    	return service.getAllPharmaciesDeGardeDispoByGarde(Integer.parseInt(garde_id));
	    	
	    }
}

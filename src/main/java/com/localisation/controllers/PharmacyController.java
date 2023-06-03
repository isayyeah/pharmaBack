package com.localisation.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.User;
import com.localisation.entities.Zone;
import com.localisation.repositories.ZoneRepository;
import com.localisation.services.PharmacyService;
import com.localisation.services.UserService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/pharmacies")
public class PharmacyController {

	@Autowired
	PharmacyService pharmacieService;

	@Autowired
	private ZoneRepository zoneService;
	@Autowired
	private UserService userService;

	@PostMapping("/save/{user_id}")
	public Pharmacy save(@RequestBody Pharmacy p, @PathVariable long user_id) {
		p.setState(0);
		User user = userService.getUserById(user_id);
		p.setUser(user);
		p.setZone(zoneService.findById(1).get()); // the default zone id is 1
		return pharmacieService.save(p);
	}

	@PostMapping("/save")
	public ResponseEntity<?> save(@Valid @RequestBody Pharmacy p, BindingResult bindingResult) {
		// Check for validation errors
		Map<String, String> errors = new HashMap<>();
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
		}

		// Check for zone ID validation errors
		Integer zoneId = p.getZone().getId();
		Optional<Zone> optionalZone = zoneService.findById(zoneId);
		if (optionalZone.isEmpty()) {
			errors.put("zone", "Zone not found");
		} else {
			p.setZone(optionalZone.get());
		}

		// Check if there are any errors
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(errors);
		}

		// Save the pharmacy data if validation passes
		Pharmacy savedPharmacy = pharmacieService.save(p);
		return ResponseEntity.ok(savedPharmacy);
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<?> updatePharmacy(@RequestBody @Valid Pharmacy p, @PathVariable int id, BindingResult bindingResult) {
	    // Check for validation errors
	    if (bindingResult.hasErrors()) {
	        Map<String, String> errors = new HashMap<>();
	        for (FieldError error : bindingResult.getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }
	        return ResponseEntity.badRequest().body(errors);
	    }
	    // Update the pharmacy with the given ID
	    Pharmacy updatedPharmacy = pharmacieService.update(p, id);
	    return ResponseEntity.ok(updatedPharmacy);
	}


	@DeleteMapping("/deletePharmacy/id={id}")
	public void deletePharmacie(@PathVariable int id) {
		Pharmacy ph = pharmacieService.findById(id);
		pharmacieService.delete(ph);
	}

	@GetMapping("/{id}")
	public Pharmacy findById(@PathVariable String id) {
		return pharmacieService.findById(Integer.parseInt(id));
	}
	
	@GetMapping("/all")
	public List<Pharmacy> findAll(){
		return pharmacieService.findAll();
	}

	@GetMapping("/pharmacy/user_id={id}")
	public Pharmacy findPharmacieByUserId(@PathVariable long id) {

		User user = userService.getUserById(id);
		return pharmacieService.findPharmacieByUser(user);
	}

	/*
	 * @GetMapping("/pharmacie/ville={id}") public List<Pharmacie>
	 * findPharmacieByVille(@PathVariable int id) { return
	 * pharmacieService.findAllPharmacieByVille(id); }
	 */

	@GetMapping("/zone/id/{id}")
	public List<Pharmacy> findPharmacieByZone(@PathVariable int id) {
		return pharmacieService.findAllPharmacieByZone(id);
	}

	@GetMapping("")
	public List<Pharmacy> findAllPharmacie() {
		return pharmacieService.findAll();
	}

	@GetMapping("/allWaitList")
	public Optional<List<Pharmacy>> findWaitlistPharmacies() {
		return pharmacieService.findWaitlistPharmacies();
	}

	@GetMapping("/allAccepted")
	public Optional<List<Pharmacy>> findAcceptedPharmacies() {
		return pharmacieService.findAcceptedPharmacies();
	}

	@GetMapping("/allRefused")
	public Optional<List<Pharmacy>> findRefusedPharmacies() {
		return pharmacieService.findRefusedPharmacies();
	}

	@PutMapping("/acceptPharmacy/id={id}")
	public Pharmacy acceptePharmacie(@PathVariable int id) {

		return pharmacieService.acceptPharmacie(id);
	}

	@PutMapping("/refusePharmacy/id={id}")
	public Pharmacy refusPharmacie(@PathVariable int id) {

		return pharmacieService.refusePharmacie(id);
	}

	@PutMapping("/{id}/state")
	public ResponseEntity<String> updatePharmacyState(@PathVariable int id, @RequestBody int state) {
		Pharmacy pharmacy = pharmacieService.findById(id);
		if (pharmacy != null) {
			pharmacy.setState(state);
			pharmacieService.save(pharmacy);
			return ResponseEntity.ok("Pharmacy state updated successfully.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/pharmacy/garde/{id}")
	public List<Pharmacy> findAllPharmacieByGarde(@PathVariable int id) {
		return pharmacieService.findAllPharmacieByGarde(id);
	}

	@GetMapping("/{id}/itineraire")
	public ResponseEntity<?> getItineraire(@PathVariable String id, @RequestParam("depart") String depart)
			throws IOException {
		Pharmacy optionalPharmacie = pharmacieService.findById(Integer.parseInt(id));
		// if (!optionalPharmacie.isPresent()) {
		// return ResponseEntity.notFound().build();
		// }
		Pharmacy pharmacie = optionalPharmacie;

		return ResponseEntity.ok(pharmacieService.getItinerary2(pharmacie, depart));
		// return pharmacieService.getItinerary(pharmacie, depart);
	}

	@GetMapping("/getAddress")
	public double[] myaddress(@RequestParam("depart") String depart) throws IOException {

		return pharmacieService.GeoCoding(depart);
	}

	@GetMapping("/zone/{zone}/garde/{state}")
    public ResponseEntity<List<Pharmacy>> getPharmacies(@PathVariable int zone,@PathVariable int state) {
        List<Pharmacy> pharmacies = pharmacieService.findByZoneAndState(zone, state);
        if (pharmacies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pharmacies);
    }
	
	
}

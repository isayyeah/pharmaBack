package com.localisation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.localisation.entities.City;
import com.localisation.entities.Zone;
import com.localisation.services.CityService;
import com.localisation.services.ZoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//@CrossOrigin("http://localhost:3000")
@Tag(name = "Zone Controller")
@RestController
@RequestMapping("/api/zones")
@CrossOrigin(origins = "*")
public class ZoneController {
	@Autowired
	ZoneService zoneService;
	@Autowired
	CityService villeService;

	@Operation(summary = "add new zone")
	@PostMapping("/save")
	public ResponseEntity<?> save(@RequestParam("name") String nom, @RequestParam("cityId") int ville_id) {
		try {
			City myville = villeService.findById(ville_id);
			Zone myzone = new Zone();
			myzone.setName(nom);
			myzone.setCity(myville);
			zoneService.save(myzone);
			return ResponseEntity.ok(myzone);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("")
	public List<Zone> findAll() {

		return zoneService.findAll();
	}

	@GetMapping("/zone/id={id}")
	public Zone findZoneById(@PathVariable int id) {

		return zoneService.findById(id);
	}

	@GetMapping("city/{id}")
	public List<Zone> findAllZoneByVille(@PathVariable int id) {

		return zoneService.findAllZoneByVille(id);
	}

	@PutMapping("/{id_zone}")
	public Zone updateZone(@RequestParam("name") String newNom, @RequestParam("cityId") int newVille_id,
			@PathVariable int id_zone) {

		City newVille = villeService.findById(newVille_id);

		// zone with new fields
		Zone updatedZone = new Zone();
		updatedZone.setName(newNom);
		updatedZone.setCity(newVille);
		return zoneService.update(updatedZone, id_zone); 

	}

	@DeleteMapping("/deleteZone/id={id}")
	public void deleteZone(@PathVariable int id) {
		Zone zone=zoneService.findById(id);
		zoneService.delete(zone);
	}
	@GetMapping("/Countbyzone/zone={id}")
	public long findNbrPharmacieZone(@PathVariable int id){
		return zoneService.countNbrOfPharmacie(id);
	}
}

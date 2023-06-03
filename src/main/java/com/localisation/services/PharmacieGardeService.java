package com.localisation.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.localisation.entities.Pharmacy;
import com.localisation.entities.PharmacieGarde;
import com.localisation.repositories.PharmacieGardeRepository;

@Service
public class PharmacieGardeService {

	@Autowired
	private PharmacieGardeRepository repository;

	public PharmacieGarde addPharmacieDeGarde(PharmacieGarde p) {

		return repository.save(p);
	}

	public List<PharmacieGarde> getAllPharmaciesDeGarde() {
		return repository.findAll();
	}

	public List<PharmacieGarde> getAllPharmaciesDeGardeByPharmacie(Pharmacy pharmacie) {
		return repository.findAllByPharmacy(pharmacie);
	}

	public List<PharmacieGarde> getAllPharmaciesDeGardeDispo() {
	    LocalDate currentDate = LocalDate.now();
	    
	   ;

	    return repository.findAllPharmacieDispo();
	}


	public List<PharmacieGarde> getAllPharmaciesDeGardeDispoByGarde(int id_garde) {

	
		return repository.findAllPharmacieDispoByGarde(id_garde);
	}
}

package rs.ac.uns.ftn.scientific_center.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rs.ac.uns.ftn.scientific_center.dto.MagazineDTO;
import rs.ac.uns.ftn.scientific_center.service.MagazineService;

import java.util.List;

@RequestMapping(value = "/magazine")
@RestController
public class MagazineController {

    @Autowired
    private MagazineService magazineService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<MagazineDTO>> getAllMagazines(){
        return ResponseEntity.ok().body(magazineService.getAllMagazines());
    }

}

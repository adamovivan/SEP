package rs.ac.uns.ftn.scientific_center.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.scientific_center.dto.MagazineDTO;
import rs.ac.uns.ftn.scientific_center.mapper.MagazineMapper;
import rs.ac.uns.ftn.scientific_center.model.Magazine;
import rs.ac.uns.ftn.scientific_center.repository.MagazineRepository;

import java.util.List;

@Service
public class MagazineService {

    @Autowired
    private MagazineRepository magazineRepository;

    @Autowired
    private MagazineMapper magazineMapper;

    public List<MagazineDTO> getAllMagazines(){
        List<Magazine> magazines = magazineRepository.findAll();
        return magazineMapper.magazinesToMagazineDTOs(magazines);
    }
}

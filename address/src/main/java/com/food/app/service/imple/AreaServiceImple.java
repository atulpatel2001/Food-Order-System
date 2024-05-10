package com.food.app.service.imple;

import com.food.app.dto.AreaDto;
import com.food.app.exception.AllDataNotFoundException;
import com.food.app.exception.AlreadyExistsException;
import com.food.app.exception.ResourceNotFoundException;
import com.food.app.mapper.AreaMapper;
import com.food.app.model.Area;
import com.food.app.repository.AreaRepository;
import com.food.app.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AreaServiceImple implements AreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public AreaDto getAreaById(Long areaId) {

        Optional<Area> areaOptional = this.areaRepository.findAreaById(areaId);

        if (areaOptional.isEmpty()) {
            throw new ResourceNotFoundException("Area", "Area id", areaId.toString());
        }

        return AreaMapper.mapToAreaDto(areaOptional.get(), new AreaDto());

    }

    @Override
    public List<AreaDto> getAreaByCityId(Long cityId) {
        Optional<List<Area>> areaByCountryId = this.areaRepository.findAreaByCityId(cityId);

        if (areaByCountryId.isEmpty()) {
            throw new AllDataNotFoundException("No Area Available From This City in system");
        }

        List<AreaDto> areaDtos = new ArrayList<>();

        areaByCountryId.get().forEach(Area -> areaDtos.add(AreaMapper.mapToAreaDto(Area, new AreaDto())));

        return areaDtos;
    }

    @Override
    public List<AreaDto> getAreaByPinCode(String pinCode) {
        Optional<List<Area>> areaByCountryId = this.areaRepository.findAreaByPinCode(pinCode);

        if (areaByCountryId.isEmpty()) {
            throw new AllDataNotFoundException("No Area Available From This PinCode in system");
        }

        List<AreaDto> areaDtos = new ArrayList<>();

        areaByCountryId.get().forEach(Area -> areaDtos.add(AreaMapper.mapToAreaDto(Area, new AreaDto())));

        return areaDtos;
    }



    @Override
    public AreaDto getAreaByName(String name) {
        Optional<Area> AreaOptional = this.areaRepository.findByName(name.toLowerCase());
        if (AreaOptional.isEmpty()) {
            throw new ResourceNotFoundException("Area", "Area name", name);
        }
        return AreaMapper.mapToAreaDto(AreaOptional.get(), new AreaDto());
    }

    @Override
    public List<AreaDto> getAllArea() {
        Optional<List<Area>> optionalAreas = this.areaRepository.findDistinctFirstByIdAndEnableTrue();

        if (optionalAreas.isEmpty()) {
            throw new AllDataNotFoundException("in System not have any District");
        }

        List<AreaDto> areaDtos = new ArrayList<>();

        optionalAreas.get().forEach(Area -> areaDtos.add(AreaMapper.mapToAreaDto(Area, new AreaDto())));

        return areaDtos;
    }

    @Override
    public void deleteArea(Long id) {
        Optional<Area> optionalArea = this.areaRepository.findById(id);

        if (optionalArea.isEmpty()) {
            throw new ResourceNotFoundException("Area", "Area id", id.toString());
        }

        Area area = optionalArea.get();

        area.setEnable(false);
        areaRepository.save(area);

    }

    @Override
    public AreaDto addArea(AreaDto area) {
        Optional<Area> optionalArea = this.areaRepository.findByName(area.getName().trim().toLowerCase());
        if (optionalArea.isPresent()) {
            throw new AlreadyExistsException("This Area is Already register With This Name "+area.getName());
        }
        Area area1 = AreaMapper.mapToArea(area, new Area());
        area1.setPinCode(area1.getPinCode().trim());
        area1.setName(area1.getName().trim().toLowerCase());
        area1.setEnable(true);
        return AreaMapper.mapToAreaDto(this.areaRepository.save(area1), new AreaDto());

    }

    @Override
    public boolean updateArea(AreaDto area) {
        boolean f = false;
        Optional<Area> optionalArea = this.areaRepository.findById(area.getId());
        if (optionalArea.isEmpty()) {
            f = false;
            throw new ResourceNotFoundException("Area", "id", area.getId().toString());
        } else if (this.areaRepository.findByName(area.getName().trim().toLowerCase()).isPresent()) {
            f = false;
            throw new AlreadyExistsException("Area is Already register With This Name");
        } else {
            Area oldArea = optionalArea.get();
            oldArea.setName(area.getName().trim().toLowerCase());
            oldArea.setCityId(area.getCityId()); ;
            oldArea.setPinCode(area.getPinCode().trim());
            this.areaRepository.save(oldArea);
            f = true;
        }
        return f;

    }
}

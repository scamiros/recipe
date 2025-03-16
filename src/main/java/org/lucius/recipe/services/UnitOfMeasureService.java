package org.lucius.recipe.services;

import org.lucius.recipe.commands.UnitOfMeasureCommand;
import org.lucius.recipe.converter.UnitOfMeasureToUnitOfMeasureCommand;
import org.lucius.recipe.repositories.UnitOfMesaureRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureService {

    private final UnitOfMesaureRepository unitOfMesaureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureService(UnitOfMesaureRepository unitOfMesaureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMesaureRepository = unitOfMesaureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    public List<UnitOfMeasureCommand> findAllUoms() {
        return StreamSupport.stream(
                unitOfMesaureRepository.findAll().spliterator(), false)
                .map(i -> unitOfMeasureToUnitOfMeasureCommand.convert(i))
                .collect(Collectors.toList());
    }


}

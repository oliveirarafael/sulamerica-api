package br.com.sulamerica.desafio.api.validation;

import java.time.LocalDate;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DataNascimentoValidator implements ConstraintValidator<DataNascimentoValid, LocalDate> {

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext context) {
        LocalDate hoje = LocalDate.now();

        if(birthDate.isAfter(hoje))
            return false;

        return true;
    }

}
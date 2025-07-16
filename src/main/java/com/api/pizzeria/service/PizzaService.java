package com.api.pizzeria.service;

import com.api.pizzeria.persistence.entity.PizzaEntity;
import com.api.pizzeria.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class PizzaService {
    //Ejemplo con JDBCTemplate
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public PizzaService(JdbcTemplate jdbcTemplate){
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    public List<PizzaEntity> getAll(){
//        return this.jdbcTemplate.query("SELECT * FROM pizza", new BeanPropertyRowMapper<>(PizzaEntity.class));
//    }

    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    public List<PizzaEntity> getAll(){
        return this.pizzaRepository.findAll();
    }

    public Optional<PizzaEntity> get(Integer idPizza){
        return (this.pizzaRepository.findById(idPizza));
    }

    public List<PizzaEntity> getAvailable(){
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    public Optional<PizzaEntity> getByName(String name){
        return Optional.ofNullable(this.pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name));
    }

    public List<PizzaEntity> getWith(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public List<PizzaEntity> getWithOut(String ingredient){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public PizzaEntity save(PizzaEntity pizza){
        return this.pizzaRepository.save(pizza);
    }

    public boolean exists(Integer idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }

    public boolean delete(Integer idPizza){
            if(this.exists(idPizza)){
                pizzaRepository.deleteById(idPizza);
                return true;
            }
            return false;
    }
}

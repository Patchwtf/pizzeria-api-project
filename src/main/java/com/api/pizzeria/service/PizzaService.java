package com.api.pizzeria.service;

import com.api.pizzeria.persistence.entity.PizzaEntity;
import com.api.pizzeria.persistence.repository.PizzaRepository;
import com.api.pizzeria.service.dto.UpdatePizzaPriceDto;
import com.api.pizzeria.service.exception.EmailApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<PizzaEntity> getAll(int page, int size){
        Pageable pageRequest = PageRequest.of(page, size);
        return this.pizzaRepository.findAll(pageRequest);
    }

    public Optional<PizzaEntity> get(Integer idPizza){
        return (this.pizzaRepository.findById(idPizza));
    }

    public Page<PizzaEntity> getAvailable(int page, int size, String sortBy, String sortDirection){
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageRequest = PageRequest.of(page, size, sort);
        return this.pizzaRepository.findAllByAvailableTrue(pageRequest);
    }

    public Optional<PizzaEntity> getByName(String name){
        return Optional.ofNullable(this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name));
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

    public List<PizzaEntity> getCheapest(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    @Transactional(noRollbackFor = EmailApiException.class)
    public void updatePrice(UpdatePizzaPriceDto dto){
        this.pizzaRepository.updatePrice(dto);
        this.sendEmail();
    }

    private void sendEmail(){
        /*Ejemplo de excepcion*/
        throw new EmailApiException();
    }
}

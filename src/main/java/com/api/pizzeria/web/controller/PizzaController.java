package com.api.pizzeria.web.controller;

import com.api.pizzeria.persistence.entity.PizzaEntity;
import com.api.pizzeria.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<List<PizzaEntity>> getAll(){
        return ResponseEntity.ok(pizzaService.getAll());
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getById(@PathVariable Integer idPizza){
        PizzaEntity pizza = pizzaService.get(idPizza);
        return pizza != null ? ResponseEntity.ok(pizza) : ResponseEntity.notFound().build();
    }

//    @PostMapping
//    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza){
//        if(pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())) {
//            PizzaEntity savedPizza = pizzaService.save(pizza);
//            return ResponseEntity.created(URI.create("/" + savedPizza.getIdPizza())).body(savedPizza);
//        } else {
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }
//    }
    @PostMapping
    public ResponseEntity<?> add(@RequestBody PizzaEntity pizza){
        try {
            if(pizza.getIdPizza() == null) {
                PizzaEntity savedPizza = pizzaService.save(pizza);
                return ResponseEntity.created(URI.create("/" + savedPizza.getIdPizza())).body(savedPizza);
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Pizza already exists");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza){
        try {
            if (pizza.getIdPizza() != null && pizzaService.exists(pizza.getIdPizza())) {
                return ResponseEntity.ok(pizzaService.save(pizza));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable Integer idPizza){
        return pizzaService.delete(idPizza) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
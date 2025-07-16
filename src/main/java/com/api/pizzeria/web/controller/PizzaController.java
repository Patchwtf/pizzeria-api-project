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
        return ResponseEntity.ok(this.pizzaService.getAll());
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> getById(@PathVariable Integer idPizza){
        return this.pizzaService.get(idPizza)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(params = "name")
    public ResponseEntity<PizzaEntity> getById(@RequestParam String name){
        return this.pizzaService.getByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getAvailable(){
        return ResponseEntity.ok(this.pizzaService.getAvailable());
    }

    @GetMapping(params = "ingredients", path = "/with")
    public ResponseEntity<List<PizzaEntity>> getWith(@RequestParam String ingredients){
        try{
            List<PizzaEntity> pizzas = this.pizzaService.getWith(ingredients);
            if (pizzas.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pizzas);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(params = "ingredients", path = "/withOut")
    public ResponseEntity<List<PizzaEntity>> getWithOut(@RequestParam String ingredients){
        try{
            List<PizzaEntity> pizzas = this.pizzaService.getWithOut(ingredients);
            if (pizzas.isEmpty()){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(pizzas);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
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
                PizzaEntity savedPizza = this.pizzaService.save(pizza);
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
            if (pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza())) {
                return ResponseEntity.ok(this.pizzaService.save(pizza));
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable Integer idPizza){
        return this.pizzaService.delete(idPizza) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
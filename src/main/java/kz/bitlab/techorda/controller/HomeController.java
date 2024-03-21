package kz.bitlab.techorda.controller;

import kz.bitlab.techorda.model.Car;
import kz.bitlab.techorda.repository.CarRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private CarRepository carRepository;

    @GetMapping(value = "/home")
    public String getHomePage(Model model){
        List<Car> carList = carRepository.findAll();
        model.addAttribute("cars", carList);
        return "carsPage";
    }

    @PostMapping(value = "/addCar")
    public String addCar(@RequestParam(value = "name") String name,
                         @RequestParam(value = "model") String model,
                         @RequestParam(value = "price") int price){
        Car car = new Car();
        car.setName(name);
        car.setModel(model);
        car.setPrice(price);

        carRepository.save(car);

        return "redirect:/home";
    }

    @GetMapping(value = "/details/{id}")
    public String getDetailsPage(@PathVariable(value = "id") Long id,
                                 Model model){
        //Car car = DBManager.getCarById(id);
        Car car = carRepository.findById(id).orElseThrow();
        model.addAttribute("onecar", car);
        return "detailsPage";
    }

    @PostMapping(value = "/updateCar/{id}")
    public String updateCar(@RequestParam(value = "name") String name,
                            @RequestParam(value = "model") String model,
                            @RequestParam(value = "price") int price,
                            @PathVariable(value = "id") Long id){

        Car car = carRepository.findById(id).orElse(null);
        car.setName(name);
        car.setPrice(price);
        car.setModel(model);
        carRepository.save(car);
        return "redirect:/home";
    }

    @PostMapping(value = "/deleteCar/{id}")
    public String deleteCar(@PathVariable(value = "id") Long id){
        carRepository.deleteById(id);
        return "redirect:/home";
    }
}

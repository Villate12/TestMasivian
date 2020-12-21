package com.testmasivian.test;

import java.util.Date;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/roulette")
public class RouletteController {

  @Autowired
  private RouletteRepository rouletteRepository;

  @Autowired
  private BetRepository betRepository;

  @PostMapping(path = "/create")
  public @ResponseBody String addNewRoulette() {
    Roulettes newRoulette = new Roulettes();
    newRoulette.setStatus("noStarted");
    newRoulette.setUpdatedAt(String.valueOf(new Date()));
    newRoulette.setCreatedAt(String.valueOf(new Date()));
    rouletteRepository.save(newRoulette);

    return String.valueOf(newRoulette.getId());
  }

  @PostMapping(path = "/create_bet")
  public @ResponseBody String addNewBet(
    @RequestParam Integer rouletteId,
    @RequestParam String type,
    @RequestParam Integer value,
    @RequestParam Integer amount,
    @RequestParam Integer userId
  ) {
    Optional<Roulettes> optionalRoulette = rouletteRepository.findById(
      rouletteId
    );
    if (optionalRoulette.isPresent()) {
      Bet testBet = new Bet();
      if (
        (amount > 0 && amount < 10000) &&
        (type.equals("color") || type.equals("number")) &&
        (value > 0 && value < 37)
      ) {
        testBet.setType(type);
        testBet.setBetValue(value);
        testBet.setAmount(amount);
        testBet.setUserId(userId);
        testBet.setRouletteId(rouletteId);
        betRepository.save(testBet);
        return "Apuesta Guardada";
      } else {
        return "Apuesta No Válida";
      }
    } else {
      return "Ruleta no encontrada";
    }
  }

  @PatchMapping(path = "/open/{id}")
  public @ResponseBody String openRoulette(@PathVariable("id") Integer id) {
    Optional<Roulettes> optionalRoulette = rouletteRepository.findById(id);
    if (optionalRoulette.isPresent()) {
      Roulettes roulette = optionalRoulette.get();
      String status = roulette.getStatus();
      System.out.println(status + " ***************************");
      if (status.equals("noStarted")) {
        roulette.setStatus("open");
        roulette.setUpdatedAt(String.valueOf(new Date()));
        rouletteRepository.save(roulette);
        return "Operacion exitosa";
      } else {
        return (status.equals("open"))
          ? "La ruleta ya esta abierta"
          : "La ruleta ya ha cerrado";
      }
    } else {
      return "Operacion denegada";
    }
  }

  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Roulettes> getAllRoulettes() {
    // This returns a JSON or XML with the users
    return rouletteRepository.findAll();
  }

  @GetMapping(path = "/all_bet")
  public @ResponseBody Iterable<Bet> getAllBets() {
    return betRepository.findAll();
  }
}

package com.testmasivian.test;
import java.util.Date;
import java.util.Optional;
import java.util.Calendar;
import java.text.SimpleDateFormat;
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
    Date fecha=new Date(Calendar.getInstance().getTimeInMillis());
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String fechaTexto = formatter.format(fecha);
    newRoulette.setUpdatedAt(fechaTexto);
    newRoulette.setCreatedAt(fechaTexto);
    rouletteRepository.save(newRoulette);

    return String.valueOf(newRoulette.getId());
  }
  @PostMapping(path = "/create_bet")
  public @ResponseBody String addNewBet(
    @RequestParam Integer rouletteId,
    @RequestParam String type,
    @RequestParam String value,
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
        (
          (
            type.equals("color") &&
            (value.equals("rojo") || value.equals("negro"))
          ) ||
          (
            type.equals("number") &&
            (Integer.parseInt(value) > 0 || Integer.parseInt(value) < 37)
          )
        )
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
  @PatchMapping(path = "/close_bet/{id}")
  public @ResponseBody String Bet(@PathVariable("id") Integer id) {
    Optional<Roulettes> optionalRoulette = rouletteRepository.findById(id);
    Integer winnerNumber = 2;//(int) (Math.random() * 36);
    String winnerColor = "";
    if (winnerNumber % 2 == 0) {
      winnerColor = "rojo";
    } else {
      winnerColor = "negro";
    }
    Integer rouletteId = id;
    float money = 0f;
    if (optionalRoulette.isPresent()) {
      Roulettes roulette = optionalRoulette.get();
      Optional<Bet> testBet = betRepository.findById(rouletteId);
      Bet bet = testBet.get();
      Integer playerId = bet.getuserId();
      String betValue = bet.getBetValue();
      Integer betAmount = bet.getAmount();
      String betType = bet.getType();
      if (betValue.equals(winnerColor)) {
        money = betAmount * 1.8f;
        return "El jugador "+playerId+" ha ganado"+money+" por su apuesta a color";
      } else if (betValue.equals(String.valueOf(winnerNumber))) {
        money = betAmount * 5f;
        return "El jugador "+playerId+" ha ganado"+money+" por su apuesta a numero";
      }else{
        return "Ningún jugador ha ganado";
      }
    }

    return "No está en juego esa ruleta";
  }
  @PatchMapping(path = "/open/{id}")
  public @ResponseBody String openRoulette(@PathVariable("id") Integer id) {
    Optional<Roulettes> optionalRoulette = rouletteRepository.findById(id);
    if (optionalRoulette.isPresent()) {
      Roulettes roulette = optionalRoulette.get();
      String status = roulette.getStatus();
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
    return rouletteRepository.findAll();
  }
  @GetMapping(path = "/all_bet")
  public @ResponseBody Iterable<Bet> getAllBets() {
    return betRepository.findAll();
  }
}

package com.testmasivian.test;
import org.springframework.data.repository.CrudRepository;
import com.testmasivian.test.Bet;
public interface BetRepository extends CrudRepository<Bet, Integer> {
}
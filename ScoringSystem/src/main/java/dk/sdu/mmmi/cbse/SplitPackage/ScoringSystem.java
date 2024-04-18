package dk.sdu.mmmi.cbse.SplitPackage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RequestMapping("/attributes")
@RestController
@CrossOrigin
public class ScoringSystem {

    private Long totalscore = 0L;
    private Long playerlives = 3L;
    public static void main(String[] args) {
        SpringApplication.run(ScoringSystem.class, args);
    }

    @GetMapping("/score")
    public Long getScore() {
        return totalscore;
    }

    @PutMapping("score/update/{score}")
    public Long updateScore(@PathVariable(value = "score") Long score) {
        totalscore += score;
        return totalscore;
    }

    @GetMapping("/lives")
    public Long getLives() {
        return playerlives;
    }

    @PutMapping("/lives/decrement/{decrement}")
    public Long decrementLives(@PathVariable(value = "decrement") Long decrement) {
        playerlives = playerlives - decrement;
        return playerlives;
    }
}

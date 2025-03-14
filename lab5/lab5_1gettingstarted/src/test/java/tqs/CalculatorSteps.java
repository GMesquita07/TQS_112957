package tqs;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CalculatorSteps {

    static final Logger log = getLogger(lookup().lookupClass());

    private Calculator calc;
    private Exception exception;

    @Given("a calculator I just turned on")
    public void setup() {
        calc = new Calculator();
    }

    @When("I add {int} and {int}")
    public void add(int arg1, int arg2) {
        log.debug("Adding {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("+");
    }

    @When("I substract {int} to {int}")
    public void substract(int arg1, int arg2) {
        log.debug("Substracting {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("-");
    }

    @When("I multiply {int} and {int}")
    public void multiply(int arg1, int arg2) {
        log.debug("multiplying {} and {}", arg1, arg2);
        calc.push(arg1);
        calc.push(arg2);
        calc.push("*");
    }

    @When("I divide {int} by {int}")
    public void divide(int arg1, int arg2) {
        log.debug("dividing {} by {}", arg1, arg2);
        try {
            calc.push(arg1);
            calc.push(arg2);
            calc.push("/");
        } catch (ArithmeticException e) {
            exception = e;
        }
    }

    @Then("the result is {double}")
    public void the_result_is(double expected) {
        Number value = calc.value();
        log.debug("Result: {} (expected {})", value, expected);
        assertEquals(expected, value);
    }

    @Then("the result is an error")
    public void the_result_is_an_error() {
        log.debug("Error: {}", exception.getMessage());
        assertEquals(ArithmeticException.class, exception.getClass());
        exception = null;
    }
}
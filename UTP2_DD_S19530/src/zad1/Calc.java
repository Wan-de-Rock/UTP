/**
 *
 *  @author Diedov Denys S19530
 *
 */

package zad1;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Calc {
    private static Map<String, Operation> OPERATIONS = new HashMap<>();

    static {

        OPERATIONS.put("+", (x, y) -> x.add(y));
        OPERATIONS.put("-", (x, y) -> x.subtract(y));
        OPERATIONS.put("*", (x, y) -> x.multiply(y));
        OPERATIONS.put("/", (x, y) -> x.divide(y));

    }

    public String doCalc(String cmd) {

        String[] args = cmd.split("\\s+");

        BigDecimal firstArg = new BigDecimal(args[0]);
        BigDecimal secondArg = new BigDecimal(args[2]);

        try {
            return Calc.OPERATIONS.get(args[1]).function(firstArg, secondArg).toString();
        }
        catch (ArithmeticException e) {
            return firstArg.divide(secondArg, 7, BigDecimal.ROUND_HALF_UP).toString();
        }
        catch(Exception exception) {
            return "Invalid command to calc";
        }
    }
}  

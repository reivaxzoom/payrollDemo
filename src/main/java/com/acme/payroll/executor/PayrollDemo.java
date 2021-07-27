package com.acme.payroll.executor;

import com.acme.payroll.exception.FileNotFoundException;
import com.acme.payroll.logic.CalculationService;
import com.acme.payroll.logic.InputOutput;
import com.acme.payroll.logic.Regulation;
import com.acme.payroll.model.Roster;
import static com.acme.payroll.model.Roster.RosterBuilder;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Main class of the program, follow the pipeline of the payroll
 * @author xavier
 */
public class PayrollDemo {
    public static final Logger LOGGER = Logger.getLogger(PayrollDemo.class.getName());

    public static void main(String[] args)  {
        try {
            LOGGER.info("Payroll Starting");
            LOGGER.fine("Loading Data");
             Map<String, Map<String, String>> inputData =InputOutput.loadData();
             
             LOGGER.fine("Printing Input");
             InputOutput.printRoster(inputData);
             
             LOGGER.fine("Parsing Data");
             List<Roster> rosters = new RosterBuilder().setData(inputData).build();
             
             LOGGER.fine("Calculating Payroll");
             Regulation reg = new CalculationService() ;
             List<Roster> rosterCalculated= rosters.stream().map(r -> reg.calculateSalary(r)).collect(Collectors.toList());
             LOGGER.fine("Printing Payroll");
             InputOutput.printOutput.accept(rosterCalculated);
            LOGGER.info("Payroll Finished ");

        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Input Output exception", ex);
        } catch (FileNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "File was not found", ex);
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Parse Exception", ex);
        } catch (URISyntaxException ex) {
           LOGGER.log(Level.SEVERE, null, ex);
        }
    }
}

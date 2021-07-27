package com.acme.payroll.logic;

import com.acme.payroll.exception.FileNotFoundException;
import com.acme.payroll.model.Roster;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Handles input data file and output file
 * @author xavier
 */
public interface InputOutput {
    public static final Logger LOGGER = Logger.getLogger(InputOutput.class.getName());
    final ResourceBundle config = ResourceBundle.getBundle("config");
    final MessageFormat workPeriodFormat = new MessageFormat(config.getString("workPeriod.format"));
    final MessageFormat outputFormat = new MessageFormat(config.getString("output.format"));
     
    
     private static Path getWorkingPath(String folder,String file){
        try {
            URI jarPath = InputOutput.class.getProtectionDomain().getCodeSource().getLocation().toURI();
            String mainPath=Paths.get(jarPath).getParent().toString();
            Path path=Paths.get(mainPath,folder,file);
            return path;
        } catch (URISyntaxException ex) {
            Logger.getLogger(InputOutput.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    };

    

    public  Consumer<List<Roster>> printOutput = rosters -> {
        Path outputFile= getWorkingPath("data", config.getString("input.file"));
        List<String> parsedOutput = rosters.stream().map(s -> MessageFormat.format(config.getString("output.format"), s.getName(), s.getTotalAmmount().toString())).collect(Collectors.toList());
        Iterator<String> it = parsedOutput.iterator();
        try (final PrintWriter out = new PrintWriter(new OutputStreamWriter(Files.newOutputStream(outputFile, StandardOpenOption.CREATE), "UTF8"))) {
            while (it.hasNext()) {
                out.append(it.next() + System.lineSeparator());
            }
        }catch(IOException e){
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    };
    
   
    public static Map<String, Map<String, String>> loadData() throws FileNotFoundException, IOException, ParseException, URISyntaxException {
        Path inputFile=getWorkingPath("data", config.getString("input.file"));
        Map<String, Map<String, String>> employeesData;
        if (Files.notExists(inputFile)) {
            throw new FileNotFoundException(MessageFormat.format("File {0} was not found on {1}", inputFile.getFileName(), inputFile.getParent().toString()));
        } else {
            employeesData = Files.lines(inputFile, Charset.forName("UTF-8")).collect(Collectors.toMap(text -> parseEmployeeName(text), text -> parseWorkTime(text)));
        }
        return employeesData;
    }

    public static void printRoster(Map<String, Map<String, String>> employeesRoster) {
        employeesRoster.entrySet().forEach(entry -> {
            Object name = entry.getKey();
            LOGGER.fine(name.toString());
            Map<String, String> listTime = entry.getValue();
            listTime.entrySet().forEach((Map.Entry<String, String> entry1) -> {
                Object day = entry1.getKey();
                Object time = entry1.getValue();
                LOGGER.fine(day + " " + time);
            });
        });
    }

    private static String parseEmployeeName(String text) {
        try {
            Object[] values;
            values = workPeriodFormat.parse(text);
            String name = values[0].toString();
            return name;
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static Map<String, String> parseWorkTime(String text) {
        try {
            Map<String, String> rosters = new HashMap<>();
            Object[] values = workPeriodFormat.parse(text);
            String periodsArray = values[1].toString();
            List<String> periods = List.of(periodsArray.split(","));
            String day;
            String workTime;
            for (String period : periods) {
                day = period.substring(0, 2);
                workTime = period.substring(2, period.length());
                rosters.put(day, workTime);
            }
            return rosters;
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

package org.teamvoltage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Logger holds a simple logging framework for logging data to a CSV file either
 * in a directory on the RoboRIO or into a USB drive attached to the RoboRIO.
 * 
 * Note that the log file will be overwritten every time the robot is run.
 * 
 * To add a data source to the logger:
 * 
 * <pre>
 * org.teamvoltage.Logger.getInstance().addDataSource("Source name", thing::methodToCall);
 * org.teamvoltage.Logger.getInstance().addDataSource("Source two", () -> thing.methodToCall());
 * org.teamvoltage.Logger.getInstance().addDataSource("Source three", () -> {
 *     return thing.methodToCall();
 * });
 * </pre>
 * 
 * To cause the logger to save:
 * 
 * <pre>
 * org.teamvoltage.Logger.getInstance().saveLogs();
 * </pre>
 * 
 * Normally you will add sources in subsystem constructors, and save the logs
 * within
 * periodic methods like teleopPeriodic and autonomousPeriodic.
 */
public class Logger {

    private static Logger INSTANCE = new Logger();

    private final List<LogSource> dataSources = new ArrayList<>();
    private boolean dataFileCreated = false;

    private String loggingLocation = "/home/lvuser/logs/";
    private final String usb1Location = "/media/sda1/";

    /**
     * Get a singleton logger instance.
     * 
     * @return The Logger singleton
     */
    public static Logger getInstance() {
        return INSTANCE;
    }

    /**
     * Determines if a flash drive is plugged into the RIO. If so, it'll use that
     * location for the log file
     */
    private Logger() {
        final File usb1 = new File(usb1Location);
        if (usb1.exists()) {
            loggingLocation = usb1Location + "logs/";
        }
    }

    /**
     * Adds a source function to the list of the data to be output.
     * 
     * @param name     the name of the parameter being output
     * @param supplier function that return the value to be written to the log
     */
    public void addDataSource(final String name, final Supplier<Object> supplier) {
        dataSources.add(new LogSource(name, supplier));
    }

    /**
     * saveLogs should be called from within one of the periodic functions, for
     * example teleopPeriodic.
     * 
     * For each object in the list of sources, the current value associated with
     * that source will be output to the log file in CSV format.
     */
    public void saveLogs() {
        try {
            final Path dataFilePath = getDataFilePath();
            if (!dataFileCreated) {
                createDataFile(dataFilePath);
                saveTitles(dataFilePath);
            }
            saveData(dataFilePath);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the log directory.
     * 
     * @exception IOException if an IO error occurs while creating the directory
     */
    private void createLogDirectory() throws IOException {
        final File logDirectory = new File(loggingLocation);
        if (!logDirectory.exists()) {
            Files.createDirectory(Path.of(loggingLocation));
        }
    }

    /**
     * Creates the output file to write the data to. The filename depends on whether
     * the robot is attached to the FMS or not. If you are not connected to an FMS
     * your log will be saved as test.csv and will be overwritten each time the
     * robot is restarted. However if you are at an event your logs will be saved in
     * the following format: eventCode_matchType + matchNumber.csv
     */
    private void createDataFile(final Path file) throws IOException {
        createLogDirectory();
        if (Files.exists(file)) {
            Files.delete(file);
        }
        Files.createFile(file);
        this.dataFileCreated = true;
    }

    /**
     * Return the Path where the data file is stored.
     * 
     * @return The data file Path
     */
    private Path getDataFilePath() {
        return Path.of(loggingLocation, getDataFileName());
    }

    /**
     * Return the String for the data file name. If the DriverStation has the
     * FMS attached then this method returns a name based on the event and
     * match details, otherwise it returns a default file name.
     * 
     * @return The name of the data file
     */
    private String getDataFileName() {
        // final var ds = DriverStation.getInstance();
        if (DriverStation.isFMSAttached()) {
            return DriverStation.getEventName() + "_" + DriverStation.getMatchType() + DriverStation.getMatchNumber()
                    + ".csv";
        } else {
            return "test.csv";
        }
    }

    /**
     * Save the CSV header.
     * 
     * @param dataFilePath The Path to the data file
     * @exception IOException raised if there is an IO error writing the header
     */
    private void saveTitles(final Path dataFilePath) throws IOException {
        final StringBuilder titles = new StringBuilder();
        titles.append("Timestamp,");
        titles.append("Match Time,");
        titles.append(dataSources.stream().map(s -> s.name).collect(Collectors.joining(","))).append(",");

        Files.write(dataFilePath, Collections.singletonList(titles.toString()), StandardOpenOption.APPEND);
    }

    /**
     * Save the CSV data.
     * 
     * @param dataFilePath The Path to the data file
     * @exception IOException raised if there is an IO error writing the data
     */
    private void saveData(final Path dataFilePath) throws IOException {
        final StringBuilder data = new StringBuilder();
        data.append(Instant.now().toString()).append(",");
        data.append(DriverStation.getMatchTime()).append(",");
        data.append(getValues());

        Files.write(dataFilePath, Collections.singletonList(data.toString()), StandardOpenOption.APPEND);
    }

    /**
     * The values to be written to the CSV file.
     * 
     * @return The values as a String
     */
    private String getValues() {
        return dataSources.stream().map(s -> s.supplier.get()).map(Object::toString).collect(Collectors.joining(","));
    }

    /**
     * LogSource holds the name and source for a single log data provider.
     */
    private class LogSource {
        private final String name;
        private final Supplier<Object> supplier;

        /**
         * Construct a source for logging data.
         * 
         * @param name     The name of the source, used for the CSV header
         * @param supplier The Supplier instance
         */
        LogSource(final String name, final Supplier<Object> supplier) {
            this.name = name;
            this.supplier = supplier;
        }
    }
}
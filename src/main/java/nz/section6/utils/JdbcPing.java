package nz.section6.utils;

import org.apache.commons.cli.*;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcPing {

    public static void main(String [] args) {

        String jdbcDriverClass = null;
        String jdbcConnectionString = null;

        // Create CLI options
        Options options = new Options();
        options.addOption("d", "driver", true, "Fully qualified class name of the JDBC driver to use");
        options.addOption("U", "url", true, "JDBC URL to use as connection string");

        try {
            // Parse CLI options
            CommandLineParser parser = new DefaultParser();
            CommandLine line = parser.parse( options, args );

            if(line.hasOption( "driver" )) {
                jdbcDriverClass = line.getOptionValue( "driver");
            }

            if(line.hasOption( "url" )) {
                jdbcConnectionString = line.getOptionValue( "url");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            // Load the specified JDBC Driver
            Class.forName(jdbcDriverClass);
        } catch(ClassNotFoundException e) {
            System.out.println("Unable to load specified JDBC driver. Please check that driver is available on classpath.");
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString);
            String dbProductName = connection.getMetaData().getDatabaseProductName();
            String dbProductVersion = connection.getMetaData().getDatabaseProductVersion();
            System.out.println("Successfully connected to " + dbProductName + " " + dbProductVersion);
        } catch(Exception e) {
            System.out.println("Failed to connect to database");
            e.printStackTrace();
        }

    }
}

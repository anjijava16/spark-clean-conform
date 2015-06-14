/*
 * Copyright (C) 2015 Roberto Congiu <rcongiu@yahoo.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.congiu.load.csv;

import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import org.apache.hadoop.conf.Configuration;
/**
 *
 * @author Roberto Congiu <rcongiu@yahoo.com>
 */
public class Main {
    public static String[] OPT_HELP = { "h", "help"};
    public static String[] OPT_DEST = { "d", "destination" };
        
    public static void main(String[] args) {
        Options opts = buildCommandLineOptions();

        Configuration conf = new Configuration();
        
        try {
            CommandLine cmd = processArguments(opts, args );
            
            if( cmd.hasOption(OPT_HELP[0])) {
                printUsage(opts, new PrintWriter(System.out));
                System.exit(0);
            }
            
            // all unprocessed arguments are files.
            // let's first check if they all exist
            for(String s: cmd.getArgs()) {
                if(! FSUtils.fileExists(conf, s)) {
                    throw new Exception("File " + s + " does not exist.");
                }
            }
            
            CSVConverter converter = getCSVConverter( cmd );
            
            for(String s: cmd.getArgs()) {
                
            }
            
        } catch (Exception ex) {
            System.err.println("There was a problem parsing your arguments.");
            System.err.println(ex);
            printUsage(opts, new PrintWriter(System.err));
            System.exit(-1);
        }
    }
    
    static void printUsage(Options options,PrintWriter pw) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(pw, 80, "load-csv [options] file [,file...]",
                "loads one or more csv files to HDFS, converting them to " +
                        "a more appropriate format", options, 2 ,2, "");
        pw.flush();
        
    }
    
    static CommandLine processArguments(Options opts, String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        return parser.parse(opts, args);
    }
    
    static Options buildCommandLineOptions() {
        Options opts = new Options();
        
        addOption( opts, OPT_HELP, false, "Show help." );
        addOption( opts, OPT_DEST, true, "Destination Directory" );
        
        return opts;
    }
    
    /*
    Utility method to add options 
    */
    static void addOption(Options opts, String[] opt, boolean param, String text) {
        opts.addOption(opt[0], opt[1],param,text);
    }

    /** 
     * factory method to instantiate the correct converter
     * based on command line options.
     * Parquet, SequenceFile, etc.
     * @param cmd
     * @return 
     */
    private static CSVConverter getCSVConverter(CommandLine cmd) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}

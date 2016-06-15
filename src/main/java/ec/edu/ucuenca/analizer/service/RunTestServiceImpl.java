/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.analizer.service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import ec.edu.ucuenca.analizer.api.RunTestService;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 *
 * @author Fernando Baculima Geo Linked Data Ecuador linkeddata.ec
 * geo.linkeddata.ec Universidad de Cuenca Departamento de Ciencias de la
 * Computacion Cuenca - Ecuador
 *
 */
public class RunTestServiceImpl implements RunTestService {

    @Override
    public String runMainTest(String endpoint, String queryFile, String folderToTest) {

        StringBuilder prefixes = new StringBuilder();
        prefixes.append(" PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> ");

        /**
         * Getting list of files
         */
        try {

            String query = "";
            query = IOUtils.toString(this.getClass().getResourceAsStream("/" + folderToTest + "/" + queryFile), "UTF-8");
            QueryExecution execution = null;
            try {
                long start = System.currentTimeMillis();
                long end = start + 5 * 1000; // 60 seconds * 1000 ms/sec
                while ((System.currentTimeMillis() < end) || execution == null) {
                    execution = QueryExecutionFactory.sparqlService(endpoint, query);
                    if (execution != null) {
                        break;
                    }
                }
                if (execution == null) {
                    System.out.println("    Time Exceeded, No results obtained  ");

                } else {
                    int allRetrieveResources = 0;

                    ResultSet queryResult = execution.execSelect();
                    while (queryResult.hasNext()) {
                        QuerySolution solution = queryResult.next();
                        String uri = solution.get("resource").toString();
                        allRetrieveResources++;
                    }
                    System.out.println("    Recovered resources from " + allRetrieveResources);
                    execution.close();
                  

                }
            } catch (Exception e) {
                System.out.println("    No results obtained");
            } finally {

            }
            return "";
        } catch (IOException ex) {
            Logger.getLogger(RunTestServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

}

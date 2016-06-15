/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.analizer.service;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.validation.metadata.ReturnValueDescriptor;

/**
 *
 * @author Satellite
 */
public class QueryExecutionThread  {

    final String endpoint;
    final String query;
    public QueryExecutionThread(String endpoint, String query) {
        this.endpoint = endpoint;
        this.query = query;
    }
    
    
//    
//       @Override
//    public void run() {
//        QueryExecution execution = QueryExecutionFactory.sparqlService(endpoint, query);      
//    }
//    
    
    
public void check() {

ExecutorService executor = Executors.newSingleThreadExecutor();
Future<QueryExecution> result = executor.submit(new Callable<QueryExecution>() {
    public QueryExecution call() throws Exception {
           return QueryExecutionFactory.sparqlService(endpoint, query);      

    }
   
});
    }
}

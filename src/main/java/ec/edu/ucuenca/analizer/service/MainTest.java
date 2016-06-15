/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ucuenca.analizer.service;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Satellite
 */
public class MainTest {

    /**
     * @param args the command line arguments
     */
    public String folderToTest;

    public MainTest(String folderToTest) {
        this.folderToTest = folderToTest;
    }

    public void runCompare() {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
      
        System.out.println(dateFormat.format(date)); //2014/08/06 15:59:48

        List<String> endpoints = new ArrayList<>();
        endpoints.add("http://localhost:8080/parliament/sparql");
        endpoints.add("http://localhost:8080/strabon/Query");
        endpoints.add("http://localhost:8080/marmotta/sparql/select");

        initwriteLog(date, endpoints);
        List<String> SparqlFilesList = getQueries();
        int totaltest = 0;
        for (String queryFile : SparqlFilesList) {
            writeLog(date, queryFile.replace(".sparql", ""));
            totaltest++;
            System.out.println("--------  TEST: " + totaltest + " de " + SparqlFilesList.size() + " ---------  ");
            System.out.println("Testing query: " + queryFile);

            for (String endpoint : endpoints) {
                System.out.println("    Execute in: " + endpoint);
                long TInicio, TFin, time; //Variables para determinar el tiempo de ejecución
                TInicio = System.currentTimeMillis(); //Tomamos la hora en que inicio el algoritmo y la almacenamos en la variable inicio

                RunTestServiceImpl rts = new RunTestServiceImpl();
                rts.runMainTest(endpoint, queryFile, folderToTest);

                TFin = System.currentTimeMillis(); //Tomamos la hora en que finalizó el algoritmo y la almacenamos en la variable T
                time = TFin - TInicio; //Calculamos los milisegundos de diferencia
                System.out.println("    Tiempo de ejecución en milisegundos: " + time); //Mostramos en pantalla el tie
                writeLog(date, time);
            }
            writeLog(date, "\n");

            //      RunTestServiceImpl rts = new RunTestServiceImpl();
            //     rts.runMainTest();
        }
    }

    private List<String> getQueries() {

        List<String> SparqlFilesList = new ArrayList<>();
        final File f = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "/" + folderToTest);

        File[] ficheros = f.listFiles();
        for (int x = 0; x < ficheros.length; x++) {
            String filename = ficheros[x].getName();
            if (filename.contains("sparql")) {
                SparqlFilesList.add(filename);
                System.out.println(ficheros[x].getName());
            }
        }
        //  SparqlFilesList.add("ehCoveredBy.sparql");
        return SparqlFilesList;

    }

    public void initwriteLog(Date date, List<String> endpoints) {

        writeLog(date, "QUERY");

        //Writing Headers
        for (String endpoint : endpoints) {
            writeLog(date, "," + endpoint);
        }
        writeLog(date,"\n");
    }

    public void writeLog(Date date, String value) {
        try {
            //Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
            String logFileName = date.toString().replace(" ", "").replace("/", "-").replace(":", "-");
            File archivo = new File(logFileName + "_results.csv");

            //Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
            FileWriter escribir = new FileWriter(archivo, true);

            //Escribimos en el archivo con el metodo write 
            escribir.write(value);
            //Cerramos la conexion
            escribir.close();
        } //Si existe un problema al escribir cae aqui
        catch (Exception e) {
            System.out.println("Error al escribir");
        }
    }

    public void writeLog(Date date, long time) {
        writeLog(date, "," + time);
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {

        MotorDeInferencia motor = new MotorDeInferencia();

        String classPath = String.valueOf(Main.class.getClassLoader().getResource(""));
        classPath = classPath.replace("file:", "").replace("\\", "/");

        // Construct the generalized paths for rules and facts files
        String factsFilePath = classPath + "pruebaHechos.txt";
        String rulesFilePath = classPath + "pruebaReglas.txt";

        cargarReglas(rulesFilePath, motor);
        cargarHechos(factsFilePath, motor);
        motor.encadenar();

    }

    private static void cargarReglas(String archivo, MotorDeInferencia motor) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split("->");
                if (partes.length == 2) {
                    String expression = partes[0].trim();
                    String conclusion = partes[1].trim();
                    motor.agregarRegla(new Regla(expression, conclusion));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void cargarHechos(String archivo, MotorDeInferencia motor) {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                motor.agregarHecho(linea.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
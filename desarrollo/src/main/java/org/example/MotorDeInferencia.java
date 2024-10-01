package org.example;

import java.util.ArrayList;
import java.util.List;

public class MotorDeInferencia {
    private List<Regla> reglas;
    private List<String> hechos;

    public MotorDeInferencia() {
        this.hechos = new ArrayList<String>();
        this.reglas = new ArrayList<Regla>();
    }

    public void agregarRegla(Regla regla) {
        reglas.add(regla);
    }

    public void agregarHecho(String hecho) {
        if (!hechos.contains(hecho)) {
            hechos.add(hecho);
        }
    }

    public void encadenar() {
        boolean nuevasDeducciones;
        do {
            nuevasDeducciones = false;
            for (Regla regla : reglas) {
                if (regla.esAplicable(hechos) && !hechos.contains(regla.getConclusion())) {
                    System.out.println("Aplicando regla: [" + regla.getConclusion() + "]");
                    hechos.add(regla.getConclusion());
                    nuevasDeducciones = true;
                }
            }
        } while (nuevasDeducciones);
        System.out.println("Hechos inferidos: " + hechos);
    }
}
package org.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MotorDeInferencia {
    private final List<Regla> reglas;
    private final List<String> hechos;
    private final Set<String> conclusionesProbadas; // Conclusiones ya evaluadas

    public MotorDeInferencia() {
        this.hechos = new ArrayList<>();
        this.reglas = new ArrayList<>();
        this.conclusionesProbadas = new HashSet<>();
    }

    public void agregarRegla(Regla regla) {
        reglas.add(regla);
    }

    public void agregarHecho(String hecho) {
        if (!hechos.contains(hecho)) {
            hechos.add(hecho);
        }
    }

    public boolean encadenar() {
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
        return !hechos.isEmpty();
    }

    // Backtracking: Intentar deducir una conclusión específica
    public boolean retroceder(String objetivo) {
        // Verificar si el objetivo ya es un hecho
        if (hechos.contains(objetivo)) {
            return true;
        }

        // Si ya se intentó deducir esta conclusión, no volver a probarla
        if (conclusionesProbadas.contains(objetivo)) {
            return false;
        }

        // Marcar la conclusión como probada
        conclusionesProbadas.add(objetivo);

        // Intentar aplicar reglas para deducir la conclusión
        for (Regla regla : reglas) {
            if (regla.getConclusion().equals(objetivo)) {
                // Verificar si las condiciones de la regla pueden ser satisfechas
                if (regla.esAplicableConBacktracking(hechos, this)) {
                    System.out.println("Aplicando regla (backtracking): [" + objetivo + "]");
                    hechos.add(objetivo);
                    return true;
                }
            }
        }

        // No se pudo deducir el objetivo
        return false;
    }
}

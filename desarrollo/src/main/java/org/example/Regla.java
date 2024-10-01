package org.example;

import java.util.List;

public class Regla {
    private final String expresion; // Expresión completa de la regla
    private final String conclusion; // Conclusión de la regla

    public Regla(String expresion, String conclusion) {
        this.expresion = expresion;
        this.conclusion = conclusion;
    }

    public String getConclusion() {
        return conclusion;
    }

    public boolean esAplicable(List<String> hechos) {
        return evaluarExpresion(expresion, hechos);
    }

    // Evaluar si la regla es aplicable utilizando backtracking
    public boolean esAplicableConBacktracking(List<String> hechos, MotorDeInferencia motor) {
        String[] orCondiciones = expresion.split("\\|");
        for (String orCondicion : orCondiciones) {
            if (evaluarCondicionesAndConBacktracking(orCondicion.trim(), hechos, motor)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluarExpresion(String expresion, List<String> hechos) {
        String[] orCondiciones = expresion.split("\\|");
        for (String orCondicion : orCondiciones) {
            if (evaluarCondicionesAnd(orCondicion.trim(), hechos)) {
                return true;
            }
        }
        return false;
    }

    private boolean evaluarCondicionesAnd(String expresion, List<String> hechos) {
        String[] andCondiciones = expresion.split("&");
        for (String condicion : andCondiciones) {
            condicion = condicion.trim();
            boolean negado = false;
            if (condicion.startsWith("!")) {
                negado = true;
                condicion = condicion.substring(1).trim();
            }
            if ((negado && hechos.contains(condicion)) || (!negado && !hechos.contains(condicion))) {
                return false;
            }
        }
        return true;
    }

    private boolean evaluarCondicionesAndConBacktracking(String expresion, List<String> hechos, MotorDeInferencia motor) {
        String[] andCondiciones = expresion.split("&");
        for (String condicion : andCondiciones) {
            condicion = condicion.trim();
            boolean negado = false;
            if (condicion.startsWith("!")) {
                negado = true;
                condicion = condicion.substring(1).trim();
            }

            // Si está negado, verificar que el hecho no esté presente
            if (negado) {
                if (hechos.contains(condicion)) {
                    return false;
                }
            } else {
                // Si no está negado, intentar deducir el hecho con backtracking si no está presente
                if (!hechos.contains(condicion) && !motor.retroceder(condicion)) {
                    return false;
                }
            }
        }
        return true;
    }
}

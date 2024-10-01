package org.example;

import java.util.List;

public class Regla {
    private String expresion; // expresion completa de la regla
    private String conclusion; // conclusion de la regla

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

    private boolean evaluarExpresion(String expresion, List<String> hechos) {
        String[] orCondiciones = expresion.split("\\|\\|");
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
                condicion = condicion.substring(1).trim(); // Corregido aquí
            }
            if ((negado && hechos.contains(condicion)) || (!negado && !hechos.contains(condicion))) {
                return false;
            }
        }
        return true;
    }
}

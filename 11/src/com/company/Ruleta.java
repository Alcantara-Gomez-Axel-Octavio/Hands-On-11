package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ruleta<T> {
    private List<T> elementos;
    private List<Double> probabilidades; // Cambiado a Double
    private Random aleatorio;

    public Ruleta() {
        this.elementos = new ArrayList<>();
        this.probabilidades = new ArrayList<>();
        this.aleatorio = new Random();
    }

    public void agregarElemento(T elemento, double probabilidad) { // Cambiado a double
        elementos.add(elemento);
        probabilidades.add(probabilidad);
    }

    public void clear() {
        elementos.clear();
        probabilidades.clear();
    }

    public T girar() {
        double valorGiro = aleatorio.nextDouble(); // No es necesario convertir a double
        double sumaProbabilidades = 0.0; // Cambiado a double

        // Comprobar si el valor de giro es menor o igual a la primera probabilidad
        if (valorGiro <= probabilidades.get(0)) {
            return girar();  // Intentar de nuevo
        }

        // Iterar sobre las probabilidades y seleccionar el elemento correspondiente
        for (int i = 0; i < probabilidades.size(); i++) {
            sumaProbabilidades += probabilidades.get(i);

            if (valorGiro <= sumaProbabilidades) {
                return elementos.get(i);
            }
        }

        // En caso de que haya errores de redondeo
        return elementos.get(elementos.size() - 1);
    }

    public static void main(String[] args) {
        Ruleta<String> ruleta = new Ruleta<>();

        // Agregando elementos con sus respectivas probabilidades
        ruleta.agregarElemento("Opción 1", 0.6);
        ruleta.agregarElemento("Opción 2", 0.2);
        ruleta.agregarElemento("Opción 3", 0.2);

        // Girando la ruleta
        for (int i = 0; i < 10; i++) {
            String resultado = ruleta.girar();
            System.out.println("Giro " + (i + 1) + ": " + resultado);
        }
    }

    public List<T> getListaElementos() {
        return elementos;
    }
}

package com.company;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lineal {


    public static final int POBLACION = 100;
    public static final int MAX_GENERACIONES = 100;
    public static final double MUTATION_RATE = 0.75;  // Aumenta la tasa de mutación
    public static final double magnitudDeMutacion = 0.80;;  // Aumenta la magnitud de la mutación



    public static void main(String[] args) {
        
        // Definir el fitness deseado o la condición de parada
        double fitnessDeseado = 0.95; // ajusta el valor según sea necesario
        double totalFitness = 0;

        Ruleta<Individuo> ruleta = new Ruleta<>();

        // Crear una población inicial con 6 individuos
        ArrayList<Individuo> poblacionInicial = generarPoblacionInicial();


        ArrayList<Individuo> NuevaPoblacion = new ArrayList<>(poblacionInicial);
        poblacionInicial.addAll(NuevaPoblacion);


        int generacionActual = 0;

        System.out.println(poblacionInicial);
        System.out.println("\nGeneracion: " + generacionActual);
        System.out.println("Fitness del padre: "+ encontrarFitnessPadre(poblacionInicial));
        System.out.println("Posicion del padre: "+ encontrarPosicionPadre(poblacionInicial));
        totalFitness = totalFitness(NuevaPoblacion);
        System.out.println("total Fitness de la primera generacion: "+ totalFitness);
        System.out.println("Fitness promedio de la primera generacion: "+ porcentaje(POBLACION, totalFitness));


        while (encontrarFitnessPadre(NuevaPoblacion) < fitnessDeseado && generacionActual < MAX_GENERACIONES) {

            // Calcular el total de fitness de la población
            totalFitness = totalFitness(NuevaPoblacion);

            // Calcular el porcentaje real de cada individuo
            porcentaje(POBLACION, totalFitness);

            // Seleccionar al más fuerte de la generación
            int posicionPadre = encontrarPosicionPadre(NuevaPoblacion);

            // Elitismo: Mover el mejor individuo (padre) a la primera posición del ArrayList
            Individuo mejorPadre = NuevaPoblacion.get(posicionPadre);
            NuevaPoblacion.remove(posicionPadre);  // Remover el mejor individuo de su posición original
            NuevaPoblacion.add(0, mejorPadre);  // Agregar el mejor individuo en la primera posición de la nueva población
            System.out.println(NuevaPoblacion);  // Imprimir la nueva población (no poblacionInicial)



            // Limpiar la ruleta antes de agregar nuevos elementos
            ruleta.clear();


            totalFitness = 0;
            for (Individuo individuo : NuevaPoblacion) {
                totalFitness += individuo.getFitness();
            }

            // Agregar elementos a la ruleta (también al padre)
            ruleta.clear(); // Limpiar la ruleta antes de agregar nuevos elementos

            for (int i = 0; i < NuevaPoblacion.size(); i++) {
                double fitness = NuevaPoblacion.get(i).getFitness();
                double probabilidad = fitness / totalFitness;

                ruleta.agregarElemento(NuevaPoblacion.get(i), probabilidad);
            }



            double crossoverRate = 0.9; // o cualquier otro valor que desees
            poblacionInicial.clear();
            poblacionInicial.addAll(generarNuevaPoblacion(NuevaPoblacion, crossoverRate, ruleta));
            // Limpiar la población inicial y agregar la nueva población
            NuevaPoblacion.clear();
            NuevaPoblacion.addAll(poblacionInicial);



            // Imprimir información sobre la generación actual
            System.out.println("\nGeneracion: " + (generacionActual+1));
            System.out.println(NuevaPoblacion);
            System.out.println("Tamano del array: "+NuevaPoblacion.size());
            System.out.println("Fitness del padre: "+ encontrarFitnessPadre(NuevaPoblacion));
            System.out.println("Posicion del padre: "+ encontrarPosicionPadre(NuevaPoblacion));
            totalFitness = totalFitness(NuevaPoblacion);
            System.out.println("total Fitness: "+ totalFitness);
            System.out.println("Fitness promedio: "+ porcentaje(POBLACION, totalFitness));

            // Incrementar el contador de generaciones
            generacionActual++;
        }



    }

    public static ArrayList<Individuo> generarNuevaPoblacion(List<Individuo> poblacionActual, double tasaCruce, Ruleta<Individuo> ruleta) {
        ArrayList<Individuo> nuevaPoblacion = new ArrayList<>();

        // Agregar el individuo élite (el mejor individuo de la generación anterior) a la nueva población
        Individuo mejorPadre = poblacionActual.get(0);
        nuevaPoblacion.add(mejorPadre);

        Random random = new Random();

        // Generar nuevos individuos usando cruce y mutación
        while (nuevaPoblacion.size() < POBLACION) {
            // Realizar cruce con probabilidad de tasaCruce
            if (random.nextDouble() < tasaCruce) {
                // Seleccionar dos padres usando la ruleta
                Individuo padre1 = ruleta.girar();
                Individuo padre2 = ruleta.girar();

                // Realizar cruce para crear un nuevo individuo
                Individuo hijo = cruce(padre1, padre2);

                // Realizar mutación con probabilidad MUTATION_RATE
                if (random.nextDouble() < MUTATION_RATE) {
                    mutar(hijo);
                }

                nuevaPoblacion.add(hijo);
            } else {
                // Si no hay cruce, simplemente copiar un individuo aleatorio de la población anterior
                Individuo individuoAleatorio = poblacionActual.get(random.nextInt(POBLACION));
                nuevaPoblacion.add(new Individuo(individuoAleatorio.getB0(), individuoAleatorio.getB1(), 10));
            }
        }

        return nuevaPoblacion;
    }

    // Definir la operación de cruce
    private static Individuo cruce(Individuo padre1, Individuo padre2) {

        double nuevoB0 = (padre1.getB0() + padre2.getB0()) / 2;
        double nuevoB1 = (padre1.getB1() + padre2.getB1()) / 2;

        return new Individuo(nuevoB0, nuevoB1, 10);
    }

    // Definir la operación de mutación
    private static void mutar(Individuo individuo) {
        // Realizar la operación de mutación (puedes definir tu propia lógica aquí)

        // Crear una instancia de la clase Random para generar números aleatorios
        Random random = new Random();

        // Generar valores de mutación (deltaB0 y deltaB1) dentro del rango [-magnitudDeMutacion, magnitudDeMutacion)
        double deltaB0 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 2;
        double deltaB1 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 0.5;

        // Aplicar la mutación sumando los valores generados a los genes existentes del individuo
        individuo.setB0(individuo.getB0() + deltaB0);
        individuo.setB1(individuo.getB1() + deltaB1);
    }




    private static double porcentaje(int POBLACION, double totalFitness) {
        // Devolver el porcentaje promedio
        return totalFitness/POBLACION;
    }

    public static double totalFitness(List<Individuo> poblacion) {
        double totalFitness = 0;
        for (Individuo individuo : poblacion) {
            totalFitness += individuo.getFitness();
        }
        return totalFitness;
    }


    public static double encontrarFitnessPadre(List<Individuo> poblacion) {
        double fitnessMaximoEncontrado = 0;

        for (int i = 0; i < poblacion.size(); i++) {
            Individuo individuo = poblacion.get(i);

            if (individuo.getFitness()>fitnessMaximoEncontrado) {
                fitnessMaximoEncontrado = individuo.getFitness();

            }
        }

        return fitnessMaximoEncontrado;
    }

    public static int encontrarPosicionPadre(List<Individuo> poblacion) {
        double fitnessMaximoEncontrado = 0;
        int posicionPadre = 0;

        for (int i = 0; i < poblacion.size(); i++) {
            Individuo individuo = poblacion.get(i);

            if (individuo.getFitness()>fitnessMaximoEncontrado) {
                fitnessMaximoEncontrado = individuo.getFitness();
                posicionPadre = i;
            }
        }

        return posicionPadre;
    }



    public static double calcularFitnessLineal(List<DataSet> datos, double b0, double b1) {
        double error;
        double SSE = 0;
        double SST = 0;
        double x;
        double y;
        double sumaY = 0;

        for (DataSet p : datos) {
            x = p.getX();
            y = p.getY();
            double residuo = y - (b0 + b1 * x);
            SSE += residuo * residuo;
        }

        for (DataSet p : datos) {
            sumaY += p.getY();
        }
        double promedioY = sumaY / datos.size();

        for (DataSet p : datos) {
            x = p.getX();
            y = p.getY();
            double residuo = y - promedioY;
            SST += residuo * residuo;
        }

        // Evitar división por cero
        if (SST == 0) {
            error = 0;
        } else {
            error = 1 - SSE / SST;
        }

        if (error <= 0 || error > 1) {
            error = 0;
        }

        return error;
    }

    // Método para generar una población inicial con valores aleatorios para los genes B0 y B1
    public static ArrayList<Individuo> generarPoblacionInicial() {
        ArrayList<Individuo> poblacionInicial = new ArrayList<>();
        Random random = new Random();

        // Definir los rangos adecuados para b0 y b1 según la ecuación y = 44.12 + 0.5121X
        double rangoB0Min = 42.0;
        double rangoB0Max = 48.0;

        double rangoB1Min = 0.2;
        double rangoB1Max = 0.8;

        for (int i = 0; i < POBLACION; i++) {
            double b0 = rangoB0Min + random.nextDouble() * (rangoB0Max - rangoB0Min);
            double b1 = rangoB1Min + random.nextDouble() * (rangoB1Max - rangoB1Min);
            poblacionInicial.add(new Individuo(b0, b1, 10));
        }

        return poblacionInicial;
    }


}

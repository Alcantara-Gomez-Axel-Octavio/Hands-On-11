package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cubico {

        public static final int POBLACION = 10;
        public static final int MAX_GENERACIONES = 50;
        public static final double MUTATION_RATE = 0.75;
        public static final double magnitudDeMutacion = 0.80;


        public static void main(String[] args) {

            ArrayList<DataSet> datos = DataSet.LeerDatos();
            double fitnessDeseado = 0.95;
            double totalFitness = 0;

            Ruleta<Individuo> ruleta = new Ruleta<>();


            ArrayList<Individuo> poblacionInicial = generarPoblacionInicial(datos);
            ArrayList<Individuo> NuevaPoblacion = new ArrayList<>(poblacionInicial);

            int generacionActual = 0;

            System.out.println(poblacionInicial);
            System.out.println("\nGeneracion: " + generacionActual);
            System.out.println("Fitness del padre: " + encontrarFitnessPadre(poblacionInicial));
            System.out.println("Posicion del padre: " + encontrarPosicionPadre(poblacionInicial));
            totalFitness = totalFitness(NuevaPoblacion);
            System.out.println("Total Fitness de la primera generacion: " + totalFitness);
            System.out.println("Fitness promedio de la primera generacion: " + porcentaje(POBLACION, totalFitness));

            while (encontrarFitnessPadre(NuevaPoblacion) < fitnessDeseado && generacionActual < MAX_GENERACIONES) {
                totalFitness = totalFitness(NuevaPoblacion);
                porcentaje(POBLACION, totalFitness);

                int posicionPadre = encontrarPosicionPadre(NuevaPoblacion);
                Individuo mejorPadre = NuevaPoblacion.get(posicionPadre);
                NuevaPoblacion.remove(posicionPadre);
                NuevaPoblacion.add(0, mejorPadre);

                System.out.println(NuevaPoblacion);

                ruleta.clear();

                totalFitness = 0;
                for (Individuo individuo : NuevaPoblacion) {
                    totalFitness += individuo.getFitness();
                }

                ruleta.clear();
                for (int i = 0; i < NuevaPoblacion.size(); i++) {
                    double fitness = NuevaPoblacion.get(i).getFitness();
                    double probabilidad = fitness / totalFitness;
                    ruleta.agregarElemento(NuevaPoblacion.get(i), probabilidad);
                }

                double crossoverRate = 0.9;
                poblacionInicial.clear();
                poblacionInicial.addAll(generarNuevaPoblacion(NuevaPoblacion, crossoverRate, ruleta));
                NuevaPoblacion.clear();
                NuevaPoblacion.addAll(poblacionInicial);

                System.out.println("\nGeneracion: " + (generacionActual + 1));
                System.out.println(NuevaPoblacion);
                System.out.println("Tamaño del array: " + NuevaPoblacion.size());
                System.out.println("Fitness del padre: " + encontrarFitnessPadre(NuevaPoblacion));
                System.out.println("Posicion del padre: " + encontrarPosicionPadre(NuevaPoblacion));
                totalFitness = totalFitness(NuevaPoblacion);
                System.out.println("Total Fitness: " + totalFitness);
                System.out.println("Fitness promedio: " + porcentaje(POBLACION, totalFitness));

                generacionActual++;
            }
        }

        public static ArrayList<Individuo> generarNuevaPoblacion(List<Individuo> poblacionActual, double tasaCruce, Ruleta<Individuo> ruleta) {
            ArrayList<Individuo> nuevaPoblacion = new ArrayList<>();
            Individuo mejorPadre = poblacionActual.get(0);
            nuevaPoblacion.add(mejorPadre);
            Random random = new Random();

            while (nuevaPoblacion.size() < POBLACION) {
                if (random.nextDouble() < tasaCruce) {
                    Individuo padre1 = ruleta.girar();
                    Individuo padre2 = ruleta.girar();
                    Individuo hijo = cruce(padre1, padre2);
                    if (random.nextDouble() < MUTATION_RATE) {
                        mutar(hijo);
                    }
                    nuevaPoblacion.add(hijo);
                } else {
                    Individuo individuoAleatorio = poblacionActual.get(random.nextInt(POBLACION));
                    nuevaPoblacion.add(new Individuo(individuoAleatorio.getB0(), individuoAleatorio.getB1(), individuoAleatorio.getB2(), 10));
                }
            }
            return nuevaPoblacion;
        }

        private static Individuo cruce(Individuo padre1, Individuo padre2) {
            double nuevoB0 = (padre1.getB0() + padre2.getB0()) / 2;
            double nuevoB1 = (padre1.getB1() + padre2.getB1()) / 2;
            double nuevoB2 = (padre1.getB2() + padre2.getB2()) / 2;
            double nuevoB3 = (padre1.getB3() + padre2.getB3()) / 2;
            return new Individuo(nuevoB0, nuevoB1, nuevoB2, nuevoB3, 10);
        }

    private static void mutar(Individuo individuo) {
        Random random = new Random();
        double deltaB0 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 2;
        double deltaB1 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 0.5;
        double deltaB2 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 0.1;
        double deltaB3 = (random.nextDouble() * 2 - 1) * magnitudDeMutacion * 0.01;
        individuo.setB0(individuo.getB0() + deltaB0);
        individuo.setB1(individuo.getB1() + deltaB1);
        individuo.setB2(individuo.getB2() + deltaB2);
        individuo.setB3(individuo.getB3() + deltaB3);
    }

        private static double porcentaje(int POBLACION, double totalFitness) {
            return totalFitness / POBLACION;
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
                if (individuo.getFitness() > fitnessMaximoEncontrado) {
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
                if (individuo.getFitness() > fitnessMaximoEncontrado) {
                    fitnessMaximoEncontrado = individuo.getFitness();
                    posicionPadre = i;
                }
            }
            return posicionPadre;
        }

    public static double calcularFitnessCubico(List<DataSet> datos, double b0, double b1, double b2, double b3) {
        double SSRegression = 0;
        double SSResidual = 0;
        double x;
        double y;
        double yPromedio = promedioY(datos);

        // Calcular la suma de los cuadrados de la regresión (SSRegression)
        for (DataSet p : datos) {
            x = p.getX();
            y = p.getY();
            double estimado = b0 + b1 * x + b2 * Math.pow(x, 2) + b3 * Math.pow(x, 3);
            SSRegression += Math.pow(estimado - yPromedio, 2);
        }

        // Calcular la suma de los cuadrados residuales (SSResidual)
        for (DataSet p : datos) {
            y = p.getY();
            SSResidual += Math.pow(y - yPromedio, 2);
        }

        // Calcular el coeficiente de determinación R2
        double SSTotal = SSRegression + SSResidual;
        double R2 = 1 - (SSResidual / SSTotal);

        return R2;
    }







    private static double promedioY(List<DataSet> datos) {
            double sumaY = 0;
            for (DataSet p : datos) {
                sumaY += p.getY();
            }
            return sumaY / datos.size();
        }



        public static ArrayList<Individuo> generarPoblacionInicial(List<DataSet> datos) {
            ArrayList<Individuo> poblacionInicial = new ArrayList<>();
            Random random = new Random();

            double rangoB0Min = 60.0;
            double rangoB0Max = 70.0;

            double rangoB1Min = 0.5;
            double rangoB1Max = 1.0;

            double rangoB2Min = 0.00;
            double rangoB2Max = 0.03;

            double rangoB3Min = 0.00001;
            double rangoB3Max = 0.00009;

            for (int i = 0; i < POBLACION; i++) {
                double b0 = rangoB0Min + random.nextDouble() * (rangoB0Max - rangoB0Min);
                double b1 = rangoB1Min + random.nextDouble() * (rangoB1Max - rangoB1Min);
                double b2 = rangoB2Min + random.nextDouble() * (rangoB2Max - rangoB2Min);
                double b3 = rangoB3Min + random.nextDouble() * (rangoB3Max - rangoB3Min);
                poblacionInicial.add(new Individuo(b0, b1, b2, b3,10 ));
            }

            return poblacionInicial;
        }
    }


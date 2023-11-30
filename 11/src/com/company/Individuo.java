package com.company;


import java.util.ArrayList;

class Individuo {
    private double b0;
    private double b1;
    private double b2;
    private double b3;
    public double fitness;
    public static ArrayList<DataSet> datos= new ArrayList<>();

    public Individuo(double b0, double b1, double fitness) {
        this.b0 = b0;
        this.b1 = b1;
        this.fitness = calcularFitness();
    }

    public Individuo(double b0, double b1,  double b2, double fitness) {
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.fitness = calcularFitnessCuadratico();
    }

    public Individuo(double b0, double b1,  double b2,  double b3, double fitness) {
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.fitness = calcularFitnessCubico();
    }

    @Override
    public String toString() {
        String equation;
        if (b3 != 0) {
            equation = String.format("y = %.10f + %.10fx + %.10fx^2 + %.10fx^3, fitness=%.4f", b0, b1, b2, b3, fitness);
        } else if (b2 != 0) {
            equation = String.format("y = %.10f + %.10fx + %.10fx^2, fitness=%.4f", b0, b1, b2, fitness);
        } else {
            equation = String.format("y = %.10f + %.10fx, fitness=%.10f", b0, b1, fitness);
        }
        return "Individuo{" + equation + '}';
    }


    public double getB0() {
        return b0;
    }

    public double getB1() {
        return b1;
    }
    public double getB2() {
        return b2;
    }

    public double getB3() {
        return b3;
    }
    public double getFitness() {
        return fitness;
    }


    public double calcularFitness() {
        ArrayList<DataSet> datos = DataSet.LeerDatos();
        return Lineal.calcularFitnessLineal(datos, b0, b1);
    }

    public double calcularFitnessCuadratico() {
        ArrayList<DataSet> datos = DataSet.LeerDatos();
        return Cuadratico.calcularFitnessCuadratico(datos, b0, b1, b2);
    }

    public double calcularFitnessCubico() {
        ArrayList<DataSet> misDatos = DataSet.LeerDatos();
        return Cubico.calcularFitnessCubico(misDatos, b0, b1, b2, b3);
    }




    public double fitnessAsdouble() {
        // Return the fitness value as a double
        return this.fitness;
    }

    public void setB0(double nuevoB0) {
    }

    public void setB1(double nuevoB1) {
    }

    public void setB2(double nuevoB2) {
        this.b2 = nuevoB2;
    }

    public void setB3(double nuevoB3) {
        this.b3 = nuevoB3;
    }

}


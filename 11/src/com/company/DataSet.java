package com.company;

import java.util.ArrayList;

public class DataSet {

    public static ArrayList<DataSet> Datos = new ArrayList<>();

    private double X, Y;

    public DataSet(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public static ArrayList<DataSet> LeerDatos(){
        Datos.add(new DataSet(108,95));
        Datos.add(new DataSet(115,96));
        Datos.add(new DataSet(106,95));
        Datos.add(new DataSet(97,97));
        Datos.add(new DataSet(95,93));
        Datos.add(new DataSet(91,94));
        Datos.add(new DataSet(97,95));
        Datos.add(new DataSet(83,93));
        Datos.add(new DataSet(83,92));
        Datos.add(new DataSet(78,86));
        Datos.add(new DataSet(54,73));
        Datos.add(new DataSet(67,80));
        Datos.add(new DataSet(56,65));
        Datos.add(new DataSet(53,69));
        Datos.add(new DataSet(61,77));
        Datos.add(new DataSet(115,96));
        Datos.add(new DataSet(81,87));
        Datos.add(new DataSet(78,89));
        Datos.add(new DataSet(30,60));
        Datos.add(new DataSet(45,63));
        Datos.add(new DataSet(99,95));
        Datos.add(new DataSet(32,61));
        Datos.add(new DataSet(25,55));
        Datos.add(new DataSet(28,56));
        Datos.add(new DataSet(90,94));
        Datos.add(new DataSet(89,93));
        return Datos;

    }
}
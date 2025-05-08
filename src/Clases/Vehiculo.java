/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.util.ArrayList;

/**
 *
 * @author Jeronimo
 */
public class Vehiculo {
    private String placa;
    private Usuario Propietario;
    private  String Tipo_Vehiculo;

    public Vehiculo(String placa, Usuario Propietario, String Tipo_Vehiculo) {
        this.placa = placa;
        this.Propietario = Propietario;
        this.Tipo_Vehiculo = Tipo_Vehiculo;
     
}

    public String getPlaca() {
        return placa;
    }

    public Usuario getPropietario() {
        return Propietario;
    }

    public String getTipo_Vehiculo() {
        return Tipo_Vehiculo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
    
}

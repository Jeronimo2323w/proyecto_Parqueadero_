/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases.controladores;

import Clases.Usuario;
import Clases.Vehiculo;
import java.util.ArrayList;

/**
 *
 * @author Jeronimo
 */
public class Controlador_Vehiculo {

    private ArrayList<Vehiculo> vehiculos = new ArrayList<>();

    public Controlador_Vehiculo() {
        this.vehiculos = new ArrayList();
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return vehiculos;
    }

    public boolean RegistarVehiculo(String Tipo_Vehiculo, String Placa, int cedula, Controlador_Usuario controladorUsuario) {
        if (!Tipo_Vehiculo.equalsIgnoreCase("moto") && !Tipo_Vehiculo.equalsIgnoreCase("carro")) {
            System.out.println("tipo de vehiculo no valido, solo se puede carro o moto");
            return false;
        }
        Usuario usuario = controladorUsuario.BuscarUsuarioPorcedula(cedula);
        if (usuario != null) {
            ArrayList<Usuario> usuarios = new ArrayList<>();
            Vehiculo vehiculo = new Vehiculo(Placa, usuario, Tipo_Vehiculo);
            vehiculos.add(vehiculo);
            return true;
        } else {
            System.out.println("usuario no encontrado");
            return false;
        }
    }

    public void eliminarVehiculosPorUsuario(int cedula) {
        vehiculos.removeIf(v -> v.getPropietario().getCedula() == cedula);
    }

    public Vehiculo BuscarVehiculoporPlaca(String Placa) {
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().equalsIgnoreCase(Placa)) {
                return v;
            }
        }
        return null;
    }

}

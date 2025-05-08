/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto_parqueadero;

import Clases.Multa;
import Clases.Reserva;
import Clases.Usuario;
import Clases.Vehiculo;
import Clases.controladores.Controlador_Usuario;
import Clases.controladores.Controlador_Vehiculo;
import Clases.controladores.Controlador_reserva;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.Duration;

/**
 *
 * @author Jeronimo
 */
public class Proyecto_parqueadero {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        Controlador_Usuario controladorusuario = new Controlador_Usuario(listaUsuarios);
        Controlador_Vehiculo controladorvehiculo = new Controlador_Vehiculo();
        Controlador_reserva controladorReserva = new Controlador_reserva();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("\n Seleccione una opción:");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Buscar por cédula");
            System.out.println("4. Eliminar usuario");
            System.out.println("5. Mostrar vehículos");
            System.out.println("6.Buscar vehículo por placa");
            System.out.println("7. Registrar reserva");
            System.out.println("8. Mostrar la reserva");
            System.out.println("9. Registrar salida y verificar multa");
            System.out.println("10. Salir");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Correo: ");
                    String correo = scanner.nextLine();
                    System.out.print("Teléfono: ");
                    String telefono = scanner.nextLine();
                    System.out.print("Cédula: ");
                    int cedula = scanner.nextInt();
                    scanner.nextLine();

                    controladorusuario.AgregarUsuario(correo, nombre, telefono, cedula);
                    System.out.println("Usuario registrado correctamente.");

                    System.out.print("¿Desea registrar un vehículo para este usuario? (si): ");
                    String respuesta = scanner.nextLine();
                    if (respuesta.equalsIgnoreCase("si")) {
                        System.out.print("Tipo de vehículo (carro/moto): ");
                        String tipoVehiculo = scanner.nextLine();
                        System.out.print("Placa del vehículo: ");
                        String placa = scanner.nextLine();

                        boolean registrado = controladorvehiculo.RegistarVehiculo(tipoVehiculo, placa, cedula, controladorusuario);
                        if (registrado) {
                            System.out.println("Vehículo registrado correctamente.");
                        }
                    }
                    break;

                case 2:
                    System.out.println("Lista de usuarios:");
                    for (Usuario u : controladorusuario.getUsuarios()) {
                        System.out.print(u);
                    }
                    break;
                case 3:
                    System.out.print("Ingrese cédula a buscar: ");
                    int cedulaBuscar = scanner.nextInt();
                    Usuario encontrado = controladorusuario.BuscarUsuarioPorcedula(cedulaBuscar);
                    if (encontrado != null) {
                        System.out.println("Usuario encontrado: " + encontrado);
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;
                case 4:
                    System.out.print("Ingrese cédula a eliminar: ");
                    int cedulaEliminar = scanner.nextInt();
                    scanner.nextLine();
                    boolean eliminado = controladorusuario.EliminarUsuario(cedulaEliminar);
                    if (eliminado) {
                        controladorvehiculo.eliminarVehiculosPorUsuario(cedulaEliminar);
                        System.out.println("Usuario y sus vehículos eliminados.");
                    } else {
                        System.out.println("Usuario no encontrado.");
                    }
                    break;
                case 5:
                    System.out.println("Lista de vehículos:");
                    for (Vehiculo v : controladorvehiculo.getVehiculos()) {
                        System.out.println("Placa: " + v.getPlaca());
                        System.out.println("Tipo: " + v.getTipo_Vehiculo());
                        System.out.println("Propietario: " + v.getPropietario().getNombre() + " - Cédula: " + v.getPropietario().getCedula());
                        System.out.println("----------------------");
                    }
                    break;
                case 6:
                    System.out.print("Ingrese la placa del vehículo: ");
                    String placaBuscar = scanner.nextLine();
                    Vehiculo vehiculo = controladorvehiculo.BuscarVehiculoporPlaca(placaBuscar);
                    if (vehiculo != null) {
                        System.out.println("Vehículo encontrado:");
                        System.out.println("Placa: " + vehiculo.getPlaca());
                        System.out.println("Tipo: " + vehiculo.getTipo_Vehiculo());
                        System.out.println("Propietario: " + vehiculo.getPropietario().getNombre() + " - Cédula: " + vehiculo.getPropietario().getCedula());
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;
                case 7:
                    System.out.print("Ingrese la placa del vehículo para reservar: ");
                    String placaReserva = scanner.nextLine();
                    Vehiculo vehiculoReserva = controladorvehiculo.BuscarVehiculoporPlaca(placaReserva);

                    if (vehiculoReserva != null) {
                        System.out.print("Piso (1 o 2): ");
                        int piso = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Lugar (A1-A5 o B1-B5): ");
                        String lugar = scanner.nextLine().toUpperCase();

                        boolean ocupado = false;
                        for (Reserva r : controladorReserva.getReservas()) {
                            if (r.getPuesto().getPiso() == piso && r.getPuesto().getLugar().equalsIgnoreCase(lugar)) {
                                ocupado = true;
                                break;
                            }
                        }

                        if (ocupado) {
                            System.out.println("Ese lugar ya está ocupado. Intente con otro.");
                            break;

                        }

                        System.out.print("Número de cuenta bancaria: ");
                        String cuenta = scanner.nextLine();

                        System.out.print("Banco: ");
                        String banco = scanner.nextLine();

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime horaEntrada = null;
                        LocalTime horaSalida = null;

                        try {
                            System.out.print("Hora de entrada : ");
                            horaEntrada = LocalTime.parse(scanner.nextLine(), formatter);

                            System.out.print("Hora de salida : ");
                            horaSalida = LocalTime.parse(scanner.nextLine(), formatter);
                        } catch (DateTimeParseException e) {
                            System.out.println("Formato de hora inválido. Use el formato HH:mm (ej: 07:40).");
                            break;
                        }

                        int horas = (int) Duration.between(horaEntrada, horaSalida).toHours();
                        float precioCarro = 3000;
                        float precioMoto = 2100;

                        float totalCarro = horas * precioCarro;
                        float totalMoto = horas * precioMoto;

                        ArrayList<Vehiculo> vehiculos = new ArrayList<>();
                        vehiculos.add(vehiculoReserva);

                        String codigo = "R" + System.currentTimeMillis();

                        Reserva reserva = new Reserva("ocupado", horas, horaEntrada, horaSalida,
                                totalCarro, totalMoto, precioMoto, precioCarro, vehiculos,
                                cuenta, banco, horas, piso, lugar, codigo);

                        controladorReserva.getReservas().add(reserva);
                        System.out.println("Reserva registrada correctamente.");
                        System.out.println("Valor total: "
                                + (vehiculoReserva.getTipo_Vehiculo().equalsIgnoreCase("carro") ? totalCarro : totalMoto));
                    } else {
                        System.out.println("No se encontró un vehículo con esa placa.");
                    }
                    break;

                case 8:
                    ArrayList<Reserva> reservas = controladorReserva.getReservas();
                    if (reservas.isEmpty()) {
                        System.out.println("No hay reservas registradas.");
                    } else {
                        for (Reserva r : reservas) {
                            System.out.println("----- RESERVA -----");
                            System.out.println("Código: " + r.getPuesto().getCodigo());
                            System.out.println("Piso: " + r.getPuesto().getPiso());
                            System.out.println("Lugar: " + r.getPuesto().getLugar());
                            System.out.println("Hora de entrada: " + r.getHora_de_entrada());
                            System.out.println("Hora de salida: " + r.getHora_de_salida());
                            System.out.println("Tiempo (horas): " + r.getTiempo());

                            for (Vehiculo v : r.getVehiculo()) {
                                System.out.println("Vehículo: " + v.getPlaca() + " (" + v.getTipo_Vehiculo() + ")");
                                System.out.println("Propietario: " + v.getPropietario().getNombre());
                                float valor = v.getTipo_Vehiculo().equalsIgnoreCase("carro")
                                        ? r.getValor_Total_Carro()
                                        : r.getValor_Total_Moto();
                                System.out.println("Total a pagar: $" + valor);
                            }

                            System.out.println("Banco: " + r.getPago_bancario().getBanco());
                            System.out.println("Cuenta: " + r.getPago_bancario().getNumero_de_cuenta());
                            System.out.println("-------------------");
                        }
                    }
                    break;
                case 9:
                    System.out.print("Ingrese la placa del vehículo para registrar salida: ");
                    String placaSalida = scanner.nextLine();
                    Vehiculo vehiculoSalida = controladorvehiculo.BuscarVehiculoporPlaca(placaSalida);

                    if (vehiculoSalida != null) {
                        Reserva reserva = controladorReserva.obtenerReservaPorPlaca(placaSalida);
                        if (reserva != null) {
                            System.out.print("Hora real de salida (HH:mm): ");
                            String salidaRealTexto = scanner.nextLine();
                            LocalTime horaRealSalida;
                            try {
                                horaRealSalida = LocalTime.parse(salidaRealTexto, DateTimeFormatter.ofPattern("HH:mm"));
                            } catch (DateTimeParseException e) {
                                System.out.println("Formato inválido. Use HH:mm.");
                                break;
                            }

                            
                            Multa multa = new Multa(3000, 0, reserva);
                            multa.calcularMulta(horaRealSalida);

                            if (multa.getValor_total_de_Multa() > 0) {
                                System.out.println("️ Se generó una multa:");
                                System.out.println("Valor total de la multa: $" + multa.getValor_total_de_Multa());
                            } else {
                                System.out.println(" No hay multa. Salió a tiempo.");
                            }
                        } else {
                            System.out.println("No se encontró una reserva para este vehículo.");
                        }
                    } else {
                        System.out.println("Vehículo no encontrado.");
                    }
                    break;
                case 10:
                    System.out.println("Saliendo...");
                    return;

                default:
                    System.out.println("Opción inválida.");
            }
        }

    }

}

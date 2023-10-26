package org.orderutopia.upb;

import javax.swing.*;
import java.time.Duration;
import java.time.Instant;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

abstract class MainApp extends JFrame implements ActionListener {
    private JComboBox<String> mainOptions;
    private JTextArea resultArea;
    private Scanner scanner;

    private List<Cliente> clientes;
    private List<Operacion> operaciones;
    private boolean[] tableStatus;

    public MainApp() {
        setTitle("OrderUtopia");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);

        mainOptions = new JComboBox<>(new String[]{"Reservar Mesa", "Calificar Restaurante", "Registrar Cliente/Operación"});
        resultArea = new JTextArea(15, 30);
        scanner = new Scanner(System.in);

        mainOptions.addActionListener(this);

        clientes = new ArrayList<>();
        operaciones = new ArrayList<>();
        tableStatus = new boolean[10];

        add(mainOptions);
        add(resultArea);
    }

    static class ComboBoxExample extends JFrame {
        private JComboBox<String> mainOptions;
        private JButton button;

        public ComboBoxExample() {
            setTitle("ComboBox Example");
            setLayout(new FlowLayout());
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainOptions = new JComboBox<>();
            mainOptions.addItem("Jcombo 1");
            mainOptions.addItem("Jcombo 2");
            mainOptions.addItem("Jcombo 3");

            button = new JButton("Selecciona tu combo");

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = mainOptions.getSelectedIndex();
                    if (selectedIndex >= 0) {
                        JOptionPane.showMessageDialog(null, "Selected index: " + selectedIndex);
                    } else {
                        JOptionPane.showMessageDialog(null, "No option selected.");
                    }
                }
            });

            add(mainOptions);
            add(button);

            setSize(300, 150);
        }
    }

    class RestaurantBookingSystem {
        public static void main(String[] args) {
            int numberOfTables = 10; // Número total de mesas
            boolean[] tableStatus = new boolean[numberOfTables]; // Un arreglo para el estado de cada mesa

            Scanner scanner = new Scanner(System.in);

            while (true) {
                // Mostrar el estado de las mesas
                StringBuilder tableStatusMessage = new StringBuilder("Estado de las mesas:\n");
                for (int i = 0; i < numberOfTables; i++) {
                    String status = tableStatus[i] ? "Ocupada" : "Disponible";
                    tableStatusMessage.append("Mesa ").append(i + 1).append(": ").append(status).append("\n");
                }
                JOptionPane.showMessageDialog(null, tableStatusMessage.toString());

                // Solicitar al usuario la mesa que desea reservar
                String tableChoiceInput = JOptionPane.showInputDialog("Ingrese el número de la mesa que desea reservar (1-" + numberOfTables + "):");
                int tableChoice;

                try {
                    tableChoice = Integer.parseInt(tableChoiceInput);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Número de mesa no válido. Por favor, elija una mesa válida.");
                    continue;
                }

                if (tableChoice < 1 || tableChoice > numberOfTables) {
                    JOptionPane.showMessageDialog(null, "Número de mesa no válido. Por favor, elija una mesa válida.");
                    continue;
                }

                // Verificar si la mesa elegida está disponible
                if (!tableStatus[tableChoice - 1]) {
                    tableStatus[tableChoice - 1] = true; // Marcar la mesa como ocupada
                    JOptionPane.showMessageDialog(null, "La Mesa " + tableChoice + " se ha reservado con éxito.");
                } else {
                    JOptionPane.showMessageDialog(null, "La Mesa " + tableChoice + " ya está ocupada. Por favor, elija otra mesa.");
                }

                // Preguntar al usuario si desea reservar otra mesa
                String choice = JOptionPane.showInputDialog("¿Desea reservar otra mesa? (sí/no):");
                if (choice == null || !choice.toLowerCase().equals("si")) {
                    break; // Salir del bucle si el usuario no desea reservar más mesas
                }
            }

            JOptionPane.showMessageDialog(null, "¡Gracias por usar el sistema de reservas de OrderUtopia!");
        }
    }


    private void reserveTable() {
        resultArea.setText(""); // Limpia el área de resultados

        // Mostrar el estado actual de las mesas
        resultArea.append("Estado de las mesas:\n");
        for (int i = 0; i < tableStatus.length; i++) {
            String status = tableStatus[i] ? "Ocupada" : "Disponible";
            resultArea.append("Mesa " + (i + 1) + ": " + status + "\n");
        }

        // Solicitar al usuario la elección de la mesa
        int tableNumber = -1;

        try {
            tableNumber = Integer.parseInt(JOptionPane.showInputDialog("Seleccione el número de mesa que desea reservar (1-" + tableStatus.length + "):"));
        } catch (NumberFormatException e) {
            resultArea.append("Entrada no válida. Debe ingresar un número de mesa válido.");
            return;
        }

        if (tableNumber < 1 || tableNumber > tableStatus.length) {
            resultArea.append("Número de mesa no válido. Debe seleccionar una mesa entre 1 y " + tableStatus.length + ".");
            return;
        }

        // Verificar si la mesa seleccionada está disponible
        if (tableStatus[tableNumber - 1]) {
            resultArea.append("La mesa " + tableNumber + " ya está ocupada. Por favor, seleccione otra mesa.");
        } else {
            tableStatus[tableNumber - 1] = true;
            resultArea.append("Mesa " + tableNumber + " reservada con éxito.");
        }
    }

    private void rateRestaurant() {
        resultArea.setText(""); // Limpia el área de resultados

        // Solicitar al usuario una calificación del restaurante
        int rating = -1;

        try {
            rating = Integer.parseInt(JOptionPane.showInputDialog("Califique el restaurante (1-5):"));
        } catch (NumberFormatException e) {
            resultArea.append("Entrada no válida. Debe ingresar un número del 1 al 5.");
            return;
        }

        if (rating < 1 || rating > 5) {
            resultArea.append("Calificación no válida. Debe calificar el restaurante del 1 al 5.");
            return;
        }

        resultArea.append("¡Gracias por calificar el restaurante con " + rating + " estrellas!");
    }


    private void registerClientAndOperation() {
        resultArea.setText(""); // Limpia el área de resultados

        // Solicitar al usuario los datos del cliente
        String nombreCliente = JOptionPane.showInputDialog("Nombre del Cliente:");
        String dniCliente = JOptionPane.showInputDialog("DNI del Cliente:");
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID del Cliente:"));
        String objetoSeleccionado = JOptionPane.showInputDialog("Objeto Seleccionado:");

        Cliente nuevoCliente = new Cliente(nombreCliente, dniCliente, idCliente, objetoSeleccionado);
        clientes.add(nuevoCliente);

        // Solicitar al usuario los datos de la operación
        String nombreOperario = JOptionPane.showInputDialog("Nombre del Operario:");
        int idOperario = Integer.parseInt(JOptionPane.showInputDialog("ID del Operario:"));
        String horaVenta = JOptionPane.showInputDialog("Hora de Venta:");

        Operacion nuevaOperacion = new Operacion(nombreOperario, idOperario, horaVenta);
        operaciones.add(nuevaOperacion);

        resultArea.append("Los datos se han registrado con éxito.");

        // Guardar los datos en archivos de texto
        guardarClientesEnArchivo("clientes.txt");
        guardarOperacionesEnArchivo("operaciones.txt");
    }
    private void guardarClientesEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            Cliente cliente = clientes.get(clientes.size() - 1); // Obtener el último cliente registrado
            writer.write(cliente.getNombreCliente() + "," + cliente.getDniCliente() + "," + cliente.getIdCliente() + "," + cliente.getObjetoSeleccionado());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarOperacionesEnArchivo(String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            Operacion operacion = operaciones.get(operaciones.size() - 1); // Obtener la última operación registrada
            writer.write(operacion.getNombreOperario() + "," + operacion.getIdOperario() + "," + operacion.getHoraVenta());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        List<Cliente> clientes = new ArrayList<>();
        List<Operacion> operaciones = new ArrayList<>();

        leerClientes(clientes, "clientes.txt");
        leerOperaciones(operaciones, "operaciones.txt");

        String nombreCliente = JOptionPane.showInputDialog("Nombre del Cliente:");
        String dniCliente = JOptionPane.showInputDialog("DNI del Cliente:");
        int idCliente = Integer.parseInt(JOptionPane.showInputDialog("ID del Cliente:"));

        Cliente nuevoCliente = new Cliente(nombreCliente, dniCliente, idCliente);
        clientes.add(nuevoCliente);

        String nombreOperario = JOptionPane.showInputDialog("Nombre del Operario:");
        int idOperario = Integer.parseInt(JOptionPane.showInputDialog("ID del Operario:"));
        String horaVenta = JOptionPane.showInputDialog("Hora de Venta:");

        Operacion nuevaOperacion = new Operacion(nombreOperario, idOperario, horaVenta);
        operaciones.add(nuevaOperacion);

        escribirClientes(clientes, "clientes.txt");
        escribirOperaciones(operaciones, "operaciones.txt");

        SwingUtilities.invokeLater(() -> {
            OrderUtopiaApp app = new OrderUtopiaApp();
            app.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            ComboBoxExample example = new ComboBoxExample();
            example.setVisible(true);
        });
    }

    public static void leerClientes(List<Cliente> clientes, String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 4) {
                    String nombre = datos[0];
                    String dni = datos[1];
                    int id = Integer.parseInt(datos[2]);
                    String objeto = datos[3];
                    Cliente cliente = new Cliente(nombre, dni, id, objeto);
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void leerOperaciones(List<Operacion> operaciones, String archivo) {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                if (datos.length == 3) {
                    String nombreOperario = datos[0];
                    int idOperario = Integer.parseInt(datos[1]);
                    String horaVenta = datos[2];
                    Operacion operacion = new Operacion(nombreOperario, idOperario, horaVenta);
                    operaciones.add(operacion);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void escribirClientes(List<Cliente> clientes, String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Cliente cliente : clientes) {
                writer.write(cliente.getNombreCliente() + "," + cliente.getDniCliente() + "," + cliente.getIdCliente() + "," + cliente.getObjetoSeleccionado());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void escribirOperaciones(List<Operacion> operaciones, String archivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (Operacion operacion : operaciones) {
                writer.write(operacion.getNombreOperario() + "," + operacion.getIdOperario() + "," + operacion.getHoraVenta());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Cliente {
    private String nombreCliente;
    private String dniCliente;
    private int idCliente;
    private String objetoSeleccionado;

    public Cliente(String nombreCliente, String dniCliente, int idCliente, String objetoSeleccionado) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.idCliente = idCliente;
        this.objetoSeleccionado = objetoSeleccionado;
    }

    public Cliente(String nombreCliente, String dniCliente, int idCliente) {
        this.nombreCliente = nombreCliente;
        this.dniCliente = dniCliente;
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getObjetoSeleccionado() {
        return objetoSeleccionado;
    }

    public void setObjetoSeleccionado(String objetoSeleccionado) {
        this.objetoSeleccionado = objetoSeleccionado;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "nombreCliente='" + nombreCliente + '\'' +
                ", dniCliente='" + dniCliente + '\'' +
                ", idCliente=" + idCliente +
                ", objetoSeleccionado='" + objetoSeleccionado + '\'' +
                '}';
    }
}
class Operacion {
    private String nombreOperario;
    private int idOperario;
    private String horaVenta;

    public Operacion(String nombreOperario, int idOperario, String horaVenta) {
        this.nombreOperario = nombreOperario;
        this.idOperario = idOperario;
        this.horaVenta = horaVenta;
    }

    public String getNombreOperario() {
        return nombreOperario;
    }

    public void setNombreOperario(String nombreOperario) {
        this.nombreOperario = nombreOperario;
    }

    public int getIdOperario() {
        return idOperario;
    }

    public void setIdOperario(int idOperario) {
        this.idOperario = idOperario;
    }

    public String getHoraVenta() {
        return horaVenta;
    }

    public void setHoraVenta(String horaVenta) {
        this.horaVenta = horaVenta;
    }

    @Override
    public String toString() {
        return "Operacion{" +
                "nombreOperario='" + nombreOperario + '\'' +
                ", idOperario=" + idOperario +
                ", horaVenta='" + horaVenta + '\'' +
                '}';
    }
}
class Alimento {
    String nombre;
    double precio;

    public Alimento(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
class Pedido {
    String nombre;
    double precio;

    public Pedido(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
}
 class RegistroLogin {

    private static Instant inicio;
    private static Duration duracionTotal;

    public static void main(String[] args) {
        inicio = Instant.now();
        duracionTotal = Duration.ZERO;

        while (true) {
            String[] opciones = {"Registrar", "Iniciar Sesión", "Salir"};
            int opcion = JOptionPane.showOptionDialog(null, "Seleccione una opción:", "Registro de Sesión", 0, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            Instant antes = Instant.now();

            switch(opcion) {
                case 0:
                    registrarUsuario();
                    break;

                case 1:
                    iniciarSesion();
                    break;

                case 2:
                    JOptionPane.showMessageDialog(null, "Gracias por usar el sistema. ¡Hasta luego!");
                    System.exit(0);
            }

            Instant despues = Instant.now();
            duracionTotal = duracionTotal.plus(Duration.between(antes, despues));
        }

        //JOptionPane.showMessageDialog(null, "Estuvo en el menú por un total de: " + duracionTotal.toSeconds() + " segundos.");
    }
    private static void iniciarSesion() {
        String nombreUsuario = JOptionPane.showInputDialog("Ingrese su nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese su contraseña:");

        if (nombreUsuario != null && contrasena != null) {
            if (nombreUsuario.equals("usuarioEjemplo") && contrasena.equals("contrasenaEjemplo")) {
                JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso. ¡Bienvenido!");
            } else {
                JOptionPane.showMessageDialog(null, "Credenciales incorrectas. Inténtelo de nuevo.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Inicio de sesión cancelado.");
        }
    }
    private static void registrarUsuario() {
        String nombreUsuario = JOptionPane.showInputDialog("Ingrese un nombre de usuario:");
        String contrasena = JOptionPane.showInputDialog("Ingrese una contraseña:");

        if (nombreUsuario != null && contrasena != null) {
            JOptionPane.showMessageDialog(null, "Usuario registrado con éxito.");
        } else {
            JOptionPane.showMessageDialog(null, "Registro de usuario cancelado.");
        }
    }

}

public class OrderUtopiaApp extends JFrame implements ActionListener {
    private JComboBox<String> secciones;
    private JButton mostrarCartaBtn;
    private JTextArea cartaTextArea;
    private JLabel numItemsLabel;
    private JTextField numItemsField;
    private JButton ordenarBtn;
    private JTextArea facturaTextArea;
    private List<Alimento> OtrasDelicias;
    private List<Alimento> hamburguesas;
    private List<Alimento> sandwiches;
    private List<Alimento> adicionales;
    private List<Pedido> pedido;
    private String nombreCliente;
    private String identificacionCliente;
    public OrderUtopiaApp() {
        nombreCliente = JOptionPane.showInputDialog("Por favor, ingrese su nombre:");
        identificacionCliente = JOptionPane.showInputDialog("Por favor, ingrese su ID:");

        OtrasDelicias = new ArrayList<>();
        hamburguesas = new ArrayList<>();
        sandwiches = new ArrayList<>();
        adicionales = new ArrayList<>();
        pedido = new ArrayList<>();

        OtrasDelicias.add(new Alimento("1.  Hot dog clasico", 9000));
        OtrasDelicias.add(new Alimento("2.  Hot dog pollo", 14000));
        OtrasDelicias.add(new Alimento("3.  Hot dog box", 16000));
        OtrasDelicias.add(new Alimento("4.  Ensalada mexicana", 17000));
        OtrasDelicias.add(new Alimento("5.  paquetaco tostacos", 10000));
        OtrasDelicias.add(new Alimento("6.  nachos mexicanos", 20000));
        OtrasDelicias.add(new Alimento("7.  salchipapa", 9000));
        OtrasDelicias.add(new Alimento("8.  choripapa", 10000));
        OtrasDelicias.add(new Alimento("9.  papas locas", 20000));
        OtrasDelicias.add(new Alimento("10. pechuga", 19000));
        OtrasDelicias.add(new Alimento("11. desgranados", 20000));
        OtrasDelicias.add(new Alimento("12. empanadas", 3900));
        OtrasDelicias.add(new Alimento("13. flautas", 4000));

        hamburguesas.add(new Alimento("1. hamburguesa clasica", 17000));
        hamburguesas.add(new Alimento("2. hamburguesa bacon", 19000));
        hamburguesas.add(new Alimento("3. hamburguesa mixta", 23000));
        hamburguesas.add(new Alimento("4. hamburguesa baucha", 23000));
        hamburguesas.add(new Alimento("5. hamburguesa costeña", 23000));
        hamburguesas.add(new Alimento("6. hamburguesa superbox", 26000));

        sandwiches.add(new Alimento("1. Sandwich de pollo", 12000));
        sandwiches.add(new Alimento("2. Sandwich de jamón y queso", 10000));
        sandwiches.add(new Alimento("3. Sandwich vegetariano", 11000));
        sandwiches.add(new Alimento("4. Sandwich de roast beef", 15000));
        sandwiches.add(new Alimento("5. Sandwich de pavo", 13000));
        sandwiches.add(new Alimento("6. Club sandwich", 14000));

        adicionales.add(new Alimento("1. papas francesa", 8000));
        adicionales.add(new Alimento("2. tocineta", 4000));
        adicionales.add(new Alimento("3. pepinillos", 3000));
        adicionales.add(new Alimento("4. jalapeños", 3000));
        adicionales.add(new Alimento("5. queso americano", 3000));

        setTitle("OrderUtopia");
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);

        String[] seccionNames = {"Otras Delicias", "Hamburguesas", "Sandwiches", "Adicionales"};
        secciones = new JComboBox<>(seccionNames);
        mostrarCartaBtn = new JButton("Mostrar Carta");
        cartaTextArea = new JTextArea(15, 30);
        numItemsLabel = new JLabel("Número de alimentos a ordenar:");
        numItemsField = new JTextField(10);
        ordenarBtn = new JButton("Ordenar");
        facturaTextArea = new JTextArea(15, 30);

        secciones.addActionListener(this);
        mostrarCartaBtn.addActionListener(this);
        ordenarBtn.addActionListener(this);

        add(secciones);
        add(mostrarCartaBtn);
        add(cartaTextArea);
        add(numItemsLabel);
        add(numItemsField);
        add(ordenarBtn);
        add(facturaTextArea);
    }


    private void mostrarFactura(double total) {
        StringBuilder factura = new StringBuilder();
        factura.append("Factura:\n");
        factura.append("Nombre del cliente: ").append(nombreCliente).append("\n");
        factura.append("ID del cliente: ").append(identificacionCliente).append("\n");
        factura.append("Alimento      Precio\n");

        for (Pedido item : pedido) {
            factura.append(item.nombre).append("   ").append(item.precio).append("\n");
        }

        factura.append("Total: ").append(total).append(" pesos");
        facturaTextArea.setText(factura.toString());
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == secciones) {
            int selectedIndex = secciones.getSelectedIndex();
            List<Alimento> selectedSection = null;
            String seccionTitle = "";

            switch (selectedIndex) {
                case 0:
                    selectedSection = OtrasDelicias;
                    seccionTitle = "Otras Delicias";
                    break;
                case 1:
                    selectedSection = hamburguesas;
                    seccionTitle = "Hamburguesas";
                    break;
                case 2:
                    selectedSection = sandwiches;
                    seccionTitle = "Sandwiches";
                    break;
                case 3:
                    selectedSection = adicionales;
                    seccionTitle = "Adicionales";
                    break;
                default:
                    break;
            }

            if (selectedSection != null) {
                mostrarCarta(selectedSection, seccionTitle);
            }
        } else if (e.getSource() == mostrarCartaBtn) {
            int selectedIndex = secciones.getSelectedIndex();
            List<Alimento> selectedSection = null;
            String seccionTitle = "";

            switch (selectedIndex) {
                case 0:
                    selectedSection = OtrasDelicias;
                    seccionTitle = "Otras Delicias";
                    break;
                case 1:
                    selectedSection = hamburguesas;
                    seccionTitle = "Hamburguesas";
                    break;
                case 2:
                    selectedSection = sandwiches;
                    seccionTitle = "Sandwiches";
                    break;
                case 3:
                    selectedSection = adicionales;
                    seccionTitle = "Adicionales";
                    break;
                default:
                    break;
            }

            if (selectedSection != null) {
                mostrarCarta(selectedSection, seccionTitle);
            }
        } else if (e.getSource() == ordenarBtn) {
            int numItems = Integer.parseInt(numItemsField.getText());
            pedido.clear();
            double total = 0.0;

            for (int i = 0; i < numItems; i++) {
                int choice = Integer.parseInt(JOptionPane.showInputDialog("Alimento #" + (i + 1) + ":"));

                List<Alimento> selectedSection = null;

                switch (secciones.getSelectedIndex()) {
                    case 0:
                        selectedSection = OtrasDelicias;
                        break;
                    case 1:
                        selectedSection = hamburguesas;
                        break;
                    case 2:
                        selectedSection = sandwiches;
                        break;
                    case 3:
                        selectedSection = adicionales;
                        break;
                    default:
                        break;
                }

                if (choice >= 1 && choice <= selectedSection.size()) {
                    total += selectedSection.get(choice - 1).precio;
                    pedido.add(new Pedido(selectedSection.get(choice - 1).nombre, selectedSection.get(choice - 1).precio));
                } else {
                    JOptionPane.showMessageDialog(null, "Selección no válida. Ignorando este alimento.");
                }
            }

            JOptionPane.showMessageDialog(null, "El precio total de tu pedido es: " + total + " pesos.");

            char deseaFactura = JOptionPane.showInputDialog("¿Deseas ver la factura? (S/N):").toLowerCase().charAt(0);

            if (deseaFactura == 's') {
                mostrarFactura(total);
            }
        }
    }
    private void mostrarCarta(List<Alimento> seccion, String seccionTitle) {
        StringBuilder carta = new StringBuilder();
        carta.append(seccionTitle).append(":\n");

        for (int i = 0; i < seccion.size(); i++) {
            carta.append(i + 1).append(". ").append(seccion.get(i).nombre).append(" - Precio: ").append(seccion.get(i).precio).append("\n");
        }

        cartaTextArea.setText(carta.toString());
    }
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OrderUtopiaApp app = new OrderUtopiaApp();
            app.setVisible(true);
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package archivo_cel;

import java.io.*;
import javax.swing.JOptionPane;

/**
 *
 * @author wilton
 */
public class Archivo_CEL {

    public static void main(String[] args) {
        File file = new File("Comidas.txt");
        String opcion = "";

        do {
            opcion = JOptionPane.showInputDialog(
                "MENÚ COMIDAS\n" +
                "1. Registrar\n" +
                "2. Leer archivo\n" +
                "3. Copiar archivo\n" +
                "4. Consultar por comida\n" +
                "5. Modificar comida\n" +
                "6. Limpiar archivo\n" +
                "7. Eliminar archivo\n" +
                "8. Salir\n\n" +
                "Seleccione una opción:"
            );

            if (opcion == null) {
                break;
            }

            switch (opcion) {
                case "1":
                    crearRegistro(file);
                    break;
                case "2":
                    leerArchivo(file);
                    break;
                case "3":
                    copiarArchivo(file);
                    break;
                case "4":
                    consultarComida(file);
                    break;
                case "5":
                    modificarRegistro(file);
                    break;
                case "6":
                    limpiarArchivo(file);
                    break;
                case "7":
                    borrarArchivo(file);
                    break;
                case "8":
                    JOptionPane.showMessageDialog(null, "Suerte pues");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Estaba muy claro parcero");
            }
        } while (!opcion.equals("8"));
    }

    //Registrar comida
    public static void crearRegistro(File file) {
        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String nombre = JOptionPane.showInputDialog("Comida chimba?: ");
            String ingrediente = JOptionPane.showInputDialog("Ingrediente principal?: ");
            String veces = JOptionPane.showInputDialog("Cuántas veces has comido?: ");
            String preciostr = JOptionPane.showInputDialog("Cuánto vale?: ");
            double precio = Double.parseDouble(preciostr);

            bw.write(nombre + ", hecha con " + ingrediente + ", comiste " + veces + " veces, vale: " + precio);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error en el archivo" + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Error en los numeros");
        }
    }

    // Leer archivo
    public static void leerArchivo(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Se debe crear un archivo primero");
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            String texto = "";
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
            br.close();
            JOptionPane.showMessageDialog(null, texto.isEmpty() ? "Archivo vacío" : texto);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer" + e.getMessage());
        }
    }

    //Copiar archivo
    public static void copiarArchivo(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No hay archivo");
            return;
        }

        File fileCopia = new File("Comidas_Copia.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileCopia, false)); // false sobreescribe si habia una copia previa
            
            String linea;
            while ((linea = br.readLine()) != null) {
                bw.write(linea);
                bw.newLine();
            }
            br.close();
            bw.close();
            JOptionPane.showMessageDialog(null, "Archivo registrado como:'Comidas_Copia.txt'");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al copiar" + e.getMessage());
        }
    }

    // Consultar 
    public static void consultarComida(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No hay archivo");
            return;
        }

        String buscar = JOptionPane.showInputDialog("¿Qué comida deseas buscar?");
        if (buscar == null || buscar.isEmpty()) return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            String resultados = "Búsqueda: ";
            boolean encontrado = false;

            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(buscar.toLowerCase())) {
                    resultados += linea + "\n";
                    encontrado = true;
                }
            }
            br.close();

            if (encontrado) {
                JOptionPane.showMessageDialog(null, resultados);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró su búsqueda");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar" + e.getMessage());
        }
    }

    // Modificar 
    public static void modificarRegistro(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No hay archivo");
            return;
        }
        
        leerArchivo(file);

        String buscar = JOptionPane.showInputDialog("Qué quieres modificar? ");
        if (buscar == null || buscar.isEmpty()) return;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String linea;
            String contenidoTotal = "";
            boolean modificado = false;

            while ((linea = br.readLine()) != null) {
                if (linea.toLowerCase().contains(buscar.toLowerCase()) && !modificado) {
                   
                        String nuevoNombre = JOptionPane.showInputDialog("Nuevo nombre de comida:");
                        String nuevoIngrediente = JOptionPane.showInputDialog("Nuevo ingrediente:");
                        String nuevasVeces = JOptionPane.showInputDialog("Nuevas veces:");
                        String nuevoPrecio = JOptionPane.showInputDialog("Nuevo precio:");
                        
                        linea = nuevoNombre + ", hecha con " + nuevoIngrediente + ", comiste " + nuevasVeces + " veces, precio: $" + nuevoPrecio;
                        modificado = true;
                    
                }
                contenidoTotal += linea + "\n";
            }
            br.close();

            if (modificado) {
                // Reescribir todo el archivo, false para sobreescribir
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
                bw.write(contenidoTotal);
                bw.close();
                JOptionPane.showMessageDialog(null, "Se modificó el registro");
            } else {
                JOptionPane.showMessageDialog(null, "No se completó la acción");
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al modificar" + e.getMessage());
        }
    }

    //  Limpiar archivo (vaciar su contenido sin borrarlo)
    public static void limpiarArchivo(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No hay archivo");
            return;
        }

        try {
            FileWriter fw = new FileWriter(file, false); // Abrir FileWriter con false vacía automáticamente el archivo
            fw.close();
            JOptionPane.showMessageDialog(null, "Archivo vacío");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al limpiar el archivo" + e.getMessage());
        }
    }

    // Borrar archivo
    public static void borrarArchivo(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "No hay archivo");
            return;
        }

        String respuesta = JOptionPane.showInputDialog("Quieres borrar 'Comidas.txt'?\n" + "1. Sí\n" +  "2. No");

    if (respuesta != null) {
        switch (respuesta) {
            case "1":
                if (file.delete()) {
                    JOptionPane.showMessageDialog(null, "Archivo eliminado");
                } else {
                    JOptionPane.showMessageDialog(null, "No es posible borrar el archivo");
                }
                break;
            
            case "2":
                JOptionPane.showMessageDialog(null, "Cancelado");
                break;
            
            default:
                JOptionPane.showMessageDialog(null, "Tas bobito?");
                break;
        }
    }
    }
}
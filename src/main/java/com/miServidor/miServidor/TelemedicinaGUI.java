package com.miServidor.miServidor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class TelemedicinaGUI extends JFrame {

    // Datos de conexión a MySQL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/telemedicina";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "patatagaming2323@#";

    private JButton btnMostrarDoctores;
    private JButton btnMostrarPacientes;

    public TelemedicinaGUI() {
        setTitle("Sistema de Telemedicina");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new FlowLayout());
        btnMostrarDoctores = new JButton("Mostrar Doctores");
        btnMostrarPacientes = new JButton("Mostrar Pacientes");

        panel.add(btnMostrarDoctores);
        panel.add(btnMostrarPacientes);
        add(panel, BorderLayout.NORTH);

        // Acción para mostrar doctores
        btnMostrarDoctores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> model = getDoctoresListModel();
                JList<String> list = new JList<>(model);
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setPreferredSize(new Dimension(400, 200));
                JOptionPane.showMessageDialog(null, scrollPane, "Lista de Doctores", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Acción para mostrar pacientes
        btnMostrarPacientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultListModel<String> model = getPacientesListModel();
                JList<String> list = new JList<>(model);
                JScrollPane scrollPane = new JScrollPane(list);
                scrollPane.setPreferredSize(new Dimension(400, 200));
                JOptionPane.showMessageDialog(null, scrollPane, "Lista de Pacientes", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    /**
     * Consulta la tabla 'doctores' y crea un DefaultListModel para mostrarlos.
     */
    private DefaultListModel<String> getDoctoresListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre, especialidad FROM doctores")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String especialidad = rs.getString("especialidad");
                String item = "ID: " + id + " - " + nombre + " (" + especialidad + ")";
                model.addElement(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al recuperar doctores: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    /**
     * Consulta la tabla 'pacientes' y crea un DefaultListModel para mostrarlos.
     */
    private DefaultListModel<String> getPacientesListModel() {
        DefaultListModel<String> model = new DefaultListModel<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, nombre, doctor_id, posicion_cola, estado FROM pacientes")) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                int doctorId = rs.getInt("doctor_id");
                int posicion = rs.getInt("posicion_cola");
                String estado = rs.getString("estado");
                String item = "ID: " + id + " - " + nombre +
                        " (Doctor ID: " + doctorId + ", Posición: " + posicion + ", Estado: " + estado + ")";
                model.addElement(item);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al recuperar pacientes: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return model;
    }

    public static void main(String[] args) {
        // Asegurarse de cargar el driver de MySQL (opcional para versiones modernas de JDBC)
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TelemedicinaGUI().setVisible(true);
            }
        });
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package letrucdebryan;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author Soul
 */
public class TableAffichage extends JFrame {

    public TableAffichage() {
        super();

        setTitle("Datas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTable table = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/champions", "root", null);
            insertData("amaury", 600, "mage noir", "midlaner");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from champions");
            ResultSetMetaData resultMeta = rs.getMetaData();
            String[] columnNames = new String[resultMeta.getColumnCount()];
            for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
                columnNames[i - 1] = resultMeta.getColumnName(i);
            }

            ArrayList<Object[]> data = new ArrayList<>();
            while (rs.next()) {
                Object[] line = new Object[resultMeta.getColumnCount()]; // on créé un tableau pour stocker la ligne courante
                for (int i = 1; i <= resultMeta.getColumnCount(); i++) {
                    line[i - 1] = rs.getObject(i);
                }
                data.add(line); // on ajoute la ligne à la liste
            }
            table = new JTable(data.stream().toArray(Object[][]::new), columnNames); // on convertit la liste en tableau pour appeler le constructeur et créer une seule JTable
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        pack();
    }

    public void insertData(String name, int hp, String type, String role) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/champions", "root", null);
            String query = " insert into champions (name, health_points, type, role)"
                    + " values (?, ?, ?, ?)";
            PreparedStatement preparedStmt = con.prepareStatement(query);
            preparedStmt.setString(1, name);
            preparedStmt.setInt(2, hp);
            preparedStmt.setString(3, type);
            preparedStmt.setString(4, role);

            // execute the java preparedstatement
            preparedStmt.executeUpdate();

            con.close();
            System.out.println("Data ajouté");
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

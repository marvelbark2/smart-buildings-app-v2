package edu.episen.si.ing1.pds.client.swing.cards;

import edu.episen.si.ing1.pds.client.swing.cards.models.DataTable;
import edu.episen.si.ing1.pds.client.swing.global.Navigate;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public enum ComponentRegister {
    Instance;

    private Map<String, JButton> buttons;
    private Map<String, JTable> tables;
    private Map<String, JLabel> labels;
    private Map<String, JComboBox> comboboxes;
    private Map<String, DataTable> dataTables;
    private Map<String, Navigate> containers;

    ComponentRegister() {
        buttons = new HashMap<>();
        tables = new HashMap<>();
        labels = new HashMap<>();
        comboboxes = new HashMap<>();
        dataTables = new HashMap<>();
        containers = new HashMap<>();
    }

    public void registerButton(String name, JButton btn) {
        buttons.put(name, btn);
    }

    public void registerTable(String name, JTable tbl) {
        tables.put(name, tbl);
    }

    public void registerLabel(String name, JLabel lbl) {
        labels.put(name, lbl);
    }

    public void registerComboBox(String name, JComboBox cb) {
        comboboxes.put(name, cb);
    }

    public void registerDataTable(String name , DataTable dt) {
        dataTables.put(name, dt);
    }

    public void registerContainer(String name, Navigate container) {
        containers.put(name, container);
    }

    public JButton getButton(String comp) {
        return buttons.get(comp);
    }

    public JTable getTable(String comp) {
        return tables.get(comp);
    }

    public JLabel getLabel(String comp) {
        return labels.get(comp);
    }

    public JComboBox getCombobox(String comp) { return comboboxes.get(comp); }

    public Navigate getContainer(String comp) {
        return containers.get(comp);
    }

    public DataTable getDataTable(String comp) {
        return dataTables.get(comp);
    }

    public Map<String, JButton> getButtons() {
        return buttons;
    }

    public Map<String, JTable> getTables() {
        return tables;
    }

    public Map<String, JLabel> getLabels() {
        return labels;
    }

    public Map<String, JComboBox> getComboboxes() {
        return comboboxes;
    }

    public Map<String, Navigate> getContainers() {
        return containers;
    }
}

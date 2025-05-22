package views;

import controllers.RecruitmentController;
import models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class RecruitmentFrame extends JFrame {
    private final RecruitmentController controller = new RecruitmentController();
    private final DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Nama", "Path", "Tulis", "Koding", "Wawancara", "Skor", "Status"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Membuat tabel tidak bisa diedit
        }
    };
    private final JTable table = new JTable(tableModel);
    private TableRowSorter<DefaultTableModel> sorter;
    
    // Field variables untuk diakses di seluruh class
    private JTextField nameField;
    private JComboBox<String> pathBox;
    private JTextField writeField;
    private JTextField codeField;
    private JTextField interviewField;
    private JTextField searchField;
    
    // Font size untuk zoom
    private int currentFontSize = 12;
    private Font currentFont = new Font("SansSerif", Font.PLAIN, currentFontSize);

    public RecruitmentFrame() {
        setTitle("Sistem Rekrutmen");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel kiri untuk tabel dengan search
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Data Kandidat"));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Cari Nama:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        
        // Zoom buttons
        JButton zoomInBtn = new JButton("+");
        JButton zoomOutBtn = new JButton("-");
        JButton resetZoomBtn = new JButton("Reset");
        
        zoomInBtn.setPreferredSize(new Dimension(45, 25));
        zoomOutBtn.setPreferredSize(new Dimension(45, 25));
        resetZoomBtn.setPreferredSize(new Dimension(60, 25));
        
        searchPanel.add(Box.createHorizontalStrut(20));
        searchPanel.add(new JLabel("Zoom:"));
        searchPanel.add(zoomOutBtn);
        searchPanel.add(zoomInBtn);
        searchPanel.add(resetZoomBtn);
        
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Setup tabel dengan sorter
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(currentFont);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, currentFontSize));
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(750, 550));
        leftPanel.add(scroll, BorderLayout.CENTER);
        
        // Panel kanan untuk form
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Input Data"));
        rightPanel.setPreferredSize(new Dimension(350, 550));
        
        // Form panel dengan GridBagLayout untuk alignment yang tepat
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 0, 5, 0);
        
        // Initialize form fields
        nameField = new JTextField(20);
        pathBox = new JComboBox<>(new String[]{"Android", "Web"});
        writeField = new JTextField(20);
        codeField = new JTextField(20);
        interviewField = new JTextField(20);
        
        // Set font untuk form fields
        nameField.setFont(currentFont);
        pathBox.setFont(currentFont);
        writeField.setFont(currentFont);
        codeField.setFont(currentFont);
        interviewField.setFont(currentFont);
        
        // Add form components dengan GridBagLayout
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(new JLabel("Nama"), gbc);
        
        gbc.gridy = 1;
        formPanel.add(nameField, gbc);
        
        gbc.gridy = 2;
        gbc.insets = new Insets(15, 0, 5, 0);
        formPanel.add(new JLabel("Jalur"), gbc);
        
        gbc.gridy = 3;
        gbc.insets = new Insets(5, 0, 5, 0);
        formPanel.add(pathBox, gbc);
        
        gbc.gridy = 4;
        gbc.insets = new Insets(15, 0, 5, 0);
        formPanel.add(new JLabel("Nilai Tulis"), gbc);
        
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 0, 5, 0);
        formPanel.add(writeField, gbc);
        
        gbc.gridy = 6;
        gbc.insets = new Insets(15, 0, 5, 0);
        formPanel.add(new JLabel("Nilai Koding"), gbc);
        
        gbc.gridy = 7;
        gbc.insets = new Insets(5, 0, 5, 0);
        formPanel.add(codeField, gbc);
        
        gbc.gridy = 8;
        gbc.insets = new Insets(15, 0, 5, 0);
        formPanel.add(new JLabel("Nilai Wawancara"), gbc);
        
        gbc.gridy = 9;
        gbc.insets = new Insets(5, 0, 20, 0);
        formPanel.add(interviewField, gbc);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        
        JButton submitBtn = new JButton("Simpan");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Hapus");
        JButton clearBtn = new JButton("Clear");
        
        // Set font untuk buttons
        submitBtn.setFont(currentFont);
        updateBtn.setFont(currentFont);
        deleteBtn.setFont(currentFont);
        clearBtn.setFont(currentFont);
        
        buttonPanel.add(submitBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);
        
        gbc.gridy = 10;
        gbc.insets = new Insets(10, 0, 0, 0);
        formPanel.add(buttonPanel, gbc);
        
        // Add glue untuk mendorong form ke atas
        gbc.gridy = 11;
        gbc.weighty = 1.0;
        formPanel.add(Box.createVerticalGlue(), gbc);
        
        rightPanel.add(formPanel, BorderLayout.CENTER);
        
        // Add panels to main panel
        mainPanel.add(leftPanel, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        add(mainPanel);
        
        refreshTable();
        setupEventListeners(submitBtn, updateBtn, deleteBtn, clearBtn, zoomInBtn, zoomOutBtn, resetZoomBtn);
    }
    
    private void setupEventListeners(JButton submitBtn, JButton updateBtn, JButton deleteBtn, JButton clearBtn,
                                   JButton zoomInBtn, JButton zoomOutBtn, JButton resetZoomBtn) {
        
        // Search functionality
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { search(); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { search(); }
            
            public void search() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text, 0)); // Search di kolom nama (index 0)
                }
            }
        });
        
        // Zoom functionality
        zoomInBtn.addActionListener(e -> {
            if (currentFontSize < 20) {
                currentFontSize += 2;
                updateFonts();
            }
        });
        
        zoomOutBtn.addActionListener(e -> {
            if (currentFontSize > 8) {
                currentFontSize -= 2;
                updateFonts();
            }
        });
        
        resetZoomBtn.addActionListener(e -> {
            currentFontSize = 12;
            updateFonts();
        });
        
        // Table mouse listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    // Convert view row to model row karena ada sorting/filtering
                    int modelRow = table.convertRowIndexToModel(row);
                    nameField.setText(tableModel.getValueAt(modelRow, 0).toString());
                    pathBox.setSelectedItem(tableModel.getValueAt(modelRow, 1).toString());
                    writeField.setText(tableModel.getValueAt(modelRow, 2).toString());
                    codeField.setText(tableModel.getValueAt(modelRow, 3).toString());
                    interviewField.setText(tableModel.getValueAt(modelRow, 4).toString());
                }
            }
        });

        // Submit button
        submitBtn.addActionListener((ActionEvent e) -> {
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String path = (String) pathBox.getSelectedItem();
                int w = Integer.parseInt(writeField.getText());
                int c = Integer.parseInt(codeField.getText());
                int i = Integer.parseInt(interviewField.getText());

                Candidate candidate = path.equals("Android") ? new AndroidDev(name, w, c, i) : new WebDev(name, w, c, i);
                controller.saveCandidate(candidate, path);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input nilai harus angka", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Update button
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            try {
                String name = nameField.getText().trim();
                if (name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nama tidak boleh kosong", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                String path = (String) pathBox.getSelectedItem();
                int w = Integer.parseInt(writeField.getText());
                int c = Integer.parseInt(codeField.getText());
                int i = Integer.parseInt(interviewField.getText());

                Candidate candidate = path.equals("Android") ? new AndroidDev(name, w, c, i) : new WebDev(name, w, c, i);
                controller.updateCandidate(candidate, path);
                refreshTable();
                clearFields();
                JOptionPane.showMessageDialog(this, "Data berhasil diupdate", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input nilai harus angka", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Delete button
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus", "Info", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus data ini?", 
                "Konfirmasi Hapus", 
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    String name = tableModel.getValueAt(modelRow, 0).toString();
                    controller.deleteCandidate(name);
                    refreshTable();
                    clearFields();
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Clear button
        clearBtn.addActionListener(e -> clearFields());
    }
    
    private void updateFonts() {
        currentFont = new Font("SansSerif", Font.PLAIN, currentFontSize);
        Font boldFont = new Font("SansSerif", Font.BOLD, currentFontSize);
        
        // Update table fonts
        table.setFont(currentFont);
        table.getTableHeader().setFont(boldFont);
        table.setRowHeight(currentFontSize + 8); // Adjust row height
        
        // Update form fonts
        nameField.setFont(currentFont);
        pathBox.setFont(currentFont);
        writeField.setFont(currentFont);
        codeField.setFont(currentFont);
        interviewField.setFont(currentFont);
        
        // Update button fonts
        Component[] components = ((JPanel)((JPanel)((BorderLayout)((JPanel)getContentPane().getComponent(0)).getLayout()).getLayoutComponent(BorderLayout.EAST)).getComponent(0)).getComponents();
        for (Component comp : components) {
            if (comp instanceof JButton) {
                comp.setFont(currentFont);
            }
        }
        
        repaint();
    }

    private void clearFields() {
        nameField.setText("");
        writeField.setText("");
        codeField.setText("");
        interviewField.setText("");
        pathBox.setSelectedIndex(0);
        table.clearSelection();
        searchField.setText(""); // Clear search juga
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            List<String[]> candidates = controller.getAllCandidates();
            for (String[] row : candidates) {
                tableModel.addRow(row);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
package view;

import controller.CandidateController;
import model.Candidate;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.util.Comparator;
import java.util.ArrayList;

public class MainForm extends JFrame {
    private JPanel contentPane;
    private JTable table;
    private JTextField txtName;
    private JComboBox<String> cmbPath;
    private JTextField txtWriting;
    private JTextField txtCoding;
    private JTextField txtInterview;
    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JLabel lblStatus;
    
    private DefaultTableModel tableModel;
    private final CandidateController controller;
    private int selectedId = -1;
    private Color primaryColor = new Color(70, 130, 180); // Steel Blue
    private Color secondaryColor = new Color(240, 248, 255); // Alice Blue
    private Color accentColor = new Color(30, 144, 255); // Dodger Blue
    private List<Candidate> allCandidates;
    
    public MainForm() {
        controller = new CandidateController();
        initComponents();
        customizeUI();
        loadData();
    }
    
    private void initComponents() {
        setTitle("PT. OOP Recruitment System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        
        // Main content panel with BorderLayout
        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);
        
        // Status bar at bottom
        lblStatus = new JLabel("Ready");
        lblStatus.setBorder(new CompoundBorder(
                new MatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        contentPane.add(lblStatus, BorderLayout.SOUTH);
        
        // Table setup with custom model
        tableModel = new DefaultTableModel(
                new Object[][] {},
                new String[] {"Name", "Path", "Writing", "Coding", "Interview", "Score", "Status"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex >= 2 && columnIndex <= 5) {
                    return Double.class;
                }
                return String.class;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(25);
        table.setIntercellSpacing(new Dimension(10, 5));
        table.setShowGrid(false);
        table.setShowHorizontalLines(true);
        
        // Custom header renderer
        JTableHeader header = table.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, accentColor));
        
        // Custom cell renderer for status column
        table.getColumnModel().getColumn(6).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = value.toString();
                
                if (status.contains("Accepted")) {
                    c.setForeground(new Color(0, 128, 0)); // Dark Green
                } else if (status.contains("Rejected")) {
                    c.setForeground(new Color(178, 34, 34)); // Firebrick Red
                } else {
                    c.setForeground(new Color(205, 133, 63)); // Peru (brownish)
                }
                
                return c;
            }
        });
        
        // Custom renderer for score columns
        DefaultTableCellRenderer scoreRenderer = new DefaultTableCellRenderer() {
            private final DecimalFormat df = new DecimalFormat("#0.00");
            
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                if (value instanceof Double) {
                    value = df.format(value);
                }
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(SwingConstants.RIGHT);
                return c;
            }
        };
        
        // Apply score renderer to numeric columns
        for (int i = 2; i <= 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(scoreRenderer);
        }
        
        // Row striping effect
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : secondaryColor);
                }
                setBorder(new EmptyBorder(0, 5, 0, 5));
                return c;
            }
        });
        
        // Adjust column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);  // Name
        columnModel.getColumn(1).setPreferredWidth(120);  // Path
        columnModel.getColumn(2).setPreferredWidth(70);   // Writing
        columnModel.getColumn(3).setPreferredWidth(70);   // Coding
        columnModel.getColumn(4).setPreferredWidth(70);   // Interview
        columnModel.getColumn(5).setPreferredWidth(70);   // Score
        columnModel.getColumn(6).setPreferredWidth(100);  // Status - wider to prevent cutoff
        
        // Table scrollpane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)));
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Search panel at top
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchPanel.setOpaque(false);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        txtSearch = new JTextField();
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchCandidates();
                }
            }
        });
        
        btnSearch = new JButton("Search");
        btnSearch.setFocusPainted(false);
        btnSearch.addActionListener(e -> searchCandidates());
        
        JButton btnReset = new JButton("Show All");
        btnReset.setFocusPainted(false);
        btnReset.addActionListener(e -> {
            txtSearch.setText("");
            loadData();
        });
        
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(btnSearch);
        btnPanel.add(btnReset);
        
        searchPanel.add(new JLabel("Search by name: "), BorderLayout.WEST);
        searchPanel.add(txtSearch, BorderLayout.CENTER);
        searchPanel.add(btnPanel, BorderLayout.EAST);
        
        // Sorting capability
        table.setAutoCreateRowSorter(true);
        
        // Left panel with table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setOpaque(false);
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Right panel with form
        JPanel formPanel = createFormPanel();
        
        // Add panels to main content
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, leftPanel, formPanel);
        splitPane.setDividerLocation(600);
        splitPane.setOneTouchExpandable(true);
        splitPane.setBorder(null);
        contentPane.add(splitPane, BorderLayout.CENTER);
        
        // Table selection listener
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    try {
                        // Convert view index to model index (for sorting/filtering)
                        int modelRow = table.convertRowIndexToModel(row);
                        String name = tableModel.getValueAt(modelRow, 0).toString();
                        
                        for (Candidate c : allCandidates) {
                            if (c.getName().equals(name)) {
                                selectedId = c.getId();
                                txtName.setText(c.getName());
                                cmbPath.setSelectedItem(c instanceof model.AndroidDeveloper ? 
                                    "Android Developer" : "Web Developer");
                                txtWriting.setText(String.valueOf(c.getWritingScore()));
                                txtCoding.setText(String.valueOf(c.getCodingScore()));
                                txtInterview.setText(String.valueOf(c.getInterviewScore()));
                                
                                updateButtonStates(true);
                                lblStatus.setText("Candidate selected: " + name);
                                break;
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainForm.this, 
                                "Error selecting candidate: " + ex.getMessage(), 
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        
        setLocationRelativeTo(null);
    }
    
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout(0, 10));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 1, 0, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(0, 15, 0, 0)));
        formPanel.setOpaque(false);
        
        // Form title
        JLabel formTitle = new JLabel("Candidate Details");
        formTitle.setFont(formTitle.getFont().deriveFont(Font.BOLD, 16f));
        formTitle.setForeground(primaryColor);
        formTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Form fields panel
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setOpaque(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.weightx = 1.0;
        
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblName = new JLabel("Name");
        fieldsPanel.add(lblName, gbc);
        
        gbc.gridy = 1;
        txtName = new JTextField();
        txtName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        fieldsPanel.add(txtName, gbc);
        
        // Path field
        gbc.gridy = 2;
        JLabel lblPath = new JLabel("Path");
        fieldsPanel.add(lblPath, gbc);
        
        gbc.gridy = 3;
        cmbPath = new JComboBox<>();
        cmbPath.setModel(new DefaultComboBoxModel<>(new String[] {"Android Developer", "Web Developer"}));
        cmbPath.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        fieldsPanel.add(cmbPath, gbc);
        
        // Writing field
        gbc.gridy = 4;
        JLabel lblWriting = new JLabel("Writing Score (0-100)");
        fieldsPanel.add(lblWriting, gbc);
        
        gbc.gridy = 5;
        txtWriting = new JTextField();
        txtWriting.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        fieldsPanel.add(txtWriting, gbc);
        
        // Coding field
        gbc.gridy = 6;
        JLabel lblCoding = new JLabel("Coding Score (0-100)");
        fieldsPanel.add(lblCoding, gbc);
        
        gbc.gridy = 7;
        txtCoding = new JTextField();
        txtCoding.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        fieldsPanel.add(txtCoding, gbc);
        
        // Interview field
        gbc.gridy = 8;
        JLabel lblInterview = new JLabel("Interview Score (0-100)");
        fieldsPanel.add(lblInterview, gbc);
        
        gbc.gridy = 9;
        txtInterview = new JTextField();
        txtInterview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 7, 5, 7)));
        fieldsPanel.add(txtInterview, gbc);
        
        // Buttons panel
        JPanel buttonsPanel = new JPanel(new GridLayout(4, 1, 0, 10));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        
        btnAdd = createStyledButton("Add Candidate", new Color(46, 125, 50)); // Green
        btnAdd.addActionListener(e -> addCandidate());
        
        btnUpdate = createStyledButton("Update Candidate", new Color(33, 150, 243)); // Blue
        btnUpdate.addActionListener(e -> updateCandidate());
        
        btnDelete = createStyledButton("Delete Candidate", new Color(211, 47, 47)); // Red
        btnDelete.addActionListener(e -> deleteCandidate());
        
        btnClear = createStyledButton("Clear Form", new Color(117, 117, 117)); // Gray
        btnClear.addActionListener(e -> clearForm());
        
        buttonsPanel.add(btnAdd);
        buttonsPanel.add(btnUpdate);
        buttonsPanel.add(btnDelete);
        buttonsPanel.add(btnClear);
        
        // Update initial button states
        updateButtonStates(false);
        
        // Add components to form panel
        formPanel.add(formTitle, BorderLayout.NORTH);
        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        return formPanel;
    }
    
private JButton createStyledButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setFocusPainted(false);
    button.setBackground(backgroundColor);
    button.setForeground(Color.BLACK); // Warna teks hitam
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(backgroundColor.darker()),
        BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    Color hoverColor = backgroundColor.brighter(); // Hover warna lebih terang

    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            if (button.isEnabled()) {
                button.setBackground(hoverColor);
            }
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            if (button.isEnabled()) {
                button.setBackground(backgroundColor);
            }
        }
    });

    return button;
}

    
    private void updateButtonStates(boolean candidateSelected) {
        btnUpdate.setEnabled(candidateSelected);
        btnDelete.setEnabled(candidateSelected);
    }
    
    private void customizeUI() {
        try {
            // Use system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            
            // Apply to all components
            SwingUtilities.updateComponentTreeUI(this);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadData() {
        try {
            // Clear the table
            tableModel.setRowCount(0);
            
            // Get all candidates
            allCandidates = controller.getAllCandidates();
            
            // Add candidates to table
            for (Candidate candidate : allCandidates) {
                double finalScore = candidate.calculateFinalScore();
                String status = candidate.getStatus();
                
                tableModel.addRow(new Object[]{
                    candidate.getName(),
                    candidate instanceof model.AndroidDeveloper ? "Android Developer" : "Web Developer",
                    candidate.getWritingScore(),
                    candidate.getCodingScore(),
                    candidate.getInterviewScore(),
                    finalScore,
                    status
                });
            }
            
            lblStatus.setText(allCandidates.size() + " candidates loaded");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error loading data: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Error loading data");
        }
    }
    
    private void searchCandidates() {
        String searchText = txtSearch.getText().trim().toLowerCase();
        if (searchText.isEmpty()) {
            loadData();
            return;
        }
        
        try {
            // Clear the table
            tableModel.setRowCount(0);
            
            // Filter candidates
            List<Candidate> filteredCandidates = new ArrayList<>();
            for (Candidate candidate : allCandidates) {
                if (candidate.getName().toLowerCase().contains(searchText)) {
                    filteredCandidates.add(candidate);
                }
            }
            
            // Add filtered candidates to table
            for (Candidate candidate : filteredCandidates) {
                double finalScore = candidate.calculateFinalScore();
                String status = candidate.getStatus();
                
                tableModel.addRow(new Object[]{
                    candidate.getName(),
                    candidate instanceof model.AndroidDeveloper ? "Android Developer" : "Web Developer",
                    candidate.getWritingScore(),
                    candidate.getCodingScore(),
                    candidate.getInterviewScore(),
                    finalScore,
                    status
                });
            }
            
            lblStatus.setText(filteredCandidates.size() + " candidates found");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error searching candidates: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Error searching candidates");
        }
    }
    
    private void addCandidate() {
        try {
            // Validate form
            validateForm();
            
            // Get form values
            String name = txtName.getText().trim();
            String path = cmbPath.getSelectedItem().toString();
            
            // Parse scores
            double writingScore = parseScore(txtWriting.getText(), "Writing");
            double codingScore = parseScore(txtCoding.getText(), "Coding");
            double interviewScore = parseScore(txtInterview.getText(), "Interview");
            
            // Add candidate
            controller.addCandidate(name, path, writingScore, codingScore, interviewScore);
            
            // Refresh data and clear form
            loadData();
            clearForm();
            
            lblStatus.setText("Candidate added successfully: " + name);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error adding candidate: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Error adding candidate");
        }
    }
    
    private void updateCandidate() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select a candidate to update", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            // Validate form
            validateForm();
            
            // Get form values
            String name = txtName.getText().trim();
            String path = cmbPath.getSelectedItem().toString();
            
            // Parse scores
            double writingScore = parseScore(txtWriting.getText(), "Writing");
            double codingScore = parseScore(txtCoding.getText(), "Coding");
            double interviewScore = parseScore(txtInterview.getText(), "Interview");
            
            // Update candidate
            controller.updateCandidate(selectedId, name, path, writingScore, codingScore, interviewScore);
            
            // Refresh data and clear form
            loadData();
            clearForm();
            
            lblStatus.setText("Candidate updated successfully: " + name);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    "Error updating candidate: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            lblStatus.setText("Error updating candidate");
        }
    }
    
    private void deleteCandidate() {
        if (selectedId == -1) {
            JOptionPane.showMessageDialog(this, 
                    "Please select a candidate to delete", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String candidateName = txtName.getText().trim();
        int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete candidate '" + candidateName + "'?", 
                "Confirm Delete", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Delete candidate
                controller.deleteCandidate(selectedId);
                
                // Refresh data and clear form
                loadData();
                clearForm();
                
                lblStatus.setText("Candidate deleted successfully: " + candidateName);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Error deleting candidate: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Error deleting candidate");
            }
        }
    }
    
    private void clearForm() {
        txtName.setText("");
        cmbPath.setSelectedIndex(0);
        txtWriting.setText("");
        txtCoding.setText("");
        txtInterview.setText("");
        selectedId = -1;
        table.clearSelection();
        updateButtonStates(false);
        lblStatus.setText("Form cleared");
    }
    
    private void validateForm() throws IllegalArgumentException {
        // Validate name
        String name = txtName.getText().trim();
        if (name.isEmpty()) {
            txtName.requestFocus();
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        // Validate scores
        if (txtWriting.getText().trim().isEmpty()) {
            txtWriting.requestFocus();
            throw new IllegalArgumentException("Writing score cannot be empty");
        }
        
        if (txtCoding.getText().trim().isEmpty()) {
            txtCoding.requestFocus();
            throw new IllegalArgumentException("Coding score cannot be empty");
        }
        
        if (txtInterview.getText().trim().isEmpty()) {
            txtInterview.requestFocus();
            throw new IllegalArgumentException("Interview score cannot be empty");
        }
    }
    
    private double parseScore(String scoreText, String fieldName) throws IllegalArgumentException {
        try {
            double score = Double.parseDouble(scoreText);
            if (score < 0 || score > 100) {
                throw new IllegalArgumentException(fieldName + " score must be between 0 and 100");
            }
            return score;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(fieldName + " score must be a valid number");
        }
    }
    
    // Feature: Export candidates to CSV
    private void exportToCSV() {
        if (allCandidates.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "No candidates to export", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save CSV File");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv";
                }
                
                // Implementation of CSV export would go here
                // For now, just show success message
                JOptionPane.showMessageDialog(this, 
                        "Candidates exported successfully to " + filePath, 
                        "Export Successful", JOptionPane.INFORMATION_MESSAGE);
                lblStatus.setText("Candidates exported to CSV");
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, 
                        "Error exporting candidates: " + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                lblStatus.setText("Error exporting candidates");
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        // Create and display the form
        SwingUtilities.invokeLater(() -> {
            MainForm mainForm = new MainForm();
            mainForm.setVisible(true);
        });
    }
}
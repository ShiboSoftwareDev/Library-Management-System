import javax.swing.*;
import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class LibraryGUI extends JFrame {
    private Library library;
    private JTabbedPane tabbedPane;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, quantityField;
    private JComboBox<String> itemTypeCombo;

    public LibraryGUI() {
        library = new Library();
        setupGUI();
    }

    private void setupGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        tabbedPane = new JTabbedPane();

        // Add Items Panel
        tabbedPane.addTab("Add Items", createAddItemsPanel());

        // View Items Panel
        tabbedPane.addTab("View Items", createViewItemsPanel());

        // Borrow/Return Panel
        tabbedPane.addTab("Borrow/Return", createBorrowReturnPanel());

        add(tabbedPane);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                library.saveToFile();
                dispose();
            }
        });

        refreshTable();
    }

    private JPanel createAddItemsPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Radio buttons for item type selection
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup itemGroup = new ButtonGroup();
        JRadioButton bookRadio = new JRadioButton("Book", true);
        JRadioButton cdRadio = new JRadioButton("CD");
        itemGroup.add(bookRadio);
        itemGroup.add(cdRadio);
        radioPanel.add(bookRadio);
        radioPanel.add(cdRadio);

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        titleField = new JTextField(20);
        JTextField field2 = new JTextField(20);  // category/company
        JTextField field3 = new JTextField(20);  // author/duration
        quantityField = new JTextField(20);

        JLabel label2 = new JLabel("Category:");
        JLabel label3 = new JLabel("Author:");

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(label2);
        inputPanel.add(field2);
        inputPanel.add(label3);
        inputPanel.add(field3);
        inputPanel.add(new JLabel("Quantity:"));
        inputPanel.add(quantityField);

        JButton addButton = new JButton("Add Item");

        // Update labels when radio selection changes
        bookRadio.addActionListener(e -> {
            label2.setText("Category:");
            label3.setText("Author:");
        });

        cdRadio.addActionListener(e -> {
            label2.setText("Company:");
            label3.setText("Duration (mins):");
        });

        addButton.addActionListener(e -> {
            try {
                if (bookRadio.isSelected()) {
                    Book book = new Book(
                        titleField.getText(),
                        field2.getText(),
                        field3.getText(),
                        Integer.parseInt(quantityField.getText())
                    );
                    library.addItem(book);
                    refreshTable();
                } else {
                    CD cd = new CD(
                        titleField.getText(),
                        field2.getText(),
                        Integer.parseInt(field3.getText()),
                        Integer.parseInt(quantityField.getText())
                    );
                    library.addItem(cd);
                    refreshTable();
                }
                titleField.setText("");
                field2.setText("");
                field3.setText("");
                quantityField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(mainPanel, 
                    "Invalid number format!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        inputPanel.add(new JLabel(""));
        inputPanel.add(addButton);

        mainPanel.add(radioPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createViewItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Type", "Title", "Details", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        itemsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(itemsTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton deleteAllButton = new JButton("Delete All Items");
        deleteAllButton.setMargin(new Insets(5, 10, 5, 10));
        buttonPanel.add(deleteAllButton, BorderLayout.WEST);

        JPanel deleteItemPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JTextField deleteTitleField = new JTextField(10);
        JTextField deleteIdentifierField = new JTextField(10);
        JButton deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> {
            String title = deleteTitleField.getText();
            String identifier = deleteIdentifierField.getText();
            library.removeItem(title, identifier);
            refreshTable();
            deleteTitleField.setText("");
            deleteIdentifierField.setText("");
        });
        deleteItemPanel.add(new JLabel("Title:"));
        deleteItemPanel.add(deleteTitleField);
        deleteItemPanel.add(new JLabel("Author/Company:"));
        deleteItemPanel.add(deleteIdentifierField);
        deleteItemPanel.add(deleteItemButton);

        JPanel buttonPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel2.add(deleteItemPanel, BorderLayout.EAST);

        JPanel panel2 = new JPanel(new BorderLayout(10, 10));
        panel.add(panel2, BorderLayout.SOUTH);
        panel2.add(buttonPanel, BorderLayout.WEST);
        panel2.add(buttonPanel2, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBorrowReturnPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Radio buttons for action selection
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ButtonGroup actionGroup = new ButtonGroup();
        JRadioButton borrowRadio = new JRadioButton("Borrow", true);
        JRadioButton returnRadio = new JRadioButton("Return");
        actionGroup.add(borrowRadio);
        actionGroup.add(returnRadio);
        radioPanel.add(borrowRadio);
        radioPanel.add(returnRadio);

        // Input fields panel
        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Item Details"));

        JTextField titleField = new JTextField(20);
        JTextField identifierField = new JTextField(20);
        JButton actionButton = new JButton("Submit");

        actionButton.addActionListener(e -> {
            if (borrowRadio.isSelected()) {
                library.borrowItem(titleField.getText(), identifierField.getText());
            } else {
                library.returnItem(titleField.getText(), identifierField.getText());
            }
            refreshTable();
            titleField.setText("");
            identifierField.setText("");
        });

        inputPanel.add(new JLabel("Title:"));
        inputPanel.add(titleField);
        inputPanel.add(new JLabel("Author/Company:"));
        inputPanel.add(identifierField);
        inputPanel.add(new JLabel(""));
        inputPanel.add(actionButton);

        mainPanel.add(radioPanel, BorderLayout.NORTH);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        return mainPanel;
    }


    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Item item : library.getAllItems()) {
            if (item instanceof Book) {
                Book book = (Book) item;
                tableModel.addRow(new Object[] {
                        "Book",
                        book.getTitle(),
                        "Author: " + book.getAuthor() + ", Category: " + book.getCategory(),
                        "Available: " + book.getQuantity() + "/" + book.getCapacity()
                });
            } else if (item instanceof CD) {
                CD cd = (CD) item;
                tableModel.addRow(new Object[] {
                        "CD",
                        cd.getTitle(),
                        "Company: " + cd.getCompany() + ", Duration: " + cd.getDuration() + " mins",
                        "Available: " + cd.getQuantity() + "/" + cd.getCapacity()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}

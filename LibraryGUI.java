import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.table.DefaultTableModel;

public class LibraryGUI extends JFrame {
    private Library library;
    private JTabbedPane tabbedPane;
    private JTable itemsTable;
    private DefaultTableModel tableModel;
    private JTextField titleField, categoryField, authorField, quantityField, priceField;
    private JTextField cdTitleField, companyField, durationField, cdPriceField;
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
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Book Panel
        JPanel bookPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        bookPanel.setBorder(BorderFactory.createTitledBorder("Add Book"));

        titleField = new JTextField(20);
        categoryField = new JTextField(20);
        authorField = new JTextField(20);
        quantityField = new JTextField(20);
        priceField = new JTextField(20);

        bookPanel.add(new JLabel("Title:"));
        bookPanel.add(titleField);
        bookPanel.add(new JLabel("Category:"));
        bookPanel.add(categoryField);
        bookPanel.add(new JLabel("Author:"));
        bookPanel.add(authorField);
        bookPanel.add(new JLabel("Quantity:"));
        bookPanel.add(quantityField);
        bookPanel.add(new JLabel("Price:"));
        bookPanel.add(priceField);

        JButton addBookButton = new JButton("Add Book");
        addBookButton.addActionListener(e -> addBook());
        bookPanel.add(addBookButton);

        // CD Panel
        JPanel cdPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        cdPanel.setBorder(BorderFactory.createTitledBorder("Add CD"));

        cdTitleField = new JTextField(20);
        companyField = new JTextField(20);
        durationField = new JTextField(20);
        cdPriceField = new JTextField(20);

        cdPanel.add(new JLabel("Title:"));
        cdPanel.add(cdTitleField);
        cdPanel.add(new JLabel("Company:"));
        cdPanel.add(companyField);
        cdPanel.add(new JLabel("Duration (mins):"));
        cdPanel.add(durationField);
        cdPanel.add(new JLabel("Price:"));
        cdPanel.add(cdPriceField);

        JButton addCdButton = new JButton("Add CD");
        addCdButton.addActionListener(e -> addCD());
        cdPanel.add(addCdButton);

        panel.add(bookPanel);
        panel.add(cdPanel);

        return panel;
    }

    private JPanel createViewItemsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] columns = { "Type", "Title", "Details", "Price", "Status" };
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
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField borrowTitleField = new JTextField(20);
        JButton borrowButton = new JButton("Borrow Item");
        borrowButton.addActionListener(e -> {
            library.borrowItem(borrowTitleField.getText());
            refreshTable();
            borrowTitleField.setText("");
        });

        JTextField returnTitleField = new JTextField(20);
        JButton returnButton = new JButton("Return Item");
        returnButton.addActionListener(e -> {
            library.returnItem(returnTitleField.getText());
            refreshTable();
            returnTitleField.setText("");
        });

        panel.add(new JLabel("Title to Borrow:"));
        panel.add(borrowTitleField);
        panel.add(borrowButton);
        panel.add(new JLabel("Title to Return:"));
        panel.add(returnTitleField);
        panel.add(returnButton);

        return panel;
    }

    private void addBook() {
        try {
            String title = titleField.getText();
            String category = categoryField.getText();
            String author = authorField.getText();
            int quantity = Integer.parseInt(quantityField.getText());
            double price = Double.parseDouble(priceField.getText());

            if (quantity <= 0) {
                JOptionPane.showMessageDialog(this, "Quantity must be greater than 0!");
                return;
            }

            Book book = new Book(title, category, author, quantity, price);
            library.addItem(book);
            JOptionPane.showMessageDialog(this, "Book added successfully.");
            clearBookFields();
            refreshTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        }
    }

    private void addCD() {
        try {
            String title = cdTitleField.getText();
            String company = companyField.getText();
            int duration = Integer.parseInt(durationField.getText());
            double price = Double.parseDouble(cdPriceField.getText());

            CD cd = new CD(title, company, duration, price);
            library.addItem(cd);
            JOptionPane.showMessageDialog(this, "CD added successfully.");
            clearCDFields();
            refreshTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number format!");
        }
    }

    private void clearBookFields() {
        titleField.setText("");
        categoryField.setText("");
        authorField.setText("");
        quantityField.setText("");
        priceField.setText("");
    }

    private void clearCDFields() {
        cdTitleField.setText("");
        companyField.setText("");
        durationField.setText("");
        cdPriceField.setText("");
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
                        String.format("$%.2f", book.getPrice()),
                        "Available: " + book.getQuantity()
                });
            } else if (item instanceof CD) {
                CD cd = (CD) item;
                tableModel.addRow(new Object[] {
                        "CD",
                        cd.getTitle(),
                        "Company: " + cd.getCompany() + ", Duration: " + cd.getDuration() + " mins",
                        String.format("$%.2f", cd.getPrice()),
                        "Available"
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

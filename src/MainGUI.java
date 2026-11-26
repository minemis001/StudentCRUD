import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainGUI extends JFrame {
    private JTextField txtNim = new JTextField(12);
    private JTextField txtName = new JTextField(15);
    private JButton btnSave = new JButton("Save");
    private JButton btnUpdate = new JButton("Update");
    private JButton btnDelete = new JButton("Delete");
    private JLabel lblStatus = new JLabel(" ");
    private DefaultTableModel model =
            new DefaultTableModel(new String[]{"ID", "NIM", "Name"}, 0);
    private JTable table = new JTable(model);
    private StudentDAO dao = new StudentDAO();

    public MainGUI() {
        setTitle("Student CRUD");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        JPanel form = new JPanel();
        form.add(new JLabel("NIM:"));  form.add(txtNim);
        form.add(new JLabel("Name:")); form.add(txtName);
        form.add(btnSave); form.add(btnUpdate); form.add(btnDelete);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                txtNim.setText(model.getValueAt(r, 1).toString());
                txtName.setText(model.getValueAt(r, 2).toString());
            }
        });

        setSize(650, 400);
        setLocationRelativeTo(null);
        loadData();
    }

    private void setBusy(boolean busy, String msg) {
        btnSave.setEnabled(!busy);
        btnUpdate.setEnabled(!busy);
        btnDelete.setEnabled(!busy);
        lblStatus.setText(msg);
    }

    private void saveStudent() {
        String nim = txtNim.getText().trim();
        String name = txtName.getText().trim();
        if (nim.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM dan Name wajib diisi");
            return;
        }
        setBusy(true, "Saving...");              // disable button + label
        new Thread(() -> {                       // DB di thread lain
            try {
                Thread.sleep(1500);
                dao.insert(nim, name);
                List<Student> data = dao.findAll();
                SwingUtilities.invokeLater(() -> {   // kembali ke UI
                    refreshTable(data);
                    txtNim.setText(""); txtName.setText("");
                    setBusy(false, "Saved!");
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    setBusy(false, "Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void updateStudent() {
        int r = table.getSelectedRow();
        if (r < 0) { lblStatus.setText("Pilih baris dulu"); return; }
        int id = (int) model.getValueAt(r, 0);
        String nim = txtNim.getText().trim();
        String name = txtName.getText().trim();
        setBusy(true, "Updating...");
        new Thread(() -> {
            try {
                dao.update(id, nim, name);
                List<Student> data = dao.findAll();
                SwingUtilities.invokeLater(() -> {
                    refreshTable(data);
                    setBusy(false, "Updated!");
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    setBusy(false, "Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void deleteStudent() {
        int r = table.getSelectedRow();
        if (r < 0) { lblStatus.setText("Pilih baris dulu"); return; }
        int id = (int) model.getValueAt(r, 0);
        setBusy(true, "Deleting...");
        new Thread(() -> {
            try {
                dao.delete(id);
                List<Student> data = dao.findAll();
                SwingUtilities.invokeLater(() -> {
                    refreshTable(data);
                    setBusy(false, "Deleted!");
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    setBusy(false, "Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void loadData() {
        setBusy(true, "Loading...");
        new Thread(() -> {
            try {
                List<Student> data = dao.findAll();
                SwingUtilities.invokeLater(() -> {
                    refreshTable(data);
                    setBusy(false, "Ready");
                });
            } catch (Exception ex) {
                SwingUtilities.invokeLater(() ->
                    setBusy(false, "Error: " + ex.getMessage()));
            }
        }).start();
    }

    private void refreshTable(List<Student> data) {
        model.setRowCount(0);
        for (Student s : data)
            model.addRow(new Object[]{s.getId(), s.getNim(), s.getName()});
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainGUI().setVisible(true));
    }
}
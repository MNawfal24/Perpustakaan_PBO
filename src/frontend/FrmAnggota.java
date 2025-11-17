package frontend;

import backend.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class FrmAnggota extends JFrame {

    private JTextField txtIdAnggota;
    private JTextField txtNama;
    private JTextField txtAlamat;
    private JTextField txtTelepon;
    private JTextField txtCari;

    private JButton btnSimpan;
    private JButton btnHapus;
    private JButton btnTambahBaru;
    private JButton btnCari;

    private JTable tblAnggota;
    private JScrollPane jScrollPane1;

    public FrmAnggota() {
        setTitle("Form Anggota");
        setSize(650, 480);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Label & Textfield
        JLabel lblId = new JLabel("ID Anggota");
        lblId.setBounds(20, 20, 100, 20);
        add(lblId);

        txtIdAnggota = new JTextField("0");
        txtIdAnggota.setEnabled(false);
        txtIdAnggota.setBounds(130, 20, 100, 20);
        add(txtIdAnggota);

        JLabel lblNama = new JLabel("Nama");
        lblNama.setBounds(20, 50, 100, 20);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(130, 50, 300, 20);
        add(txtNama);

        JLabel lblAlamat = new JLabel("Alamat");
        lblAlamat.setBounds(20, 80, 100, 20);
        add(lblAlamat);

        txtAlamat = new JTextField();
        txtAlamat.setBounds(130, 80, 300, 20);
        add(txtAlamat);

        JLabel lblTelepon = new JLabel("Telepon");
        lblTelepon.setBounds(20, 110, 100, 20);
        add(lblTelepon);

        txtTelepon = new JTextField();
        txtTelepon.setBounds(130, 110, 300, 20);
        add(txtTelepon);

        // Buttons
        btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(20, 150, 100, 30);
        add(btnSimpan);

        btnTambahBaru = new JButton("Tambah Baru");
        btnTambahBaru.setBounds(130, 150, 130, 30);
        add(btnTambahBaru);

        btnHapus = new JButton("Hapus");
        btnHapus.setBounds(270, 150, 100, 30);
        add(btnHapus);

        txtCari = new JTextField();
        txtCari.setBounds(380, 155, 150, 25);
        add(txtCari);

        btnCari = new JButton("Cari");
        btnCari.setBounds(540, 155, 70, 25);
        add(btnCari);

        // Table
        tblAnggota = new JTable();
        jScrollPane1 = new JScrollPane(tblAnggota);
        jScrollPane1.setBounds(20, 200, 590, 230);
        add(jScrollPane1);

        // event listener
        btnSimpan.addActionListener(e -> btnSimpanActionPerformed());
        btnTambahBaru.addActionListener(e -> kosongkanForm());
        btnHapus.addActionListener(e -> btnHapusActionPerformed());
        btnCari.addActionListener(e -> cari(txtCari.getText()));

        tblAnggota.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tableKlik();
            }
        });

        // load data pertama
        kosongkanForm();
        tampilkanData();
    }

    private void kosongkanForm() {
        txtIdAnggota.setText("0");
        txtNama.setText("");
        txtAlamat.setText("");
        txtTelepon.setText("");
    }

    private void tampilkanData() {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().getAll();
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        for (Anggota ang : list) {
            Object[] row = {
                ang.getIdanggota(),
                ang.getNama(),
                ang.getAlamat(),
                ang.getTelepon()
            };
            model.addRow(row);
        }

        tblAnggota.setModel(model);
    }

    private void cari(String keyword) {
        String[] kolom = {"ID", "Nama", "Alamat", "Telepon"};
        ArrayList<Anggota> list = new Anggota().search(keyword);
        DefaultTableModel model = new DefaultTableModel(kolom, 0);

        for (Anggota ang : list) {
            Object[] row = {
                ang.getIdanggota(),
                ang.getNama(),
                ang.getAlamat(),
                ang.getTelepon()
            };
            model.addRow(row);
        }

        tblAnggota.setModel(model);
    }

    private void tableKlik() {
        int row = tblAnggota.getSelectedRow();
        txtIdAnggota.setText(tblAnggota.getValueAt(row, 0).toString());
        txtNama.setText(tblAnggota.getValueAt(row, 1).toString());
        txtAlamat.setText(tblAnggota.getValueAt(row, 2).toString());
        txtTelepon.setText(tblAnggota.getValueAt(row, 3).toString());
    }

    private void btnSimpanActionPerformed() {
        int id = Integer.parseInt(txtIdAnggota.getText());
        Anggota ang = new Anggota();

        // Jika bukan data baru, load datanya
        if (id != 0) {
            ang = ang.getById(id);
        }

        ang.setNama(txtNama.getText());
        ang.setAlamat(txtAlamat.getText());
        ang.setTelepon(txtTelepon.getText());

        ang.save();
        txtIdAnggota.setText("" + ang.getIdanggota());
        tampilkanData();
    }

    private void btnHapusActionPerformed() {
        int row = tblAnggota.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        int id = Integer.parseInt(tblAnggota.getValueAt(row, 0).toString());
        Anggota ang = new Anggota().getById(id);
        ang.delete();

        kosongkanForm();
        tampilkanData();
    }

    public static void main(String[] args) {
        new FrmAnggota().setVisible(true);
    }
}

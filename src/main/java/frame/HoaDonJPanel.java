/*
 * trungpvpp02786
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package frame;

import DAO.BillDAO;
import DAO.HoiVienDAO;
import DAO.NhanVienDao;
import DAO.OrderDAO;
import DAO.OrderDetailsDAO;
import helper.DateHelper;
import helper.DialogHelper;
import helper.LoadingHelper;
import helper.NumberHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import model.Bill;
import model.NhanVien;
import model.orderedDishes;

/**
 *
 * @author capoo
 */
public class HoaDonJPanel extends javax.swing.JPanel {

    BillDAO dao = new BillDAO();
    OrderDAO orderDao = new OrderDAO();
    OrderDetailsDAO odDao = new OrderDetailsDAO();
    NhanVienDao nvdao = new NhanVienDao();
    HoiVienDAO hvdao = new HoiVienDAO();
    static List<Bill> billList = new ArrayList<>();
    static List<orderedDishes> orders = new ArrayList<>();

    /**
     * Creates new form HoaDonJPanel
     */
    public HoaDonJPanel() {
        initComponents();

        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblBillList = new javax.swing.JTable();
        txtSearch = new javax.swing.JTextField();
        cboFilter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        oldOrderList = new javax.swing.JTable();
        jButton5 = new javax.swing.JButton();

        tblBillList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "bill_id", "NV Trực", "Tổng Tiền", "Ngày Tạo", "Note", "Tên KH", "Voucher", "Mã Bill", "Trạng Thái", "Mã Đơn"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblBillList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblBillListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblBillList);
        if (tblBillList.getColumnModel().getColumnCount() > 0) {
            tblBillList.getColumnModel().getColumn(0).setMinWidth(1);
            tblBillList.getColumnModel().getColumn(0).setPreferredWidth(1);
            tblBillList.getColumnModel().getColumn(0).setMaxWidth(1);
            tblBillList.getColumnModel().getColumn(1).setPreferredWidth(0);
        }

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        cboFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Lọc" }));
        cboFilter.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                cboFilterPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });

        jLabel1.setText("Tìm Kiếm:");

        jButton1.setText("Huỷ Đơn");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        oldOrderList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Tên món", "Số lượng", "Gộp giá"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(oldOrderList);

        jButton5.setText("Xác Nhận Thanh Toán");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(404, 404, 404)
                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jButton1)
                    .addComponent(jButton5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblBillListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblBillListMouseReleased
        // TODO add your handling code here
        int selectedRow = tblBillList.getSelectedRow();

// Get the value from the first column of the selected row
        Object order_ID = tblBillList.getValueAt(selectedRow, 9);
        fetchOrders(order_ID);
    }//GEN-LAST:event_tblBillListMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int selectedRow = tblBillList.getSelectedRow();

// Get the value from the first column of the selected row
        Object order_ID = tblBillList.getValueAt(selectedRow, 9);
        fetchOrders(order_ID);
        removeBill(order_ID);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cboFilterPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_cboFilterPopupMenuWillBecomeInvisible
        // TODO add your handling code here:
    }//GEN-LAST:event_cboFilterPopupMenuWillBecomeInvisible

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchKeyReleased

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        if (DialogHelper.confirm(this, "Xác nhận hoàn tất đơn?")) {
            int selectedRow = tblBillList.getSelectedRow();
            try {
                Object billID = tblBillList.getValueAt(selectedRow, 0);
                Object orderID = tblBillList.getValueAt(selectedRow, 9);
                closeBill(Integer.valueOf(billID.toString()), Integer.valueOf(orderID.toString()));
            } catch (Exception e) {
                DialogHelper.alert(jLabel1, "Có lỗi khi xác nhận...");
                e.printStackTrace();
            }
            fetchFull();
            fillTable(billList);
        }
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboFilter;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable oldOrderList;
    private javax.swing.JTable tblBillList;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void fetchFull() {
        billList.clear();
        billList = dao.selectAll();
        cboFilter.removeAllItems();
        cboFilter.addItem("Chưa Thanh Toán");
        cboFilter.addItem("Đã Thanh Toán");
    }

    public void init() {
        new Thread(() -> {
            try {
                fetchFull();
                fillTable(billList);
//                fetchOrders(order_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void fillTable(List<Bill> billList) {
        List<NhanVien> nvList = nvdao.selectAll();
        DefaultTableModel model = (DefaultTableModel) tblBillList.getModel();
        model.setRowCount(0);
        for (Bill bill : billList) {
            Object[] row = {bill.getBill_ID(), getNhanVienName(bill.getUser_ID(), nvList), NumberHelper.formatNumber("" + (bill.getAmount())), DateHelper.formatTime(bill.getBillDate()), bill.getNote(), getTenKH(bill.getMa_KH()), bill.getVCode(), bill.getBillCode(), bill.getStatusName(), bill.getOrder_ID()};
            model.addRow(row);
        }
    }

    private String getNhanVienName(int user_ID, List<NhanVien> nvList) {
        String name = "";
        for (NhanVien nhanVien : nvList) {
            if (nhanVien.getMaNV() == user_ID) {
                name = nhanVien.getName();
            }
        }
        return name;
    }

    private Object getTenKH(int ma_KH) {
        String tenKH = "";
        tenKH = hvdao.getNameByID(ma_KH);
        return tenKH;
    }

    private void closeBill(Integer billID, Integer orderID) {
        dao.setStatus(billID, 1);
        orderDao.setStatus(orderID, 1);
    }

    private void fetchOrders(Object order_ID) {
        try {
            orders.clear();
            orders = odDao.getOrderedDish(Integer.parseInt(order_ID.toString()));
            fillOrderList(orders);
        } catch (Exception ex) {
            Logger.getLogger(HoaDonJPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void fillOrderList(List<orderedDishes> ordered) {
        DefaultTableModel model = (DefaultTableModel) oldOrderList.getModel();
        model.setRowCount(0);
        int count = 1;
        for (orderedDishes od : ordered) {

            Object[] row = new Object[]{od.getFoodName(), od.getQuantity(), NumberHelper.formatNumber(od.getPrice().toString())};
            model.addRow(row);
            count++;
        }

    }

    @SuppressWarnings("Unchecked")
    private void removeBill(Object order_ID) {
        if (DialogHelper.confirm(jButton5, "Chắc chắn huỷ đơn " + order_ID.toString())) {
            LoadingHelper load = new LoadingHelper(jButton5, "Loading...");
            new Thread(() -> {
                try {
                    load.showLoadingDialog();
                    dao.removeBillAndOrder(Integer.parseInt(order_ID.toString()));
                    billList.clear();
                    billList = dao.selectAll();
                    load.done();
                    DialogHelper.alert(jButton5, "Huỷ thành công.");
                    fetchFull();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}

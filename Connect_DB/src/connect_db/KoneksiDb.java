package connect_db;

import java.sql.*;
import java.util.Scanner;

public class KoneksiDb {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/perpustakaan";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static Statement stmt;
    static ResultSet rs;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Menambahkan Data");
            System.out.println("2. Mengedit Data");
            System.out.println("3. Menghapus Data");
            System.out.println("4. Menampilkan Data");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi: ");
            int pilihan = scanner.nextInt();
            scanner.nextLine(); 

            switch (pilihan) {
                case 1:
                    insert(scanner);
                    break;
                case 2:
                    edit(scanner);
                    break;
                case 3:
                    delete(scanner);
                    break;
                case 4:
                    show();
                    break;
                case 5:
                    System.out.println("Keluar dari program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Pilihan tidak valid,Silahkan coba lagi.");
            }
        }
    }

    public static void insert(Scanner scanner) {
        System.out.print("Judul Buku: ");
        String Judul_buku = scanner.nextLine();
        System.out.print("Stok: ");
        int Stok = scanner.nextInt();
        System.out.print("Tahun Terbit (Tahun-Bulan-Tanggal): ");
        Date Tahun_terbit = Date.valueOf(scanner.next());
        System.out.print("ID Penulis: ");
        String Id_penulis = scanner.next();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "INSERT INTO buku (Judul_buku, Stok, Tahun_terbit, Id_penulis) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, Judul_buku);
            ps.setInt(2, Stok);
            ps.setDate(3, Tahun_terbit);
            ps.setString(4, Id_penulis);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void edit(Scanner scanner) {
        System.out.print("ID Buku yang akan diedit: ");
        int Id_buku = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Judul Buku baru: ");
        String Judul_buku = scanner.nextLine();
        System.out.print("Stok baru: ");
        int Stok = scanner.nextInt();
        System.out.print("Tahun Terbit baru (YYYY-MM-DD): ");
        Date Tahun_terbit = Date.valueOf(scanner.next());
        System.out.print("ID Penulis baru: ");
        String Id_penulis = scanner.next();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "UPDATE buku SET Judul_buku = ?, Stok = ?, Tahun_terbit = ?, Id_penulis = ? WHERE Id_buku = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, Judul_buku);
            ps.setInt(2, Stok);
            ps.setDate(3, Tahun_terbit);
            ps.setString(4, Id_penulis);
            ps.setInt(5, Id_buku);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void delete(Scanner scanner) {
        System.out.print("ID Buku yang akan dihapus: ");
        int Id = scanner.nextInt();

        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "DELETE FROM buku WHERE Id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, Id);

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (Exception e) {
        }
    }

    public static void show() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            String sql = "SELECT b.Judul_buku, b.Stok, b.Tahun_terbit, p.Nama_penulis " +
                         "FROM buku b " +
                         "JOIN penulis p ON b.Id_penulis = p.Id_penulis";
            rs = stmt.executeQuery(sql);
            int i = 1;
            while (rs.next()) {
                System.out.println("Data ke-" + i);
                System.out.println("Judul Buku : " + rs.getString("Judul_buku"));
                System.out.println("Stok : " + rs.getInt("Stok"));
                System.out.println("Tanggal Terbit: " + rs.getDate("Tahun_terbit"));
                System.out.println("Nama Penulis: " + rs.getString("Nama_penulis"));
                i++;
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
        }
    }
}
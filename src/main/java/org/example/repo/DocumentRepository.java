package org.example.repo;

import org.example.db.Database;
import org.example.model.Document;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentRepository {

    public void insert(Path path, String mime) {
        try (PreparedStatement st = Database.get().prepareStatement(
                "insert into documents(file_name, file_path, mime_type, size_bytes, sha256) values (?,?,?,?,?)")) {
            String fileName = path.getFileName().toString();
            long size = Files.size(path);
            String sha256 = sha256Hex(Files.readAllBytes(path));

            st.setString(1, fileName);
            st.setString(2, path.toString());
            st.setString(3, mime);
            st.setLong(4, size);
            st.setString(5, sha256);
            st.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Document> findAll() {
        try (Statement s = Database.get().createStatement();
             ResultSet rs = s.executeQuery("select id,file_name,file_path,mime_type,size_bytes,created_at,sha256,ocr_done from documents order by created_at desc")) {
            List<Document> out = new ArrayList<>();
            while (rs.next()) {
                out.add(new Document(
                        rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getLong(5), rs.getString(6), rs.getString(7), rs.getInt(8) == 1
                ));
            }
            return out;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String sha256Hex(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] dig = md.digest(data);
        StringBuilder sb = new StringBuilder();
        for (byte b : dig) sb.append(String.format("%02x", b));
        return sb.toString();
    }
}

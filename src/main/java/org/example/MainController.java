package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.example.model.Document;
import org.example.repo.DocumentRepository;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class MainController {

    @FXML private TableView<Document> table;
    @FXML private TableColumn<Document, String> colName;
    @FXML private TableColumn<Document, Number> colSize;
    @FXML private TableColumn<Document, String> colMime;
    @FXML private TableColumn<Document, String> colCreated;

    private final DocumentRepository repo = new DocumentRepository();
    private final ObservableList<Document> items = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colName.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("fileName"));
        colSize.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("sizeBytes"));
        colMime.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("mimeType"));
        colCreated.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("createdAt"));

        table.setItems(items);
        refreshTable();
    }

    public void onLoadDocumentClick(ActionEvent e) {
        Window owner = table.getScene().getWindow();
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dokumenty", "*.pdf", "*.jpg", "*.jpeg", "*.png"),
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*")
        );
        File file = fc.showOpenDialog(owner);
        if (file == null) return;

        String mime = guessMime(file);
        repo.insert(Path.of(file.toURI()), mime);
        refreshTable();
    }

    private void refreshTable() {
        List<Document> all = repo.findAll();
        System.out.println("Wczytano z DB: " + all.size() + " dokumentów");
        items.setAll(all);
    }


    private String guessMime(File f) {
        String n = f.getName().toLowerCase();
        if (n.endsWith(".pdf")) return "application/pdf";
        if (n.endsWith(".jpg") || n.endsWith(".jpeg")) return "image/jpeg";
        if (n.endsWith(".png")) return "image/png";
        return "application/octet-stream";
    }
}

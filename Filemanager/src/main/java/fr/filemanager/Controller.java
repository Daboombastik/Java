package fr.filemanager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class Controller {
    @FXML
    VBox leftPanel, rightPanel;

    public void btnExitAction(ActionEvent actionEvent) {
        Platform.exit();
    }

    public void btnCopyAction(ActionEvent actionEvent) {
        PanelController leftPan = (PanelController) leftPanel.getProperties().get("controlMain");
        PanelController rightPan = (PanelController) rightPanel.getProperties().get("controlMain");

        if (leftPan.getSelectedFileName() == null && rightPan.getSelectedFileName() == null) {
            new Alert(Alert.AlertType.ERROR, "choose Directory or File", ButtonType.OK).showAndWait();
            return;
        }
        ProgressBar progressBar = new ProgressBar();
        for (int i = 0; i < 100; i++) {
            progressBar.setProgress(1.0 / 100 * i);
        }

        PanelController sourcePanel, destinationPanel;
        sourcePanel = destinationPanel = null;
        if (leftPan.getSelectedFileName() != null) {
            sourcePanel = leftPan;
            destinationPanel = rightPan;
        }
        if (rightPan.getSelectedFileName() != null) {
            sourcePanel = rightPan;
            destinationPanel = leftPan;
        }

        assert sourcePanel != null;
        Path sourcePath = Paths.get(sourcePanel.getCurrentPath(), sourcePanel.getSelectedFileName());
        Path destinationPath = Paths.get(destinationPanel.getCurrentPath()).
                resolve(sourcePath.getFileName().toString());

        try {
            Files.copy(sourcePath, destinationPath);
            destinationPanel.updateList(Paths.get(destinationPanel.getCurrentPath()));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "File already exists", ButtonType.OK).showAndWait();
        }
    }
}

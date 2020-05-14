package fr.filemanager;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class PanelController implements Initializable {
    @FXML
    TableView<FileInfo> filesTable;

    @FXML
    ComboBox<String> diskBox;

    @FXML
    TextField pathField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //добавляем в таблицу колонку из FileInfo -> enum FileType
        TableColumn<FileInfo, String> fileTypeColumn = new TableColumn<>();
        fileTypeColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getFileType().getName()
        ));
        fileTypeColumn.setPrefWidth(24);

        //добавляем колонку Имя директории/файла из FileInfo
        TableColumn<FileInfo, String> fileNameColumn = new TableColumn<>("Path");
        fileNameColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getFileName()
        ));
        fileNameColumn.setPrefWidth(240);

        //добавляем колонку Размер
        TableColumn<FileInfo, Long> fileSizeColumn = new TableColumn<>("Size");
        fileSizeColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getSize()));
        //форматируем ячейски с указанием размера
        //в формат.строке задаем пробелы между тысячами
        //в случае с директорией укызываем слово DIR
        fileSizeColumn.setCellFactory(cell -> {
            return new TableCell<FileInfo, Long>() {
                @Override
                protected void updateItem(Long item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        String text = String.format("%,d bytes", item);
                        if (item == -1L) {
                            text = "DIR";
                        }
                        setText(text);
                    }
                }
            };
        });
        fileSizeColumn.setPrefWidth(120);

        //добавляем колонку Дата последнего измнения
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        TableColumn<FileInfo, String> fileLastModifiedColumn = new TableColumn<>("Last Modified");
        fileLastModifiedColumn.setCellValueFactory(param -> new SimpleStringProperty(
                param.getValue().getLastModified().format(dtf)));
        fileLastModifiedColumn.setPrefWidth(120);

        //сортируем таблицу по типам отображения Directory/Files
        filesTable.getSortOrder().add(fileTypeColumn);
        filesTable.getColumns().addAll(fileTypeColumn, fileNameColumn, fileSizeColumn, fileLastModifiedColumn);
        //Идем в корень текущей директории
        updateList(Paths.get("C:\\"));

        //получаем список дисков и выбираем первый из них
        diskBox.getItems().clear();
        for (Path path : FileSystems.getDefault().getRootDirectories()) {
            diskBox.getItems().add(path.toString());
        }
        diskBox.getSelectionModel().selectFirst();
        //можно еще командой getSelectionModel().select(0);

        //по двойному клику мыши переходим в папку
        filesTable.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                Path path = Paths.get(pathField.getText()).
                        resolve(filesTable.getSelectionModel().getSelectedItem().getFileName());
                if (Files.isDirectory(path)) {
                    updateList(path);
                }
            }
        });
    }

    //метод отображения файлов в таблице
    public void updateList(Path path) {
//                            filesTable.setStyle("-fx-font-weight: bold");
        try {
            //очищаем таблицу
            filesTable.getItems().clear();
            //получаем в таблицу поток Stream файлов из переменной path
            filesTable.getItems().addAll(Files.list(path).
                    //преобразуем поток в тип FileInfo
                            map(FileInfo::new).
                    //собираем в список
                            collect(Collectors.toList()));
            filesTable.sort();
            //заполняем путь к файлу/директории в TextField
            pathField.setText(path.toString());
            //лектор предложил такой путь, как ниже...
            //pathField.setText (path.normalize().toAbsolutePath().toString());

        } catch (IOException e) {
            //создаем окно предупреждения
            new Alert(Alert.AlertType.WARNING, "Unable to get the list of files", ButtonType.OK).
                    showAndWait();
        }
    }

    public void btnPathUpAction(ActionEvent actionEvent) {
        Path upperPath = Paths.get(pathField.getText()).getParent();
        if (upperPath != null)
            updateList(upperPath);
    }

    public void btnSelectDiskAction(ActionEvent actionEvent) {
        ComboBox<String> root = (ComboBox<String>) actionEvent.getSource();
        updateList(Paths.get(root.getSelectionModel().getSelectedItem()).getRoot());
        //в корень действующей папки не переходит!!! -> доработать!!!
    }

    public String getSelectedFileName () {
        if (filesTable.isFocused())
        return filesTable.getSelectionModel().getSelectedItem().getFileName();
        else return null;
    }

    public String getCurrentPath(){
        return pathField.getText();
    }
}

package scriptModifier;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

 
public final class ScriptModifier extends Application {
 
    @Override
    public void start(final Stage stage) {
        stage.setTitle("ScriptModifier");
 
        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
 
        final Button openButton = new Button("Rechercher...");
        final Button openMultipleButton = new Button("Rechercher...");
        final Button operation = new Button("Modifier");
        
        final Label fichierAModifier = new Label("Fichier à modifier:");
        final TextField champFichierAModifier = new TextField ();
        
        final Label fichierModifie = new Label("Fichier modifié:");
        final TextField champFichierModifie = new TextField ();

        openButton.setOnAction(
            (final ActionEvent e) -> {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    champFichierAModifier.setText(file.getAbsolutePath());
                }
        });
        openMultipleButton.setOnAction(
            (final ActionEvent e) -> {
                configureDirectoryChooser(directoryChooser);
                champFichierModifie.setText(directoryChooser.showDialog(stage).toString());
        });

        operation.setOnAction(
            (final ActionEvent e) -> {
                String nomFichier =  (champFichierAModifier.getText().substring(0, champFichierAModifier.getText().length()-4)) + "_MODIFIE.sql";
                operation(champFichierAModifier.getText(), nomFichier);
        });
        
        final GridPane inputGridPane = new GridPane();
 
        GridPane.setConstraints(fichierAModifier, 0, 0);
        GridPane.setConstraints(champFichierAModifier, 1, 0);
        GridPane.setConstraints(fichierModifie, 0, 1);
        GridPane.setConstraints(champFichierModifie, 1, 1);
        GridPane.setConstraints(openButton, 2, 0);
        GridPane.setConstraints(openMultipleButton, 2, 1);
        GridPane.setConstraints(operation, 1, 2);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(fichierAModifier, champFichierAModifier, fichierModifie, champFichierModifie, openButton, openMultipleButton, operation);
        
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    public static void main(String[] args) {
        Application.launch(args);
    }
   
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("Recherche Fichier");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    private static void configureDirectoryChooser(final DirectoryChooser directoryChooser){                           
        directoryChooser.setTitle("Emplacement");
        directoryChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    private static void operation(String fichier, String fichierSortie){
        try{
            InputStream ips=new FileInputStream(fichier); 
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedWriter writer;
            try (BufferedReader br = new BufferedReader(ipsr)) {
                String ligne;
                writer = new BufferedWriter(new FileWriter(fichierSortie));
                while ((ligne=br.readLine())!=null){
                    writer.write(ligne + "\n");
                    writer.write("GO\n");
                }
            }
            writer.close();
        }		
	catch (Exception e){
            System.out.println(e.toString());
	}
    }
}
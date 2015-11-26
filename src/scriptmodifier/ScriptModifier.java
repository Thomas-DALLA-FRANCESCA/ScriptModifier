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
        // Titre de la fenetre principale
        stage.setTitle("ScriptModifier");
 
        // Déclaration des sélecteurs
        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
 
        // Déclaration des boutons
        final Button openButton = new Button("Rechercher...");
        final Button openMultipleButton = new Button("Rechercher...");
        final Button operation = new Button("Modifier");
        
        // Déclaration du champ d'affichage du fichier d'entrée sélectionné
        final Label fichierAModifier = new Label("Fichier à modifier:");
        final TextField champFichierAModifier = new TextField ();
        
        // Déclaration du champ d'affichage du répertoire de destination sélectionné
        final Label fichierModifie = new Label("Fichier modifié:");
        final TextField champFichierModifie = new TextField ();

        // Action à réaliser pour le bouton de sélection de fichier
        openButton.setOnAction(
            (final ActionEvent e) -> {
                configureFileChooser(fileChooser);
                File file = fileChooser.showOpenDialog(stage);
                if (file != null) {
                    champFichierAModifier.setText(file.getAbsolutePath());
                }
        });
        
        // Action à réaliser pour le bouton de sélection de répertoire
        openMultipleButton.setOnAction(
            (final ActionEvent e) -> {
                configureDirectoryChooser(directoryChooser);
                champFichierModifie.setText(directoryChooser.showDialog(stage).toString());
        });

        // Action à réaliser pour le bouton déclenchant l'opération
        operation.setOnAction(
            (final ActionEvent e) -> {
                String nomFichier =  (champFichierAModifier.getText().substring(0, champFichierAModifier.getText().length()-4)) + "_MODIFIE.sql";
                operation(champFichierAModifier.getText(), nomFichier);
        });
        
        // Déclaration du gridPane permettant l'agencement des différents éléments graphiques
        final GridPane inputGridPane = new GridPane();
 
        // Emplacements des éléments graphiques
        GridPane.setConstraints(fichierAModifier, 0, 0);
        GridPane.setConstraints(champFichierAModifier, 1, 0);
        GridPane.setConstraints(fichierModifie, 0, 1);
        GridPane.setConstraints(champFichierModifie, 1, 1);
        GridPane.setConstraints(openButton, 2, 0);
        GridPane.setConstraints(openMultipleButton, 2, 1);
        GridPane.setConstraints(operation, 1, 2);
        
        // Déclaration de la taille du gridPane
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        
        // Ajout des éléments au gridPane
        inputGridPane.getChildren().addAll(fichierAModifier, champFichierAModifier, fichierModifie, champFichierModifie, openButton, openMultipleButton, operation);
        
        // Déclaration du Pane contenant le gridPane
        final Pane rootGroup = new VBox(12);
        rootGroup.getChildren().addAll(inputGridPane);
        rootGroup.setPadding(new Insets(12, 12, 12, 12));
 
        // Méthode d'affichage des éléments graphiques
        stage.setScene(new Scene(rootGroup));
        stage.show();
    }
 
    // Méthode de lancement de l'application
    public static void main(String[] args) {
        Application.launch(args);
    }
   
    /*
    Méthode de configuration du sélecteur de fichier
    @param : sélecteur de fichier
    */
    private static void configureFileChooser(final FileChooser fileChooser){                           
        fileChooser.setTitle("Recherche Fichier");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    /*
    Méthode de configuration du sélecteur de répertoire
    @ param : Sélecteur de répertoire
    */
    private static void configureDirectoryChooser(final DirectoryChooser directoryChooser){                           
        directoryChooser.setTitle("Emplacement");
        directoryChooser.setInitialDirectory(
            new File(System.getProperty("user.home"))
        ); 
    }
    
    /*
    Méthode effectuant la transformation du fichier texte
    @ param fichier : Nom du fichier à traiter
    @param fichierSortie : Nom du fichier traité créé
    */
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
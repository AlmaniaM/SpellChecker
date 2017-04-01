/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wou.cs260.SpellChecker;

import javafx.application.Application;
import javafx.beans.property.SimpleLongProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * @author Almania
 */
public class ADTGui extends Application
{
    private static int WIDTH = 1100;
    private static int HEIGHT = 700;

    private int hashTableSize = 150000;

    private final int DL_LIST = 0;
    private final int BS_TREE_SET = 1;
    private final int AVL_TREE_SET = 2;
    private final int OCH_SET = 3;
    int curADTType = DL_LIST;

    SpellCheckUser spellCheckUser;

    private String randomDict = ".//English_Dictionary_Randomized.txt";
    private String nonRandDict = ".//English_Dictionary.txt";
    private String currentDict = nonRandDict;

    private String largeTextFile = ".//TextFileLarge.txt";
    private String smallTextFile = ".//TextFile.txt";
    private String currentTextFile = largeTextFile;

    private final String SMALL_FILE_NAME = "Small File";
    private final String LARGE_FILE_NAME = "Large File";
    private String defaultFileName = SMALL_FILE_NAME;

    private final String RANDOM_DICT = "Randomized";
    private final String NON_RANDOM_DICT = "Non Randomized";
    private String defaultDict = NON_RANDOM_DICT;

    private final String DL_LIST_NAME = "DLList";
    private final String BST_SET_NAME = "BSTreeSet";
    private final String AVL_TREE_SET_NAME = "AVLTreeSet";
    private final String OCH_SET_NAME = "OpenChainHashSet";
    private String defaultADT = DL_LIST_NAME;

    private ObservableList<Result> results;

    public TextArea outputArea;
    private TextField curADTText;
    private TextField curFileText;
    private TextField curDictText;
    private TextField twcText;
    private TextField tcwText;
    private TextField tiwText;
    private TextField acpwText;
    private TextField rfcText;

    private TableColumn adtColumn;
    private TableColumn dtColumn;
    private TableColumn tfColumn;
    private TableColumn twcColumn;
    private TableColumn tcwColumn;
    private TableColumn tiwColumn;
    private TableColumn acpwColumn;
    private TableColumn rfcColumn;
    private TableColumn fasterColumn;

    private TableView resultsTable;

    private HBox outputBox;

    private Button saveButton;
    private Button startButton;
    private Button clearTable;

    private Result adtResult;


    @Override
    public void start(Stage primaryStage)
    {
        this.results = FXCollections.observableArrayList();

        StackPane root = new StackPane();
        root.getChildren().add(this.getMainLayout());

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.getStylesheets().add("edu/wou/cs260/SpellChecker/style.css");

        primaryStage.setTitle("Spell Checker");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * getMainLayout returns the layout manager that contains every Node in the program
     * @return A Node containing everything in the program
     */
    private Node getMainLayout()
    {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);

        clearTable = new Button("Clear Table");
        clearTable.setId("clearButton");
        clearTable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event)
            {
                clearResultsTable();
            }
        });

        HBox menuBarButtonPane = new HBox();
        HBox.setHgrow(clearTable, Priority.ALWAYS);

        VBox mainLayout = new VBox();
        mainLayout.setMaxSize(this.WIDTH, this.HEIGHT);
        final MenuBar menuBar = new MenuBar();
        menuBar.setMinWidth(((WIDTH - menuBar.getWidth()) - clearTable.getWidth()) - 80);

        menuBar.getMenus().addAll(this.getADTMenu(), this.getDictMenu(), this.getFileMenu());
        menuBar.setMinHeight(28);

        menuBarButtonPane.getChildren().addAll(menuBar, clearTable);

        mainLayout.getChildren().addAll(menuBarButtonPane, getTabbedLayout());

        splitPane.getItems().addAll(mainLayout);

        return splitPane;
    }

    /**
     * getFileMenu returns a Node with the menu items in the FileMenu
     * @return A Node with menu items from the File Menu
     */
    private Menu getFileMenu()
    {
        final MenuItem itemLarge = MenuItemBuilder.create().text("Large File").build();
        itemLarge.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent t)
            {
                currentTextFile = largeTextFile;
                if(curFileText != null)
                    curFileText.setText("Large File");
            }
        });

        final MenuItem itemSmall = MenuItemBuilder.create().text("Small File").build();
        itemSmall.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                currentTextFile = smallTextFile;
                if(curFileText != null)
                    curFileText.setText("Small File");
            }
        });

        Menu dictMenu = MenuBuilder.create().text("File Size").items(itemLarge, itemSmall).build();

        return dictMenu;
    }

    /**
     * getDictLayout returns the layout manager that contains every Node in the Dictionary menu
     * @return A Node containing everything in the Dictionary menu
     */
    private Menu getDictMenu()
    {
        final MenuItem itemRandom = MenuItemBuilder.create().text("Randomized").build();
        itemRandom.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                currentDict = randomDict;
                if(curDictText != null)
                    curDictText.setText(RANDOM_DICT);
            }
        });

        final MenuItem itemNonRand = MenuItemBuilder.create().text("In Order").build();
        itemNonRand.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                currentDict = nonRandDict;
                if(curDictText != null)
                    curDictText.setText(NON_RANDOM_DICT);
            }
        });

        Menu dictMenu = MenuBuilder.create().text("Dictionary").items(itemRandom, itemNonRand).build();

        return dictMenu;
    }

    /**
     * getADTLayout returns the layout manager that contains every Node in the ADT menu
     * @return A Node containing everything in the ADT menu
     */
    private Menu getADTMenu()
    {
        final MenuItem itemDLList = MenuItemBuilder.create().text("Doubly Linked List").build();
        itemDLList.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                curADTType = DL_LIST;
                if(curADTText != null)
                    curADTText.setText(DL_LIST_NAME);
            }
        });

        final MenuItem itemBSTList = MenuItemBuilder.create().text("Binary Search Tree").build();
        itemBSTList.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                curADTType = BS_TREE_SET;
                if(curADTText != null)
                    curADTText.setText(BST_SET_NAME);
            }
        });

        final MenuItem itemAVLList = MenuItemBuilder.create().text("AVL Tree").build();
        itemAVLList.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                curADTType = AVL_TREE_SET;
                if(curADTText != null)
                    curADTText.setText(AVL_TREE_SET_NAME);
            }
        });

        final MenuItem itemOCHSet = MenuItemBuilder.create().text("Open Chain Hash Set").build();
        itemOCHSet.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                curADTType = OCH_SET;
                if(curADTText != null)
                    curADTText.setText(OCH_SET_NAME);
            }
        });

        Menu adtMenu = MenuBuilder.create().text("ADT").items(itemDLList, itemBSTList, itemAVLList, itemOCHSet).build();

        return adtMenu;
    }

    /**
     * getTabbedLayout returns the layout manager that contains every Node in the TabbedPane
     * @return A Node containing everything in the TabbedPane
     */
    private Node getTabbedLayout()
    {
        HBox tabbedLayout = new HBox();
        final TabPane tabPane = new TabPane();
        tabPane.setPrefSize(this.WIDTH, this.HEIGHT);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        final Tab resultsTab = new Tab();
        resultsTab.setText("Results");
        resultsTab.setContent(this.getResultsLayout());
        resultsTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event)
            {
                if(resultsTab.isSelected())
                    clearTable.setDisable(true);
            }
        });

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(getCompareLayout());

        final Tab compareTab = new Tab();
        compareTab.setText("Compare Results");
        compareTab.setContent(getCompareLayout());
        compareTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event)
            {
                if(compareTab.isSelected())
                    clearTable.setDisable(false);
            }
        });

        tabPane.getTabs().addAll(resultsTab, compareTab);

        tabbedLayout.getChildren().addAll(tabPane);

        return tabbedLayout;
    }

    /**
     * getResultsLayout returns the layout manager that contains every Node in the Results Tab
     * @return A Node containing everything in the Results Tab
     */
    private Node getResultsLayout()
    {
        final double WIDTH = 180;
        final double HEIGHT = 25;

        final double LABEL_HEIGHT_SPACING = 24;
        final double TEXT_HEIGHT_SPACING = 19;
        final double HORIZONTAL_SPACING = 15;

        //Pane for current settings labels
        VBox mainVerticalPane = new VBox();
        mainVerticalPane.setSpacing(LABEL_HEIGHT_SPACING);
        mainVerticalPane.setPadding(new Insets(7, 0, 0, 0));

        //Pane to hold the save and start buttons
        HBox buttonPane = new HBox();
        buttonPane.setSpacing(HORIZONTAL_SPACING);
        buttonPane.setPadding(new Insets(20, 0, 0, 15));

        //Pane for Results labels
        VBox resultsLabelPane = new VBox();
        resultsLabelPane.setSpacing(LABEL_HEIGHT_SPACING);
        resultsLabelPane.setPadding(new Insets(7, 0, 0, 0));

        //Pane for result TextViews
        VBox resultsTextPane = new VBox();
        resultsTextPane.setSpacing(TEXT_HEIGHT_SPACING);

        //Pane for holding the result Labels and TextViews
        HBox resultsHPane = new HBox();
        resultsHPane.setPadding(new Insets(16, 0, 0, 15));
        resultsHPane.setSpacing(HORIZONTAL_SPACING);

        //Create Results tab labels
        final Label twc = new Label("Total Words Checked");
        final Label tcw = new Label("Total Correct Words");
        final Label tiw = new Label("Total Incorrect Words");
        final Label acpw = new Label("Average Compare Per Word");
        final Label rfc = new Label("Runtime For Checking");

        final Label emptyLabel = new Label();
        emptyLabel.setMaxHeight(HEIGHT);
        final Label emptyLabel2 = new Label();
        emptyLabel2.setMaxHeight(HEIGHT);

        //Labels to display current settings that are selected
        final Label curADT = new Label("Current ADT Selected");
        final Label curDict = new Label("Current Dictionary");
        final Label curFile = new Label("Current Text File");

        //Create Results Text Fields
        twcText = new TextField();
        twcText.setMaxSize(WIDTH, HEIGHT);
        twcText.setId("tcwText");
        twcText.setEditable(false);

        tcwText = new TextField();
        tcwText.setMaxSize(WIDTH, HEIGHT);
        tcwText.setId("tcwText");
        tcwText.setEditable(false);

        tiwText = new TextField();
        tiwText.setMaxSize(WIDTH, HEIGHT);
        tiwText.setId("tiwText");
        tiwText.setEditable(false);

        acpwText = new TextField();
        acpwText.setMaxSize(WIDTH, HEIGHT);
        acpwText.setId("acpwText");
        acpwText.setEditable(false);

        rfcText = new TextField();
        rfcText.setMaxSize(WIDTH, HEIGHT);
        rfcText.setId("rfcText");
        rfcText.setEditable(false);

        curADTText = new TextField();
        curADTText.setMaxSize(WIDTH, HEIGHT);
        curADTText.setId("CurrentADT");
        curADTText.setEditable(false);
        curADTText.setText(defaultADT);

        curDictText = new TextField();
        curDictText.setMaxSize(WIDTH, HEIGHT);
        curDictText.setId("CurrentDict");
        curDictText.setEditable(false);
        curDictText.setText(defaultDict);

        curFileText = new TextField();
        curFileText.setMaxSize(WIDTH, HEIGHT);
        curFileText.setId("CurrentFile");
        curFileText.setEditable(false);
        curFileText.setText(defaultFileName);

        saveButton = new Button("Save Results");
        saveButton.setDisable(true);
        saveButton.setId("saveButton");
        saveButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                saveState();
            }
        });

        startButton = new Button("Start Test");
        startButton.setDisable(false);
        startButton.setId("startButton");
        startButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent actionEvent)
            {
                startSpellChecker();
            }
        });

        resultsLabelPane.getChildren().addAll(curADT, curDict, curFile, emptyLabel, twc, tcw, tiw, acpw, rfc);
        resultsTextPane.getChildren().addAll(curADTText, curDictText, curFileText, emptyLabel2, twcText, tcwText, tiwText, acpwText, rfcText);

        resultsHPane.getChildren().addAll(resultsLabelPane, resultsTextPane);

        buttonPane.getChildren().addAll(startButton, saveButton);

        mainVerticalPane.getChildren().addAll(resultsHPane, buttonPane);

        return mainVerticalPane;
    }

    /**
     * getMainLayout returns the layout manager that contains every Node in the Table Tab
     * @return A Node containing everything in the Table Tab
     */
    private Node getCompareLayout()
    {
        final int COLUMN_WIDTH = 140;

        //Create columns
        adtColumn = new TableColumn();
        adtColumn.setText("ADT Object");
        adtColumn.setCellValueFactory(new PropertyValueFactory("name"));

        dtColumn = new TableColumn();
        dtColumn.setText("Dictionary");
        dtColumn.setMinWidth(110);
        dtColumn.setCellValueFactory(new PropertyValueFactory("dict"));

        tfColumn = new TableColumn();
        tfColumn.setCellValueFactory(new PropertyValueFactory("tFile"));
        tfColumn.setText("Text File");

        twcColumn= new TableColumn();
        twcColumn.setMinWidth(COLUMN_WIDTH);
        twcColumn.setText("Total Words Checked");
        twcColumn.setCellValueFactory(new PropertyValueFactory("twc"));

        tcwColumn = new TableColumn();
        tcwColumn.setMinWidth(COLUMN_WIDTH);
        tcwColumn.setText("Total Correct Words");
        tcwColumn.setCellValueFactory(new PropertyValueFactory("tcw"));

        tiwColumn = new TableColumn();
        tiwColumn.setMinWidth(COLUMN_WIDTH);
        tiwColumn.setText("Total Incorrect Words");
        tiwColumn.setCellValueFactory(new PropertyValueFactory("tiw"));

        acpwColumn = new TableColumn();
        acpwColumn.setMinWidth(155);
        acpwColumn.setText("Avrg Compares Per Word");
        acpwColumn.setCellValueFactory(new PropertyValueFactory("acpw"));

        rfcColumn = new TableColumn();
        rfcColumn.setMinWidth(COLUMN_WIDTH);
        rfcColumn.setText("Runtime For Checking");
        rfcColumn.setCellValueFactory(new PropertyValueFactory("rfc"));

/*        fasterColumn = new TableColumn();
        fasterColumn.setText("Faster ADT");
        dtColumn.setCellValueFactory(new PropertyValueFactory("dict"));*/

        resultsTable = new TableView();
        resultsTable.setEditable(false);
        resultsTable.setId("ComparesTable");
        resultsTable.setItems(results);
        resultsTable.getColumns().addAll(adtColumn, dtColumn, tfColumn, twcColumn, tcwColumn, tiwColumn, acpwColumn, rfcColumn);

        return resultsTable;
    }
/*
    private Node getOutputPane()
    {
        outputBox = new HBox();
        ScrollPane scrollPane = new ScrollPane();

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setMaxWidth(800);
        outputArea.setMaxHeight(1000);

        scrollPane.setContent(outputArea);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        outputBox.setHgrow(scrollPane, Priority.ALWAYS);
        outputBox.getChildren().add(outputArea);

        return outputBox;
    }*/

    /**
     * setHashTableSize sets the size of the OpenChainHashSet ADT
     * @param hashTableSize An integer representing the size of the HashTable
     */
    public void setHashTableSize(int hashTableSize)
    {
        this.hashTableSize = hashTableSize;
    }

    private void startSpellChecker()
    {
        this.spellCheckUser = new SpellCheckUser(curADTType, hashTableSize);
        this.spellCheckUser.runSpellChecker(currentDict.toString(), currentTextFile.toString());

        if(twcText != null)
            twcText.setText("" + spellCheckUser.getTotalWordsChecked());

        if(tcwText != null)
            tcwText.setText("" + spellCheckUser.getCorrectWords());

        if(tiwText != null)
            tiwText.setText("" + spellCheckUser.getIncorrectWords());

        if(acpwText != null)
            acpwText.setText("" + spellCheckUser.getAverageCompares());

        if(rfcText != null)
            rfcText.setText("" + spellCheckUser.getRunTime());

        if(saveButton != null)
            saveButton.setDisable(false);

        adtResult = new Result();
        adtResult.setName(curADTText.getText().trim());
        adtResult.setDict(curDictText.getText().trim());
        adtResult.settFile(curFileText.getText().trim());
        adtResult.setTwc(new SimpleLongProperty(spellCheckUser.getTotalWordsChecked()));
        adtResult.setTcw(new SimpleLongProperty(spellCheckUser.getCorrectWords()));
        adtResult.setTiw(new SimpleLongProperty(spellCheckUser.getIncorrectWords()));
        adtResult.setAcpw(new SimpleLongProperty(spellCheckUser.getAverageCompares()));
        adtResult.setRfc(new SimpleLongProperty(spellCheckUser.getRunTime()));

        spellCheckUser.printResults();
    }

    private void clearFields()
    {
        if(twcText != null)
            twcText.clear();

        if(tcwText != null)
            tcwText.clear();

        if(tiwText != null)
            tiwText.clear();

        if(acpwText != null)
            acpwText.clear();

        if(rfcText != null)
            rfcText.clear();
    }

/*    private void clearOutput()
    {
        String ta = outputArea.getText().trim();
        if(!ta.equals(""))
            outputArea.clear();
    }*/

    private void clearResultsTable()
    {
        results = FXCollections.observableArrayList();
        if(resultsTable != null)
            resultsTable.setItems(results);
    }

    private void saveState()
    {
        results.add(adtResult);
        saveButton.setDisable(true);
        adtResult = null;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);
    }

}

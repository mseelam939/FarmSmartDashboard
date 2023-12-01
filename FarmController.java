package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.chart.*;
import java.util.List;
import machinelearning.PredictiveModel;


public class FarmController {

    @FXML private Pane bottomPane;
    @FXML private TreeView<String> treeView;
    @FXML private ListView<String> listCtrl;
    private TreeItem<String> rootItem = new TreeItem<>("Root");
    @FXML private ImageView myDroneImage;
    private ArrayList<FarmItemsContainer> itemsContainerCollection = new ArrayList<>();
    private static FarmController INSTANCE;
    @FXML
    private TableView<FarmComponent> farmItemsTableView; 
    private ObservableList<FarmComponent> farmComponents;

    @FXML
    private void initialize() {
        setupTreeView();
        setupEventHandlers();
        addDefaultItemsToRootTree();
        farmComponents = FXCollections.observableArrayList();
        setupFarmItemsTableView();
        loadItemsFromDataSource();
    }

    private void setupTreeView() {
        treeView.setRoot(rootItem);
        rootItem.setExpanded(true); 
        treeView.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(getItem());
                }
            }
        });
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleTreeItemSelection(newValue);
            }
        });
        loadTreeItems();
    }

    private void handleTreeItemSelection(TreeItem<String> selectedItem) {
        System.out.println("Selected item: " + selectedItem.getValue());
    }

    private void loadTreeItems() {
        TreeItem<String> item1 = new TreeItem<>("Item 1");
        TreeItem<String> container1 = new TreeItem<>("Container 1");
        rootItem.getChildren().addAll(item1, container1);
        TreeItem<String> subItem1 = new TreeItem<>("Sub-item 1");
        container1.getChildren().add(subItem1);
    }

private void setupEventHandlers() {
    treeView.setOnMouseClicked(this::handleTreeViewClick);
    listCtrl.setOnMouseClicked(this::handleListViewClick);
    ContextMenu treeContextMenu = createTreeContextMenu();
    treeView.setContextMenu(treeContextMenu);
}

private ContextMenu createTreeContextMenu() {
    ContextMenu contextMenu = new ContextMenu();
    MenuItem addMenuItem = new MenuItem("Add");
    addMenuItem.setOnAction(e -> addNewItem());
    MenuItem deleteMenuItem = new MenuItem("Delete");
    deleteMenuItem.setOnAction(e -> deleteSelectedItem());
    contextMenu.getItems().addAll(addMenuItem, deleteMenuItem);
    return contextMenu;
}

private void handleTreeViewClick(MouseEvent event) {
    if (event.getClickCount() == 2) { // Double-click detection
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Example: Display details of the selected item
            displayItemDetails(selectedItem.getValue());
        }
    }
    showSelectedItemCommands(); 
}

    private void handleListViewClick(MouseEvent event) {
        String selectedCommand = listCtrl.getSelectionModel().getSelectedItem();
        if (selectedCommand != null && !selectedCommand.contains("Commands")) {
            checkCommandsAndDoFn(selectedCommand);
        }
    }

private void setupFarmItemsTableView() {
    TableColumn<FarmComponent, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

    TableColumn<FarmComponent, Number> priceColumn = new TableColumn<>("Price");
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

    TableColumn<FarmComponent, Number> xCoordColumn = new TableColumn<>("X-Coordinate");
    xCoordColumn.setCellValueFactory(new PropertyValueFactory<>("locationX"));

    TableColumn<FarmComponent, Number> yCoordColumn = new TableColumn<>("Y-Coordinate");
    yCoordColumn.setCellValueFactory(new PropertyValueFactory<>("locationY"));

    TableColumn<FarmComponent, Number> lengthColumn = new TableColumn<>("Length");
    lengthColumn.setCellValueFactory(new PropertyValueFactory<>("length"));

    TableColumn<FarmComponent, Number> widthColumn = new TableColumn<>("Width");
    widthColumn.setCellValueFactory(new PropertyValueFactory<>("width"));

    TableColumn<FarmComponent, Number> heightColumn = new TableColumn<>("Height");
    heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));

    TableColumn<FarmComponent, String> typeColumn = new TableColumn<>("Type");
    typeColumn.setCellValueFactory(new PropertyValueFactory<>("type")); // Assuming there's a 'type' property to distinguish between item and container

    farmItemsTableView.getColumns().addAll(nameColumn, priceColumn, xCoordColumn, yCoordColumn, lengthColumn, widthColumn, heightColumn, typeColumn);
    farmItemsTableView.setItems(farmComponents);

    // Optionally, you can add a listener to handle row selection changes
    farmItemsTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
        if (newSelection != null) {
            handleFarmItemSelection(newSelection);
        }
    });
}

private void handleTreeViewClick(MouseEvent event) {
    TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
    if (item != null && event.getClickCount() == 2) { // Handle double click
        displayItemDetails(item.getValue());
    }
}

private void handleListViewClick(MouseEvent event) {
    String selectedCommand = listCtrl.getSelectionModel().getSelectedItem();
    if (selectedCommand != null) {
        executeCommand(selectedCommand);
    }
}

private void executeCommand(String command) {
    switch (command) {
        case "Add Item":
            addNewItem();
            break;
        case "Delete Item":
            deleteSelectedItem();
            break;
        // Handle other commands as needed
    }
}
    // Method to handle adding a new FarmComponent
private void addNewItem() {
    TextInputDialog dialog = new TextInputDialog("New Item");
    dialog.setTitle("Add New Item");
    dialog.setHeaderText("Enter the name of the new item");
    Optional<String> result = dialog.showAndWait();
    result.ifPresent(name -> {
        FarmComponent newItem = new FarmComponent(name, 0, 0, 0, 0, 0, 0); // Replace with actual constructor
        farmComponents.add(newItem);
        updateTreeView();
        updateTableView();
    });
}

    // Method to handle editing a selected FarmComponent
    private void editFarmComponent() {
        FarmComponent selectedComponent = farmItemsTableView.getSelectionModel().getSelectedItem();
        if (selectedComponent != null) {
            // Logic for editing the selected FarmComponent
        }
    }

    // Method to handle deleting a selected FarmComponent
    private void deleteFarmComponent() {
        FarmComponent selectedComponent = farmItemsTableView.getSelectionModel().getSelectedItem();
        if (selectedComponent != null) {
            farmComponents.remove(selectedComponent);
            // Additional logic for deletion
        }
    }

    private void addNewItem() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Item");
        dialog.setHeaderText("Enter details for the new item");
        dialog.setContentText("Name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            // For simplicity, using default values for other properties
            FarmItem newItem = new FarmItem(name, 0, 0, 10, 10, 10, 0);
            itemsContainerCollection.add(newItem);
            updateTreeView();
        });
    }

    private void editSelectedItem() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.getValue().equals("Root")) {
            TextInputDialog dialog = new TextInputDialog(selectedItem.getValue());
            dialog.setTitle("Edit Item");
            dialog.setHeaderText("Edit details for the item");
            dialog.setContentText("Name:");
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name -> {
                // Logic to update the item's name and other properties
                updateTreeView();
            });
        }
    }

    private void setupEventHandlers() {
    treeView.setOnMouseClicked(this::handleTreeViewClick);
    listCtrl.setOnMouseClicked(this::handleListViewClick);
    // Add other event handlers as needed
}

public void initialize() {
    setupFarmItemsTableView();
    setupEventHandlers();
    loadFarmComponents(); // Assuming a method to load initial data
}


    private void deleteSelectedItem() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null && !selectedItem.getValue().equals("Root")) {
            itemsContainerCollection.removeIf(item -> item.getName().equals(selectedItem.getValue()));
            updateTreeView();
        }
    }

    private void updateTreeView() {
        rootItem.getChildren().clear();
        for (FarmItemsContainer item : itemsContainerCollection) {
            TreeItem<String> itemNode = new TreeItem<>(item.getName());
            rootItem.getChildren().add(itemNode);
        }
    }

    private void checkCommandsAndDoFn(String command) {
        switch (command) {
            case "Add Item":
                addNewItem();
                break;
            case "Edit Item":
                editSelectedItem();
                break;
            case "Delete Item":
                deleteSelectedItem();
                break;
            // Add more cases as needed
        }
    }



    private void executeCommandOnFarmComponent(String command, FarmComponent component) {
        switch (command) {
            case "Add Item":
                addFarmComponent();
                break;
            case "Edit Item":
                editFarmComponent();
                break;
            case "Delete Item":
                deleteFarmComponent();
                break;
            // Implement other commands (Change name, price, location, etc.)
        }
    }
    
private void displayItemDetails(FarmItem item) {
    if (item == null) {
        // Handle case where no item is selected or item is null
        showErrorMessage("No item selected.");
        return;
    }

    Alert detailsDialog = new Alert(Alert.AlertType.INFORMATION);
    detailsDialog.setTitle("Item Details");
    detailsDialog.setHeaderText("Details for " + item.getName());

    // Creating a GridPane to layout the item details
    GridPane grid = new GridPane();
    grid.setHgap(10);
    grid.setVgap(10);

    // Adding labels and values for each property of the FarmItem
    grid.add(new Label("Name:"), 0, 0);
    grid.add(new Label(item.getName()), 1, 0);

    grid.add(new Label("Price:"), 0, 1);
    grid.add(new Label(String.valueOf(item.getPrice())), 1, 1);

    grid.add(new Label("X-Coordinate:"), 0, 2);
    grid.add(new Label(String.valueOf(item.getLocationX())), 1, 2);

    grid.add(new Label("Y-Coordinate:"), 0, 3);
    grid.add(new Label(String.valueOf(item.getLocationY())), 1, 3);

    detailsDialog.getDialogPane().setContent(grid);
    detailsDialog.showAndWait();
}

private void showErrorMessage(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
    alert.showAndWait();
}

    private void handleItemSelection() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            FarmItem farmItem = findItemByName(selectedItem.getValue());
            if (farmItem != null) {
                displayItemDetails(farmItem);
            }
        }
    }

    private FarmItem findItemByName(String name) {
        for (FarmItemsContainer container : itemsContainerCollection) {
            FarmItem item = container.findItemByName(name);
            if (item != null) {
                return item;
            }
        }
        return null;
    }

    private void updateItemDetails(FarmItem item, String newName, int newX, int newY) {
        item.setName(newName);
        item.setX(newX);
        item.setY(newY);
        updateTreeView();
    }

    @FXML
    private void handleSaveAction(ActionEvent event) {
        saveItemsToDataSource();
        // Additional logic after saving (e.g., showing confirmation message)
    }

    @FXML
    private void handleLoadAction(ActionEvent event) {
        loadItemsFromDataSource();
        // Additional logic after loading (e.g., refreshing UI components)
    }
    private void controlDroneAnimation(String action, FarmItem targetItem) {
        // Placeholder for controlling drone animation
        // 'action' could be "visit", "scan", etc.
        switch (action) {
            case "visit":
                // Logic for drone visiting a specific item
                break;
            case "scan":
                // Logic for drone scanning the farm
                break;
            // Add more cases as needed
        }
    }

private void generateFarmReport() {
    StringBuilder report = new StringBuilder();
    report.append("Farm Report\n");
    report.append("------------\n");

    // Counting different types of items
    Map<String, Integer> itemCounts = new HashMap<>();
    for (FarmItem item : itemsContainerCollection) {
        itemCounts.put(item.getType(), itemCounts.getOrDefault(item.getType(), 0) + 1);
    }

    report.append("Item Counts:\n");
    for (Map.Entry<String, Integer> entry : itemCounts.entrySet()) {
        report.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

    // Calculating total area used
    double totalArea = itemsContainerCollection.stream()
                            .mapToDouble(item -> item.getLength() * item.getWidth())
                            .sum();

    report.append("Total Area Used: ").append(totalArea).append("\n");

    Alert reportDialog = new Alert(Alert.AlertType.INFORMATION);
    reportDialog.setTitle("Farm Report");
    reportDialog.setHeaderText("Statistics and Information");
    reportDialog.setContentText(report.toString());
    reportDialog.showAndWait();
}

private void displayFarmAnalytics() {
    Alert analyticsDialog = new Alert(Alert.AlertType.INFORMATION);
    analyticsDialog.setTitle("Farm Analytics");
    analyticsDialog.setHeaderText("Analytics Overview");

    String analyticsData = "Item Type Counts:\n" + calculateItemTypeCounts() +
                           "\nAverage Price: " + calculateAveragePrice();

    analyticsDialog.setContentText(analyticsData);
    analyticsDialog.showAndWait();
}

private String calculateItemTypeCounts() {
    Map<String, Integer> typeCounts = new HashMap<>();
    for (FarmItem item : itemsContainerCollection) {
        typeCounts.put(item.getType(), typeCounts.getOrDefault(item.getType(), 0) + 1);
    }

    StringBuilder countsBuilder = new StringBuilder();
    for (Map.Entry<String, Integer> entry : typeCounts.entrySet()) {
        countsBuilder.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }
    
    return countsBuilder.toString();
}

private double calculateAveragePrice() {
    if (itemsContainerCollection.isEmpty()) {
        return 0.0;
    }

    double total = 0.0;
    for (FarmItem item : itemsContainerCollection) {
        total += item.getPrice();
    }
    return total / itemsContainerCollection.size();
}

@FXML
private void handleDroneControl(ActionEvent event) {
    String action = determineDroneActionBasedOnEvent(event);
    FarmItem targetItem = getSelectedFarmItemForDrone();

    controlDroneAnimation(action, targetItem);
}

private String determineDroneActionBasedOnEvent(ActionEvent event) {
    Button sourceButton = (Button) event.getSource();
    return sourceButton.getText().toLowerCase(); 
}

private FarmItem getSelectedFarmItemForDrone() {
    if (farmItemsTableView != null) {
        return farmItemsTableView.getSelectionModel().getSelectedItem();
    } else {
        return null;
    }
}


private void controlDroneAnimation(String action, FarmItem targetItem) {
    switch (action) {
        case "visit":
            if (targetItem != null) {
                animateDroneToItem(targetItem);
            }
            break;
        case "scan":
            animateDroneScanFarm();
            break;
        default:
            // Default action, maybe return to base or do nothing
            resetDronePosition();
            break;
    }
}

private void animateDroneToItem(FarmItem item) {
    double targetX = item.getX();
    double targetY = item.getY();
    
    TranslateTransition transition = new TranslateTransition(Duration.seconds(2), myDroneImage);
    transition.setToX(targetX);
    transition.setToY(targetY);
    transition.play();
}

private void animateDroneScanFarm() {
    SequentialTransition sequentialTransition = new SequentialTransition();
    
    TranslateTransition move1 = new TranslateTransition(Duration.seconds(2), myDroneImage);
    move1.setToX(100); // Example coordinates
    move1.setToY(200);

    TranslateTransition move2 = new TranslateTransition(Duration.seconds(2), myDroneImage);
    move2.setToX(300);
    move2.setToY(400);
    
    sequentialTransition.getChildren().addAll(move1, move2); // Add all movements
    sequentialTransition.play();
}

private void resetDronePosition() {
    myDroneImage.setX(initialDroneX); 
    myDroneImage.setY(initialDroneY); 
}


@FXML
private void handleGenerateReport(ActionEvent event) {
    generateFarmReport();
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Report generated successfully.", ButtonType.OK);
    alert.showAndWait();
}

private void generateFarmReport() {
    String report = "Farm Report\n---------------\n";
    reportBuilder.append("Total Items: ").append(itemsContainerCollection.size()).append("\n");
    System.out.println(report); 
}

@FXML
private void handleViewAnalytics(ActionEvent event) {
String analyticsInfo = displayFarmAnalytics();
    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Farm Analytics:\n" + analyticsInfo, ButtonType.OK);
    alert.showAndWait();
}

private void displayFarmAnalytics() {
    String analyticsInfo = "Farm Analytics\n---------------\n"; // Placeholder for analytics data
    analyticsBuilder.append("Most Common Item: ").append(getMostCommonItem()).append("\n");
    Alert alert = new Alert(Alert.AlertType.INFORMATION, analyticsInfo, ButtonType.OK);
    alert.showAndWait();
}

private UserSettings userSettings; 

private void loadUserSettings() {
    userSettings = new UserSettings();
    userSettings.setNotificationsEnabled(true); 
    userSettings.setPreferredLanguage("en"); 
    applyUserSettingsToUI();
}

private void saveUserSettings() {
    System.out.println("Saving User Settings: " + userSettings.toString());
}

@FXML
private void handleChangeSettings(ActionEvent event) {
    userSettings.setNotificationsEnabled(!userSettings.isNotificationsEnabled());
    saveUserSettings(); 
    applyUserSettingsToUI(); 
}

private void applyUserSettingsToUI() {
    if (userSettings.getPreferredLanguage().equals("en")) {
        setLanguage("English");
    } else if (userSettings.getPreferredLanguage().equals("es")) {
        setLanguage("Spanish");
    }
    // ... add more languages as needed
    updateNotificationSettings(userSettings.isNotificationsEnabled());
}

private void setLanguage(String language) {
    titleLabel.setText(language.equals("English") ? "Farm Dashboard" : "Tablero de la Granja");
    reportButton.setText(language.equals("English") ? "Generate Report" : "Generar Informe");
}
    @FXML
    private void handleRefresh(ActionEvent event) {
        loadItemsFromDataSource();
        applyUserSettingsToUI();
        updateUIComponents();
    }

private void loadItemsFromDataSource() {
    itemsContainerCollection.clear();
    ArrayList<FarmItemsContainer> newData = fetchDataFromDataSource();
    itemsContainerCollection.addAll(newData);
    updateTreeView();
}

private ArrayList<FarmItemsContainer> fetchDataFromDataSource() {
    ArrayList<FarmItemsContainer> newData = new ArrayList<>();
    // Logic to fetch data from the data source (database, API, file, etc.)
    // Replace the below example with actual data fetching logic
    newData.add(new FarmItemsContainer("Barn", 100, 10, 10, 200, 300, 150));
    newData.add(new FarmItemsContainer("Field", 50, 50, 20, 500, 400, 200));
    // Add more items as needed
    return newData;
}

private void updateTreeView() {
    rootItem.getChildren().clear();
    for (FarmItemsContainer container : itemsContainerCollection) {
        TreeItem<String> itemNode = new TreeItem<>(container.getName());
        rootItem.getChildren().add(itemNode);
        // Optionally, add child nodes for each container's items
        for (FarmItem item : container.getItems()) {
            TreeItem<String> childNode = new TreeItem<>(item.getName());
            itemNode.getChildren().add(childNode);
        }
    }
}

private void updateUIComponents() {
    updateFarmItemsTable();
    updateFarmStatisticsChart();
}

private void updateFarmItemsTable() {
    farmItemsTable.getItems().clear();
    for (FarmItemsContainer container : itemsContainerCollection) {
        farmItemsTable.getItems().addAll(container.getItems());
    }
}

private void updateFarmStatisticsChart() {
    farmStatisticsChart.getData().clear();
    XYChart.Series<String, Number> series = new XYChart.Series<>();
    series.setName("Farm Data");
    for (FarmItemsContainer container : itemsContainerCollection) {
        series.getData().add(new XYChart.Data<>(container.getName(), container.getPrice()));
    }
    farmStatisticsChart.getData().add(series);
}

    private void showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }
private PredictiveModel predictiveModel; 

private void initializeMachineLearningModel() {
    predictiveModel = new PredictiveModel();
    List<DataPoint> trainingData = prepareTrainingData();
    predictiveModel.trainModel(trainingData);
}

private List<DataPoint> prepareTrainingData() {
    List<DataPoint> trainingData = new ArrayList<>();
    for (FarmItemsContainer container : itemsContainerCollection) {
        for (FarmItem item : container.getItems()) {
            DataPoint dataPoint = new DataPoint();
            dataPoint.addFeature("price", item.getPrice());
            dataPoint.addFeature("size", item.getLength() * item.getWidth());
            trainingData.add(dataPoint);
        }
    }
    return trainingData;
}

private void performDataAnalysis() {
    for (FarmItemsContainer container : itemsContainerCollection) {
        Map<String, Long> itemCounts = container.getItems().stream()
            .collect(Collectors.groupingBy(FarmItem::getType, Collectors.counting()));
        System.out.println("Container: " + container.getName() + ", Item Distribution: " + itemCounts);
    }
}

private void makePredictionsBasedOnData() {
    for (FarmItemsContainer container : itemsContainerCollection) {
        for (FarmItem item : container.getItems()) {
            PredictionResult prediction = predictiveModel.predict(createPredictionInput(item));
            System.out.println("Prediction for " + item.getName() + ": " + prediction);
        }
    }
}

private PredictionInput createPredictionInput(FarmItem item) {
    PredictionInput input = new PredictionInput();
    input.addFeature("price", item.getPrice());
    input.addFeature("size", item.getLength() * item.getWidth());
    return input;
}
class DataPoint {
    private Map<String, Double> features = new HashMap<>();

    void addFeature(String name, double value) {
        features.put(name, value);
    }

    Map<String, Double> getFeatures() {
        return features;
    }
}

class PredictionResult {
    private String predictionDetails;

    public PredictionResult(String predictionDetails) {
        this.predictionDetails = predictionDetails;
    }

    public String getPredictionDetails() {
        return predictionDetails;
    }

    public void setPredictionDetails(String predictionDetails) {
        this.predictionDetails = predictionDetails;
    }

    @Override
    public String toString() {
        return "PredictionResult{" +
                "predictionDetails='" + predictionDetails + '\'' +
                '}';
    }
}

class PredictionInput {
    private Map<String, Double> features = new HashMap<>();

    public void addFeature(String name, double value) {
        features.put(name, value);
    }

    public Map<String, Double> getFeatures() {
        return features;
    }
}

class PredictiveModel {
    private Map<String, Double> modelParameters = new HashMap<>();
    public PredictiveModel(Map<String, Double> modelParameters) {
        this.modelParameters = modelParameters;
    }
    public void trainModel(List<DataPoint> dataPoints) {
        for (DataPoint dataPoint : dataPoints) {
            System.out.println("Training with data point: " + dataPoint.getFeatures());
        }
    }

    public PredictionResult predict(PredictionInput input) {
        double predictionValue = modelParameters.getOrDefault("parameter1", 0.0) * input.getFeatures().getOrDefault("feature1", 0.0);

        String predictionDetails = "Predicted value: " + predictionValue;

        return new PredictionResult(predictionDetails);
    }
}
@FXML
private LineChart<String, Number> farmStatisticsChart;
@FXML
private TableView<FarmItem> farmItemsTable;
@FXML
private Button runAnalysisButton;
@FXML
private Label statusLabel;

private void setupAdvancedUIComponents() {
    setupFarmStatisticsChart();
    setupFarmItemsTable();
}

private void setupFarmStatisticsChart() {
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    farmStatisticsChart.setTitle("Farm Statistics");
    xAxis.setLabel("Month");
    yAxis.setLabel("Value");
    LineChart.Series<String, Number> series = new LineChart.Series<>();
    series.setName("Data Series");
    // Add data points to the series
    series.getData().add(new XYChart.Data<>("Jan", 10));
    series.getData().add(new XYChart.Data<>("Feb", 20));
    farmStatisticsChart.getData().add(series);
}

private void setupFarmItemsTable() {
    TableColumn<FarmItem, String> nameColumn = new TableColumn<>("Name");
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
    TableColumn<FarmItem, Number> priceColumn = new TableColumn<>("Price");
    priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    farmItemsTable.getColumns().addAll(nameColumn, priceColumn);
    farmItemsTable.setItems(farmComponents);
}

@FXML
private void handleRunAnalysisButtonAction(ActionEvent event) {
    runDataAnalysisAndDisplayResults();
}

private void runDataAnalysisAndDisplayResults() {
    performDataAnalysis();
    updateFarmStatisticsChart();
    statusLabel.setText("Analysis Completed");
}

private void updateFarmStatisticsChart() {
    farmStatisticsChart.getData().clear();
    LineChart.Series<String, Number> series = new LineChart.Series<>();
    series.setName("New Data Series");
    series.getData().add(new XYChart.Data<>("Mar", 30));
    series.getData().add(new XYChart.Data<>("Apr", 25));
    farmStatisticsChart.getData().add(series);
}


}

package com.mrfindroom.ui;

import com.mrfindroom.data.RoomData;
import com.mrfindroom.model.Room;
import com.mrfindroom.service.RoomFilterService;
import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.*;

/**
 * Màn hình tìm phòng — tương đương timphong.html
 */
public class MainView extends BorderPane {

    private final RoomFilterService filterService = new RoomFilterService();

    // Filter state
    private final Set<String> activePriceKeys   = new LinkedHashSet<>();
    private final Set<String> activeAmenityKeys = new LinkedHashSet<>();

    // UI references
    private final GridPane roomGrid;
    private final Label resultLabel;
    private final HBox resultBar;
    private SmartChatPanel chatPanel;
    private boolean chatVisible = false;
    private TextField searchFieldRef;
    private Label smartBtnLabel;

    public MainView() {
        getStyleClass().add("main-view");

        // ══ TOP BAR ══
        HBox topBar = buildTopBar();

        // ══ FILTER SECTION ══
        VBox filterSection = buildFilterSection();

        // ══ RESULT BAR ══
        resultLabel = new Label();
        resultLabel.getStyleClass().add("result-label");
        resultBar = new HBox(8, resultLabel);
        resultBar.getStyleClass().add("result-bar");
        resultBar.setPadding(new Insets(6, 16, 6, 16));
        resultBar.setVisible(false);
        resultBar.setManaged(false);

        VBox leftPanel = new VBox(0, topBar, filterSection, resultBar);

        // ══ ROOM GRID ══
        roomGrid = new GridPane();
        roomGrid.getStyleClass().add("room-grid");
        roomGrid.setHgap(16);
        roomGrid.setVgap(16);
        roomGrid.setPadding(new Insets(16));

        ScrollPane gridScroll = new ScrollPane(roomGrid);
        gridScroll.setFitToWidth(true);
        gridScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        gridScroll.getStyleClass().add("room-scroll");

        // ══ CHAT PANEL ══
        chatPanel = new SmartChatPanel(this::displayFilteredRooms);
        chatPanel.setVisible(false);
        chatPanel.setManaged(false);

        HBox contentArea = new HBox(0, gridScroll, chatPanel);
        HBox.setHgrow(gridScroll, Priority.ALWAYS);

        VBox center = new VBox(0, leftPanel, contentArea);
        VBox.setVgrow(contentArea, Priority.ALWAYS);
        setCenter(center);

        // Load phòng ban đầu
        displayRooms(RoomData.ALL_ROOMS);
    }

    private HBox buildTopBar() {
        Label logo = new Label("🏠 MR-FindRoom");
        logo.getStyleClass().add("logo-label");

        searchFieldRef = new TextField();
        searchFieldRef.getStyleClass().add("search-field");
        searchFieldRef.setPromptText("Tìm theo quận, tên phòng, số điện thoại, tiện nghi...");
        searchFieldRef.textProperty().addListener((obs, o, n) -> filterRooms());
        HBox.setHgrow(searchFieldRef, Priority.ALWAYS);

        Button searchBtn = new Button("🔍 Tìm kiếm");
        searchBtn.getStyleClass().add("btn-search");
        searchBtn.setOnAction(e -> filterRooms());

        Button smartBtn = new Button("✨ Gợi Ý AI");
        smartBtn.getStyleClass().add("btn-smart");
        smartBtn.setOnAction(e -> toggleChat());

        Button resetBtn = new Button("✕ Xoá lọc");
        resetBtn.getStyleClass().add("btn-reset");
        resetBtn.setOnAction(e -> resetFilter());

        HBox row = new HBox(10, logo, searchFieldRef, searchBtn, smartBtn, resetBtn);
        row.getStyleClass().add("top-bar");
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(12, 16, 12, 16));
        return row;
    }

    private VBox buildFilterSection() {
        // ── Price chips ──
        Label priceLabel = new Label("Giá thuê:");
        priceLabel.getStyleClass().add("filter-label");

        FlowPane priceChips = new FlowPane(8, 6);
        priceChips.getChildren().add(makeAllChip("Tất cả", "price", priceChips));
        for (String label : RoomFilterService.PRICE_RANGES.keySet()) {
            priceChips.getChildren().add(makePriceChip(label, priceChips));
        }

        HBox priceRow = new HBox(10, priceLabel, priceChips);
        priceRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(priceChips, Priority.ALWAYS);

        // ── Amenity chips ──
        Label amenityLabel = new Label("Tiện nghi:");
        amenityLabel.getStyleClass().add("filter-label");

        FlowPane amenityChips = new FlowPane(8, 6);
        amenityChips.getChildren().add(makeAllChip("Tất cả", "amenity", amenityChips));
        for (Map.Entry<String, String> e : RoomFilterService.AMENITY_LABELS.entrySet()) {
            amenityChips.getChildren().add(makeAmenityChip(e.getKey(), e.getValue(), amenityChips));
        }

        HBox amenityRow = new HBox(10, amenityLabel, amenityChips);
        amenityRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(amenityChips, Priority.ALWAYS);

        VBox section = new VBox(10, priceRow, amenityRow);
        section.getStyleClass().add("filter-section");
        section.setPadding(new Insets(10, 16, 10, 16));
        return section;
    }

    private Button makeAllChip(String text, String type, FlowPane group) {
        Button chip = new Button(text);
        chip.getStyleClass().addAll("chip", "chip-all", "active");
        chip.setOnAction(e -> {
            if (type.equals("price")) activePriceKeys.clear();
            else                      activeAmenityKeys.clear();
            group.getChildren().forEach(n -> {
                if (n instanceof Button b) b.getStyleClass().remove("active");
            });
            chip.getStyleClass().add("active");
            filterRooms();
        });
        return chip;
    }

    private Button makePriceChip(String label, FlowPane group) {
        Button chip = new Button(label);
        chip.getStyleClass().add("chip");
        chip.setOnAction(e -> { toggleChipState(chip, label, activePriceKeys, group); filterRooms(); });
        return chip;
    }

    private Button makeAmenityChip(String key, String label, FlowPane group) {
        Button chip = new Button(label);
        chip.getStyleClass().add("chip");
        chip.setOnAction(e -> { toggleChipState(chip, key, activeAmenityKeys, group); filterRooms(); });
        return chip;
    }

    private void toggleChipState(Button chip, String key, Set<String> activeSet, FlowPane group) {
        group.getChildren().stream()
            .filter(n -> n instanceof Button b && b.getStyleClass().contains("chip-all"))
            .forEach(n -> ((Button) n).getStyleClass().remove("active"));

        if (activeSet.contains(key)) {
            activeSet.remove(key);
            chip.getStyleClass().remove("active");
            if (activeSet.isEmpty()) {
                group.getChildren().stream()
                    .filter(n -> n instanceof Button b && b.getStyleClass().contains("chip-all"))
                    .forEach(n -> ((Button) n).getStyleClass().add("active"));
            }
        } else {
            activeSet.add(key);
            chip.getStyleClass().add("active");
        }
    }

    private void filterRooms() {
        String kw = searchFieldRef != null ? searchFieldRef.getText() : "";
        List<Room> result = filterService.filter(kw, activePriceKeys, activeAmenityKeys);
        displayRooms(result);
        updateResultBar(result.size(), kw);
    }

    void displayFilteredRooms(List<Room> rooms) {
        displayRooms(rooms);
        showResultBar("Gợi ý thông minh", rooms.size());
    }

    private void displayRooms(List<Room> rooms) {
        roomGrid.getChildren().clear();
        roomGrid.getColumnConstraints().clear();

        if (rooms.isEmpty()) {
            Label empty = new Label("😕 Không tìm thấy phòng phù hợp tại Đà Nẵng.\nHãy thử thay đổi bộ lọc.");
            empty.getStyleClass().add("no-result");
            empty.setWrapText(true);
            GridPane.setColumnSpan(empty, 3);
            roomGrid.add(empty, 0, 0);
            return;
        }

        // 3 cột đều nhau
        for (int i = 0; i < 3; i++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(33.33);
            cc.setHgrow(Priority.ALWAYS);
            roomGrid.getColumnConstraints().add(cc);
        }

        int col = 0, row = 0;
        for (Room r : rooms) {
            RoomCard card = new RoomCard(r);
            card.setMaxWidth(Double.MAX_VALUE);
            FadeTransition ft = new FadeTransition(Duration.millis(200), card);
            ft.setFromValue(0); ft.setToValue(1); ft.play();
            roomGrid.add(card, col, row);
            col++;
            if (col == 3) { col = 0; row++; }
        }
    }

    private void updateResultBar(int count, String keyword) {
        boolean hasFilter = (keyword != null && !keyword.isBlank())
            || !activePriceKeys.isEmpty() || !activeAmenityKeys.isEmpty();
        if (!hasFilter) {
            resultBar.setVisible(false);
            resultBar.setManaged(false);
            return;
        }
        showResultBar(null, count);
    }

    private void showResultBar(String suffix, int count) {
        resultBar.setVisible(true);
        resultBar.setManaged(true);
        String text = "Tìm thấy " + count + " phòng tại Đà Nẵng";
        if (suffix != null) text += " · " + suffix;
        resultLabel.setText(text);
    }

    private void toggleChat() {
        chatVisible = !chatVisible;
        chatPanel.setVisible(chatVisible);
        chatPanel.setManaged(chatVisible);
    }

    private void resetFilter() {
        if (searchFieldRef != null) searchFieldRef.clear();
        activePriceKeys.clear();
        activeAmenityKeys.clear();
        if (getScene() != null && getScene().getRoot() != null) {
            getScene().getRoot().lookupAll(".chip").forEach(n -> {
                if (n instanceof Button b) {
                    b.getStyleClass().remove("active");
                    if (b.getStyleClass().contains("chip-all")) b.getStyleClass().add("active");
                }
            });
        }
        resultBar.setVisible(false);
        resultBar.setManaged(false);
        displayRooms(RoomData.ALL_ROOMS);
    }
}
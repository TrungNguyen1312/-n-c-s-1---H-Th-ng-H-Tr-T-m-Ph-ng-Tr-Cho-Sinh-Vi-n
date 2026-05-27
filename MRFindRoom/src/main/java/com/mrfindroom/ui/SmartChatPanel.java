package com.mrfindroom.ui;

import javafx.scene.Parent;
import com.mrfindroom.model.Room;
import com.mrfindroom.model.SmartFilter;
import com.mrfindroom.service.GeminiService;
import com.mrfindroom.service.RoomFilterService;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Consumer;

/**
 * Chat panel "Gợi ý thông minh" — tương đương #smartChat trong HTML
 */
public class SmartChatPanel extends VBox {

    private final VBox messagesBox = new VBox(10);
    private final TextField inputField = new TextField();
    private final GeminiService geminiService = new GeminiService();
    private final RoomFilterService filterService = new RoomFilterService();
    private final Consumer<List<Room>> onFilterResult;

    public SmartChatPanel(Consumer<List<Room>> onFilterResult) {
        super(0);
        this.onFilterResult = onFilterResult;
        getStyleClass().add("smart-panel");
        setMinWidth(320);
        setMaxWidth(360);

        // ── Header ──
        HBox header = new HBox();
        header.getStyleClass().add("smart-header");
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(12, 16, 12, 16));
        Label title = new Label("✨ Tìm phòng thông minh");
        title.getStyleClass().add("smart-title");
        HBox.setHgrow(title, Priority.ALWAYS);
        header.getChildren().add(title);

        // ── Messages area ──
        messagesBox.setPadding(new Insets(12));
        ScrollPane scrollPane = new ScrollPane(messagesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.getStyleClass().add("chat-scroll");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        // ── Input area ──
        HBox inputArea = new HBox(8);
        inputArea.setPadding(new Insets(10, 12, 10, 12));
        inputArea.getStyleClass().add("smart-input-bar");
        inputArea.setAlignment(Pos.CENTER);

        inputField.setPromptText("Mô tả nhu cầu của bạn...");
        inputField.getStyleClass().add("smart-input");
        HBox.setHgrow(inputField, Priority.ALWAYS);
        inputField.setOnAction(e -> sendMessage());

        Button sendBtn = new Button("Gửi");
        sendBtn.getStyleClass().add("btn-send");
        sendBtn.setOnAction(e -> sendMessage());

        inputArea.getChildren().addAll(inputField, sendBtn);
        getChildren().addAll(header, scrollPane, inputArea);

        // Tin nhắn chào mặc định
        addBotMessage("Xin chào! Bạn muốn tìm phòng ở đâu tại Đà Nẵng? Ví dụ: \"Tôi cần phòng dưới 2 triệu ở Ngũ Hành Sơn, có wifi và giữ xe\"");
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (text.isBlank()) return;
        addUserMessage(text);
        inputField.clear();
        inputField.setDisable(true);

        Label loading = addBotMessage("⏳ Đang phân tích...");

        Thread.ofVirtual().start(() -> {
            try {
                GeminiService.GeminiResult result = geminiService.query(text);
                Platform.runLater(() -> {
                    removeMessage(loading);
                    if (result.hasFilter()) {
                        List<Room> rooms = filterService.applySmartFilter(result.filter());
                        result.filter().count = rooms.size();
                        onFilterResult.accept(rooms);
                        String note = rooms.isEmpty()
                            ? "Không tìm thấy phòng phù hợp 😕"
                            : "→ Hiển thị " + rooms.size() + " phòng bên dưới ↓";
                        addBotMessage(result.friendlyReply() + "\n" + note);
                    } else {
                        addBotMessage(result.friendlyReply());
                    }
                    inputField.setDisable(false);
                    inputField.requestFocus();
                });
            } catch (Exception ex) {
                // Fallback về parser thủ công
                SmartFilter sf = filterService.parseSmartQuery(text);
                List<Room> rooms = filterService.applySmartFilter(sf);
                sf.count = rooms.size();
                Platform.runLater(() -> {
                    removeMessage(loading);
                    onFilterResult.accept(rooms);
                    String msg = rooms.isEmpty()
                        ? "😕 Không tìm được phòng nào khớp. Bạn thử mô tả lại nhé!"
                        : "✅ Tìm được " + rooms.size() + " phòng. Kết quả hiển thị bên dưới!";
                    addBotMessage(msg + "\n(Offline – không kết nối AI)");
                    inputField.setDisable(false);
                    inputField.requestFocus();
                });
            }
        });
    }

    private Label addUserMessage(String text) {
        Label msg = new Label(text);
        msg.getStyleClass().addAll("chat-msg", "msg-user");
        msg.setWrapText(true);
        msg.setMaxWidth(260);

        HBox row = new HBox(msg);
        row.setAlignment(Pos.CENTER_RIGHT);
        messagesBox.getChildren().add(row);
        scrollToBottom();
        return msg;
    }

    private Label addBotMessage(String text) {
        Label msg = new Label(text);
        msg.getStyleClass().addAll("chat-msg", "msg-bot");
        msg.setWrapText(true);
        msg.setMaxWidth(270);

        HBox row = new HBox(msg);
        row.setAlignment(Pos.CENTER_LEFT);
        messagesBox.getChildren().add(row);
        scrollToBottom();
        return msg;
    }

    private void removeMessage(Label msg) {
        messagesBox.getChildren().removeIf(node ->
            node instanceof HBox hbox && hbox.getChildren().contains(msg));
    }

    private void scrollToBottom() {
        Platform.runLater(() -> {
            Parent p1 = messagesBox.getParent();
if (p1 == null) return;
Parent p2 = p1.getParent();
if (!(p2 instanceof ScrollPane sp)) return;
sp.setVvalue(1.0);
        });
    }
}

package com.mrfindroom.ui;

import com.mrfindroom.model.Room;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.awt.Desktop;
import java.net.URI;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Card hiển thị thông tin 1 phòng — tương đương .room-card trong HTML/CSS
 */
public class RoomCard extends VBox {

    private static final NumberFormat VND = NumberFormat.getNumberInstance(Locale.forLanguageTag("vi-VN"));

    public RoomCard(Room room) {
        super(0);
        getStyleClass().add("room-card");
        setMaxWidth(Double.MAX_VALUE);

        // ── Ảnh phòng ──
        ImageView imageView = new ImageView();
        imageView.setFitWidth(Double.MAX_VALUE);
        imageView.setFitHeight(200);
        imageView.setFitWidth(USE_COMPUTED_SIZE);
        imageView.setPreserveRatio(false);
        imageView.setSmooth(true);

        // Placeholder gradient khi ảnh đang load
        StackPane imgWrapper = new StackPane(imageView);
        imgWrapper.setStyle("-fx-background-color: #1e293b; -fx-pref-height: 200;");
        imgWrapper.setPrefHeight(200);
        VBox.setVgrow(imgWrapper, Priority.NEVER);

        // Load ảnh async
        try {
            Image img = new Image(room.getImageUrl(), true);
            imageView.setImage(img);
            imageView.fitWidthProperty().bind(imgWrapper.widthProperty());
        } catch (Exception ignored) {}

        // ── Nội dung phòng ──
        VBox info = new VBox(6);
        info.setPadding(new Insets(14, 16, 14, 16));

        // Tên phòng
        Label nameLabel = new Label(room.getName());
        nameLabel.getStyleClass().add("card-name");
        nameLabel.setWrapText(true);

        // Địa chỉ
        Label addrLabel = new Label("📍 " + room.getAddress());
        addrLabel.getStyleClass().add("card-addr");
        addrLabel.setWrapText(true);

        // Giá
        Label priceLabel = new Label(VND.format(room.getPrice()) + " VNĐ/tháng");
        priceLabel.getStyleClass().add("card-price");

        // Đặt cọc + SĐT
        Label depositLabel = new Label("Đặt cọc: " + room.getDeposit() + " tháng");
        depositLabel.getStyleClass().add("card-meta");

        // Mô tả
        Label descLabel = new Label(room.getDescription());
        descLabel.getStyleClass().add("card-desc");
        descLabel.setWrapText(true);

        // Tags tiện nghi
        FlowPane tags = new FlowPane(6, 4);
        tags.setPadding(new Insets(4, 0, 0, 0));
        if (room.isWifi())           tags.getChildren().add(makeTag("📶 Wifi"));
        if (room.isAirConditioner()) tags.getChildren().add(makeTag("❄️ Máy lạnh"));
        if (room.isParking())        tags.getChildren().add(makeTag("🛵 Giữ xe"));
        if (room.isHotWater())       tags.getChildren().add(makeTag("🚿 Nóng lạnh"));
        if (room.isOwnToilet())      tags.getChildren().add(makeTag("🚽 WC riêng"));
        if (room.isBalcony())        tags.getChildren().add(makeTag("🌿 Ban công"));
        if (room.isNearSchool())     tags.getChildren().add(makeTag("🏫 Gần trường"));

        // Nút liên hệ
        Button callBtn = new Button("📞 Gọi ngay: " + room.getPhone());
        callBtn.getStyleClass().add("call-btn");
        callBtn.setMaxWidth(Double.MAX_VALUE);
        callBtn.setCursor(Cursor.HAND);
        callBtn.setOnAction(e -> {
            // Thử mở dialer nếu hệ thống hỗ trợ, nếu không thì copy
            try {
                Desktop.getDesktop().browse(new URI("tel:" + room.getPhone()));
            } catch (Exception ex) {
                // Copy to clipboard
                javafx.scene.input.Clipboard clipboard = javafx.scene.input.Clipboard.getSystemClipboard();
                javafx.scene.input.ClipboardContent cc = new javafx.scene.input.ClipboardContent();
                cc.putString(room.getPhone());
                clipboard.setContent(cc);
                callBtn.setText("✅ Đã copy: " + room.getPhone());
            }
        });

        info.getChildren().addAll(nameLabel, addrLabel, priceLabel, depositLabel, descLabel, tags, callBtn);
        getChildren().addAll(imgWrapper, info);
    }

    private Label makeTag(String text) {
        Label tag = new Label(text);
        tag.getStyleClass().add("tag");
        return tag;
    }
}

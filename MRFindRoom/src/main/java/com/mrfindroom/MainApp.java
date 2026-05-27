package com.mrfindroom;

import com.mrfindroom.ui.HomeView;
import com.mrfindroom.ui.MainView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Điểm khởi đầu ứng dụng MR-FindRoom Desktop
 */
public class MainApp extends Application {

    private BorderPane root;
    private HomeView homeView;
    private MainView mainView;

    // Nav buttons
    private Button navHome, navSearch, navPost, navMsg, navProfile;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();

        // Khởi tạo các màn hình
        homeView = new HomeView(this::goToSearch);
        mainView = new MainView();

        // Bottom nav bar
        HBox navBar = buildNavBar();
        root.setBottom(navBar);

        // Bắt đầu ở trang chủ
        showHome();

        Scene scene = new Scene(root, 1100, 720);
        scene.getStylesheets().add(
    Objects.requireNonNull(getClass().getResource("/light-theme.css")).toExternalForm()
);

        stage.setTitle("MR-FindRoom – Tìm Phòng Trọ Sinh Viên Đà Nẵng");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setMaximized(true);
        stage.show();
    }

    private HBox buildNavBar() {
        navHome    = makeNavBtn("🏠 Trang chủ");
        navSearch  = makeNavBtn("🔍 Tìm phòng");
        navPost    = makeNavBtn("➕ Đăng tin");
        navMsg     = makeNavBtn("💬 Tin nhắn");
        navProfile = makeNavBtn("👤 Tôi");

        navHome.setOnAction(e -> { showHome(); setActiveNav(navHome); });
        navSearch.setOnAction(e -> { showSearch(null); setActiveNav(navSearch); });
        navPost.setOnAction(e -> showAlert("Tính năng Đăng tin đang được phát triển! 🚀"));
        navMsg.setOnAction(e -> showAlert("Tính năng Tin nhắn đang được phát triển! 💬"));
        navProfile.setOnAction(e -> showAlert("Tính năng hồ sơ đang được phát triển! 👤"));

        HBox nav = new HBox();
        nav.getStyleClass().add("bottom-nav");
        nav.setAlignment(Pos.CENTER);

        for (Button b : new Button[]{navHome, navSearch, navPost, navMsg, navProfile}) {
            HBox.setHgrow(b, Priority.ALWAYS);
            b.setMaxWidth(Double.MAX_VALUE);
            nav.getChildren().add(b);
        }
        return nav;
    }

    private Button makeNavBtn(String text) {
        Button btn = new Button(text);
        btn.getStyleClass().add("nav-btn");
        return btn;
    }

    private void setActiveNav(Button active) {
        for (Button b : new Button[]{navHome, navSearch, navPost, navMsg, navProfile}) {
            b.getStyleClass().remove("active");
        }
        active.getStyleClass().add("active");
    }

    private void showHome() {
        root.setCenter(homeView);
        setActiveNav(navHome);
    }

    private void goToSearch(String query) {
        showSearch(query);
        setActiveNav(navSearch);
    }

    private void showSearch(String query) {
        root.setCenter(mainView);
        setActiveNav(navSearch);
        // Nếu có query từ trang chủ, tự động tìm
        // (Có thể mở rộng: truyền query vào MainView)
    }

    private void showAlert(String msg) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(
            javafx.scene.control.Alert.AlertType.INFORMATION, msg);
        alert.setTitle("MR-FindRoom");
        alert.setHeaderText(null);
        // Apply dark style to dialog
        alert.getDialogPane().getScene().getRoot().setStyle(
            "-fx-base: #1e293b; -fx-background: #0f172a; -fx-control-inner-background: #1e293b;");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

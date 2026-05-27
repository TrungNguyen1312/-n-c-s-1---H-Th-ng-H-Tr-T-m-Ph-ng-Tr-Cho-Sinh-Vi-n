package com.mrfindroom.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;

import java.util.function.Consumer;

/**
 * Màn hình trang chủ — tương đương home.html
 */
public class HomeView extends BorderPane {

    private final Consumer<String> onSearch;

    public HomeView(Consumer<String> onSearch) {
        this.onSearch = onSearch;
        getStyleClass().add("home-view");

        ScrollPane scroll = new ScrollPane(buildContent());
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.getStyleClass().add("home-scroll");
        setCenter(scroll);
    }

    private VBox buildContent() {
        VBox root = new VBox(0);
        root.getStyleClass().add("home-content");

        // ── Hero ──
        root.getChildren().add(buildHero());

        // ── Quick Search ──
        root.getChildren().add(buildQuickSearch());

        // ── Stats ──
        root.getChildren().add(buildStats());

        // ── Features ──
        root.getChildren().add(buildFeatures());

        // ── How it works ──
        root.getChildren().add(buildHowItWorks());

        // ── CTA ──
        root.getChildren().add(buildCTA());

        return root;
    }

    private VBox buildHero() {
        Label badge = new Label("🏠 Nền tảng tìm trọ #1 cho sinh viên Đà Nẵng");
        badge.getStyleClass().add("hero-badge");

        Label title = new Label("Phòng trọ Đà Nẵng");
        title.getStyleClass().add("hero-title");
        title.setTextAlignment(TextAlignment.CENTER);

        Label accent = new Label("nhanh · đúng · rẻ");
        accent.getStyleClass().add("hero-accent");

        Label desc = new Label("Hàng trăm phòng trọ uy tín tại Đà Nẵng được kiểm duyệt kỹ lưỡng,\nlọc theo giá, tiện nghi và từng quận chỉ trong vài giây.");
        desc.getStyleClass().add("hero-desc");
        desc.setTextAlignment(TextAlignment.CENTER);
        desc.setWrapText(true);

        Button findBtn = new Button("🔍 Tìm phòng ngay");
        findBtn.getStyleClass().add("btn-primary");
        findBtn.setOnAction(e -> onSearch.accept(""));

        Button aiBtn = new Button("✨ Gợi ý thông minh");
        aiBtn.getStyleClass().add("btn-outline");
        aiBtn.setOnAction(e -> onSearch.accept(""));

        HBox actions = new HBox(12, findBtn, aiBtn);
        actions.setAlignment(Pos.CENTER);

        VBox hero = new VBox(16, badge, title, accent, desc, actions);
        hero.getStyleClass().add("hero-section");
        hero.setAlignment(Pos.CENTER);
        hero.setPadding(new Insets(60, 40, 40, 40));
        return hero;
    }

    private HBox buildQuickSearch() {
        TextField qs = new TextField();
        qs.getStyleClass().add("qs-field");
        qs.setPromptText("VD: Ngũ Hành Sơn, wifi, dưới 2 triệu...");
        qs.setOnAction(e -> onSearch.accept(qs.getText()));
        HBox.setHgrow(qs, Priority.ALWAYS);

        Button btn = new Button("Tìm ngay");
        btn.getStyleClass().add("btn-primary");
        btn.setOnAction(e -> onSearch.accept(qs.getText()));

        HBox box = new HBox(10, qs, btn);
        box.getStyleClass().add("quick-search");
        box.setPadding(new Insets(0, 40, 24, 40));
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox buildStats() {
        HBox stats = new HBox();
        stats.getStyleClass().add("stats-bar");
        stats.setAlignment(Pos.CENTER);
        stats.setPadding(new Insets(20, 40, 20, 40));
        stats.getChildren().addAll(
            makeStat("300+", "Phòng tại Đà Nẵng"),
            makeStatDivider(),
            makeStat("8", "Quận / Huyện"),
            makeStatDivider(),
            makeStat("98%", "Hài lòng")
        );
        return stats;
    }

    private VBox makeStat(String num, String label) {
        Label n = new Label(num);
        n.getStyleClass().add("stat-num");
        Label l = new Label(label);
        l.getStyleClass().add("stat-label");
        VBox v = new VBox(4, n, l);
        v.setAlignment(Pos.CENTER);
        v.setPadding(new Insets(0, 32, 0, 32));
        return v;
    }

    private Label makeStatDivider() {
        Label d = new Label("|");
        d.getStyleClass().add("stat-divider");
        return d;
    }

    private VBox buildFeatures() {
        Label sectionTitle = new Label("Tất cả trong một nền tảng");
        sectionTitle.getStyleClass().add("section-title");

        Label sectionDesc = new Label("Được xây dựng đặc biệt cho sinh viên Đà Nẵng — đơn giản, nhanh và miễn phí.");
        sectionDesc.getStyleClass().add("section-desc");
        sectionDesc.setWrapText(true);
        sectionDesc.setTextAlignment(TextAlignment.CENTER);

        String[][] features = {
            {"🔍", "Tìm kiếm thông minh", "Gõ bất kỳ điều bạn cần — quận, tiện nghi, giá — hệ thống tự hiểu và lọc kết quả phù hợp nhất tại Đà Nẵng."},
            {"✨", "Gợi ý AI", "Mô tả nhu cầu bằng lời tự nhiên, AI phân tích và đề xuất danh sách phòng phù hợp tại đúng khu vực bạn cần."},
            {"🛡️", "Tin đăng uy tín", "Mỗi phòng đều được kiểm duyệt địa chỉ và tiện nghi thực tế trước khi hiển thị trên nền tảng."},
            {"📶", "Lọc đa tiêu chí", "Lọc theo giá, wifi, máy lạnh, nóng lạnh, WC riêng, giữ xe, gần trường — tất cả chỉ một cú click."},
            {"📞", "Liên hệ trực tiếp", "Số điện thoại chủ nhà hiển thị ngay trên thẻ phòng, gọi ngay không qua trung gian, không mất phí."},
            {"💬", "Tin nhắn nhanh", "Nhắn tin hỏi thăm phòng trọ ngay trong ứng dụng, lưu lịch sử hội thoại tiện lợi và nhanh chóng."}
        };

        var grid = new javafx.scene.layout.GridPane();
        grid.setHgap(16); grid.setVgap(16);
        grid.setPadding(new Insets(24, 40, 24, 40));
        for (int i = 0; i < features.length; i++) {
            grid.add(makeFeatureCard(features[i][0], features[i][1], features[i][2]), i % 3, i / 3);
            javafx.scene.layout.GridPane.setHgrow(grid.getChildren().get(i), Priority.ALWAYS);
        }

        VBox section = new VBox(10, sectionTitle, sectionDesc, grid);
        section.getStyleClass().add("section");
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(40, 0, 20, 0));
        return section;
    }

    private VBox makeFeatureCard(String icon, String title, String desc) {
        Label iconL = new Label(icon);
        iconL.getStyleClass().add("feature-icon");
        Label titleL = new Label(title);
        titleL.getStyleClass().add("feature-title");
        Label descL = new Label(desc);
        descL.getStyleClass().add("feature-desc");
        descL.setWrapText(true);
        VBox card = new VBox(8, iconL, titleL, descL);
        card.getStyleClass().add("feature-card");
        card.setPadding(new Insets(18));
        return card;
    }

    private VBox buildHowItWorks() {
        Label title = new Label("Chỉ 3 bước tìm phòng tại Đà Nẵng");
        title.getStyleClass().add("section-title");

        String[][] steps = {
            {"1", "Nhập nhu cầu của bạn", "Gõ quận muốn thuê tại Đà Nẵng, mức giá hoặc dùng AI để mô tả tự nhiên."},
            {"2", "Lọc & so sánh phòng", "Dùng bộ lọc tiện nghi và giá để thu hẹp kết quả. Xem ảnh, địa chỉ và thông tin đầy đủ."},
            {"3", "Liên hệ & ký hợp đồng", "Gọi thẳng cho chủ nhà. Đặt lịch xem phòng và dọn vào sớm nhất có thể!"}
        };

        VBox stepsBox = new VBox(16);
        stepsBox.setPadding(new Insets(24, 40, 24, 40));
        for (String[] s : steps) {
            HBox step = new HBox(16);
            step.getStyleClass().add("step");
            step.setAlignment(Pos.TOP_LEFT);

            Label num = new Label(s[0]);
            num.getStyleClass().add("step-num");

            Label stepTitle = new Label(s[1]);
            stepTitle.getStyleClass().add("step-title");
            Label stepDesc = new Label(s[2]);
            stepDesc.getStyleClass().add("step-desc");
            stepDesc.setWrapText(true);
            VBox body = new VBox(4, stepTitle, stepDesc);
            HBox.setHgrow(body, Priority.ALWAYS);

            step.getChildren().addAll(num, body);
            stepsBox.getChildren().add(step);
        }

        VBox section = new VBox(12, title, stepsBox);
        section.getStyleClass().add("section");
        section.setAlignment(Pos.CENTER);
        section.setPadding(new Insets(20, 0, 20, 0));
        return section;
    }

    private VBox buildCTA() {
        Label title = new Label("Tìm phòng trọ Đà Nẵng ngay hôm nay");
        title.getStyleClass().add("cta-title");
        title.setWrapText(true);
        title.setTextAlignment(TextAlignment.CENTER);

        Label sub = new Label("Miễn phí hoàn toàn · Không cần đăng ký · Kết quả tức thì");
        sub.getStyleClass().add("cta-sub");

        Button btn = new Button("🔍 Tìm phòng ngay →");
        btn.getStyleClass().add("btn-white");
        btn.setOnAction(e -> onSearch.accept(""));

        VBox cta = new VBox(14, title, sub, btn);
        cta.getStyleClass().add("cta-section");
        cta.setAlignment(Pos.CENTER);
        cta.setPadding(new Insets(50, 60, 50, 60));
        cta.setMaxWidth(800);

        VBox wrapper = new VBox(cta);
        wrapper.setAlignment(Pos.CENTER);
        wrapper.setPadding(new Insets(20, 40, 40, 40));
        return wrapper;
    }
}

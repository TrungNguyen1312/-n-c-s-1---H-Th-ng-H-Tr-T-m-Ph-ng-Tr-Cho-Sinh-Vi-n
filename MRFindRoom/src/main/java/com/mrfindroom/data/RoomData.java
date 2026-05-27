package com.mrfindroom.data;

import com.mrfindroom.model.Room;
import java.util.List;

/**
 * Dữ liệu 9 phòng trọ — chuyển từ mảng rooms[] trong script.js
 */
public class RoomData {

    public static final List<Room> ALL_ROOMS = List.of(
        new Room(1,
            "Phòng Trọ Hải Châu", "Hải Châu",
            "15 Trần Phú, Hải Châu, Đà Nẵng",
            2_500_000L, 1,
            "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?w=400",
            "0901234567",
            true, true, true, true, true, true, false,
            "Phòng thoáng mát, có cửa sổ, gần ĐH Kinh Tế Đà Nẵng. Điện 3.5k/số, nước 8k/khối. Không chung chủ."
        ),
        new Room(2,
            "Phòng Trọ Ngũ Hành Sơn", "Ngũ Hành Sơn",
            "87 Lê Văn Hiến, Ngũ Hành Sơn, Đà Nẵng",
            1_800_000L, 1,
            "https://images.unsplash.com/photo-1494526585095-c41746248156?w=400",
            "0912345678",
            true, true, false, true, false, false, false,
            "Gần ĐH FPT Đà Nẵng và Làng Đại học. Cửa tự do 24/7, khu vực yên tĩnh, không ngập lụt."
        ),
        new Room(3,
            "Phòng Trọ Thanh Khê", "Thanh Khê",
            "204 Điện Biên Phủ, Thanh Khê, Đà Nẵng",
            3_200_000L, 2,
            "https://images.unsplash.com/photo-1484154218962-a197022b5858?w=400",
            "0923456789",
            false, true, true, false, true, true, true,
            "Phòng gác lửng đầy đủ nội thất. Có ban công, cửa sổ thoáng. Gần chợ Thanh Khê, tiện đi lại."
        ),
        new Room(4,
            "Phòng Trọ Liên Chiểu", "Liên Chiểu",
            "33 Nguyễn Lương Bằng, Liên Chiểu, Đà Nẵng",
            950_000L, 1,
            "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=400",
            "0934567890",
            true, true, false, true, false, false, false,
            "Phòng nhỏ gọn giá siêu rẻ, gần ĐH Bách Khoa Đà Nẵng. Điện nước giá nhà nước. Xung quanh có nhiều quán ăn bình dân."
        ),
        new Room(5,
            "Phòng Trọ Sơn Trà", "Sơn Trà",
            "56 Ngô Quyền, Sơn Trà, Đà Nẵng",
            4_500_000L, 2,
            "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=400",
            "0945678901",
            false, true, true, true, true, true, true,
            "Phòng studio cao cấp full nội thất: giường, tủ, bàn làm việc, tủ lạnh, máy giặt. View biển, gần Bãi Mỹ Khê, an ninh tốt."
        ),
        new Room(6,
            "Phòng Trọ Cẩm Lệ", "Cẩm Lệ",
            "122 Cách Mạng Tháng 8, Cẩm Lệ, Đà Nẵng",
            2_200_000L, 1,
            "https://images.unsplash.com/photo-1536376072261-38c75010e6c9?w=400",
            "0956789012",
            true, true, true, true, true, false, false,
            "Gần ĐH Duy Tân và ĐH Đông Á. Phòng mới xây, sạch sẽ, thoáng mát. Chủ nhà thân thiện, hỗ trợ sinh viên ngoại tỉnh."
        ),
        new Room(7,
            "Phòng Trọ Ngũ Hành Sơn Cao Cấp", "Ngũ Hành Sơn",
            "38 Trường Sa, Ngũ Hành Sơn, Đà Nẵng",
            6_500_000L, 2,
            "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?w=400",
            "0967890123",
            false, true, true, true, true, true, true,
            "Căn hộ mini cao cấp gần biển Mỹ Khê, view đẹp. Đầy đủ nội thất hiện đại, thang máy, bảo vệ 24/7. Gần các resort và trung tâm thương mại."
        ),
        new Room(8,
            "Phòng Trọ Hòa Vang", "Hòa Vang",
            "75 Quốc lộ 14B, Hòa Vang, Đà Nẵng",
            1_500_000L, 1,
            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400",
            "0978901234",
            true, false, false, true, false, true, false,
            "Phòng rộng rãi thoáng mát, WC riêng khép kín. Gần ĐH Sư Phạm Đà Nẵng, chợ, tiện lợi. Không chung chủ, ra vào tự do."
        ),
        new Room(9,
            "Phòng Trọ Hải Châu Trung Tâm", "Hải Châu",
            "9 Bạch Đằng, Hải Châu, Đà Nẵng",
            3_800_000L, 2,
            "https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=400",
            "0989012345",
            true, true, true, true, true, true, false,
            "Ngay trung tâm Đà Nẵng, sát sông Hàn. Gần ĐH Kinh Tế và ĐH Ngoại Ngữ. Phòng yên tĩnh, đầy đủ tiện nghi, điện 2.5k/số."
        )
    );
}

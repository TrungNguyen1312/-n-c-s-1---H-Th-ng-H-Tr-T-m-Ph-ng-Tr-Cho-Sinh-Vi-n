const rooms = [
    {
        id: 1,
        name: "Phòng Trọ Quận 1",
        area: "Quận 1",
        address: "Kiệt 45 Nguyễn Trãi, Quận 1",
        price: 2500000,
        deposit: 1,
        image: "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85",
        phone: "0901234567",
        nearSchool: true,
        wifi: true,
        airConditioner: true,
        parking: true,
        hotWater: true,
        ownToilet: true,
        balcony: false,
        description: "Phòng thoáng mát, có cửa sổ, không chung chủ. Điện 3.5k/số, nước 8k/khối."
    },
    {
        id: 2,
        name: "Phòng Trọ Thủ Đức",
        area: "Thủ Đức",
        address: "119 Võ Văn Ngân, Thủ Đức",
        price: 1800000,
        deposit: 1,
        image: "https://images.unsplash.com/photo-1494526585095-c41746248156",
        phone: "0912345678",
        nearSchool: true,
        wifi: true,
        airConditioner: false,
        parking: true,
        hotWater: false,
        ownToilet: false,
        balcony: false,
        description: "Gần ĐH Sư Phạm Kỹ Thuật, bệnh viện. Cửa tự do, không ngập, không ẩm."
    },
    {
        id: 3,
        name: "Phòng Trọ Bình Thạnh",
        area: "Bình Thạnh",
        address: "354 Đinh Bộ Lĩnh, Bình Thạnh",
        price: 3200000,
        deposit: 2,
        image: "https://images.unsplash.com/photo-1484154218962-a197022b5858",
        phone: "0923456789",
        nearSchool: false,
        wifi: true,
        airConditioner: true,
        parking: false,
        hotWater: true,
        ownToilet: true,
        balcony: true,
        description: "Phòng gác lửng đầy đủ nội thất. Có ban công, cửa sổ thoáng mát. Hỗ trợ phí vận chuyển."
    }
];

// Tập hợp các giá trị đang được chọn (multi-select)
const activePrices    = new Set();
const activeAmenities = new Set();

// Mapping chip giá → khoảng min/max
const priceRanges = {
    "1000000":  { min: 0,       max: 1000000  },
    "2000000":  { min: 1000000, max: 2000000  },
    "3000000":  { min: 2000000, max: 3000000  },
    "5000000":  { min: 3000000, max: 5000000  },
    "7000000":  { min: 5000000, max: 7000000  },
    "99000000": { min: 7000000, max: Infinity }
};

// Mapping chip tiện nghi → field trong object room
const amenityMap = {
    wifi:     "wifi",
    ac:       "airConditioner",
    parking:  "parking",
    hotwater: "hotWater",
    toilet:   "ownToilet",
    school:   "nearSchool"
};

// Từ đồng nghĩa / từ liên quan để mở rộng tìm kiếm
const synonyms = {
    "wifi":          ["wifi", "mạng", "internet", "wi-fi"],
    "máy lạnh":      ["máy lạnh", "điều hòa", "điều hoà", "lạnh", "ac"],
    "giữ xe":        ["giữ xe", "đậu xe", "xe máy", "parking", "gửi xe"],
    "nóng lạnh":     ["nóng lạnh", "bình nóng lạnh", "water heater", "vòi nóng"],
    "wc riêng":      ["wc riêng", "toilet riêng", "nhà vệ sinh riêng", "phòng tắm riêng", "wc", "toilet"],
    "gần trường":    ["gần trường", "gần đại học", "gần trường học", "gần đh", "sinh viên"],
    "ban công":      ["ban công", "balcony", "sân thượng"],
    "rẻ":            ["rẻ", "giá rẻ", "tiết kiệm", "sinh viên"],
    "trung tâm":     ["trung tâm", "quận 1", "nội thành"],
    "nội thất":      ["nội thất", "đầy đủ", "có tủ", "có giường", "có bàn"],
    "không chung chủ": ["không chung chủ", "tự do", "riêng tư"],
    "thoáng":        ["thoáng", "thoáng mát", "cửa sổ", "thoáng đãng"],
};

const roomList = document.getElementById("roomList");

/* ══════════════════════════════════════════════
   FUZZY / BROAD SEARCH
   ══════════════════════════════════════════════ */

/**
 * Chuẩn hoá chuỗi: bỏ dấu, viết thường, trim
 */
function normalize(str) {
    return str
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/đ/g, "d")
        .trim();
}

/**
 * Mở rộng từ khoá qua bảng đồng nghĩa
 */
function expandKeyword(kw) {
    const normKw = normalize(kw);
    const expanded = new Set([normKw]);
    for (const variants of Object.values(synonyms)) {
        if (variants.some(v => normalize(v).includes(normKw) || normKw.includes(normalize(v)))) {
            variants.forEach(v => expanded.add(normalize(v)));
        }
    }
    return [...expanded];
}

/**
 * Kiểm tra một token có xuất hiện trong chuỗi haystack không
 * Hỗ trợ: khớp trực tiếp + khớp một phần số điện thoại
 */
function tokenMatches(token, haystack) {
    const normHay = normalize(haystack);
    if (normHay.includes(token)) return true;

    // Tìm kiếm số điện thoại một phần: chỉ giữ chữ số trong token và haystack
    const digitsToken = token.replace(/\D/g, "");
    if (digitsToken.length >= 3) {
        const digitsHay = haystack.replace(/\D/g, "");
        if (digitsHay.includes(digitsToken)) return true;
    }

    return false;
}

/**
 * Tính điểm phù hợp của một phòng với từ khoá (0 = không khớp)
 */
function scoreRoom(room, keyword) {
    if (!keyword) return 1; // Không có từ khoá → luôn hiện

    const searchFields = [
        room.name,
        room.area,
        room.address,
        room.description,
        room.phone,
    ].join(" ");

    // Tách từng token (hỗ trợ nhập nhiều từ)
    const tokens = keyword.toLowerCase().trim().split(/\s+/);

    // Mở rộng từng token qua từ đồng nghĩa
    const allTerms = tokens.flatMap(t => expandKeyword(t));

    let matchCount = 0;
    for (const term of allTerms) {
        if (tokenMatches(term, searchFields)) {
            matchCount++;
        }
    }

    // Nếu ít nhất 1 term khớp → hiện phòng; điểm cao hơn = ưu tiên hơn
    return matchCount;
}

/* ══════════════════════════════════════════════
   TOGGLE CHIP — "Tất cả" + multi-select
   ══════════════════════════════════════════════ */

function toggleAll(type, el) {
    const set       = type === "price" ? activePrices : activeAmenities;
    const groupId   = type === "price" ? "priceChips" : "amenityChips";
    const group     = document.getElementById(groupId);

    // Xoá tất cả lựa chọn, bật lại nút "Tất cả"
    set.clear();
    group.querySelectorAll(".chip").forEach(c => c.classList.remove("active"));
    el.classList.add("active");
    filterRooms();
}

function toggleChip(type, value, el) {
    const set     = type === "price" ? activePrices : activeAmenities;
    const groupId = type === "price" ? "priceChips" : "amenityChips";
    const group   = document.getElementById(groupId);
    const allBtn  = group.querySelector(".chip-all");

    // Tắt nút "Tất cả" khi chọn giá trị cụ thể
    if (allBtn) allBtn.classList.remove("active");

    if (set.has(value)) {
        set.delete(value);
        el.classList.remove("active");
        // Nếu không còn gì được chọn → bật lại "Tất cả"
        if (set.size === 0 && allBtn) allBtn.classList.add("active");
    } else {
        set.add(value);
        el.classList.add("active");
    }

    filterRooms();
}

/* ══════════════════════════════════════════════
   LỌC & HIỂN THỊ
   ══════════════════════════════════════════════ */
function filterRooms() {
    const raw     = document.getElementById("search").value.trim();
    const keyword = raw; // Giữ nguyên, scoreRoom sẽ xử lý

    const filtered = rooms
        .map(room => ({ room, score: scoreRoom(room, keyword) }))
        .filter(({ room, score }) => {
            if (score === 0) return false;

            // Lọc giá
            let matchPrice = true;
            if (activePrices.size > 0) {
                matchPrice = [...activePrices].some(val => {
                    const range = priceRanges[val];
                    return room.price >= range.min && room.price <= range.max;
                });
            }

            // Lọc tiện nghi
            let matchAmenity = true;
            if (activeAmenities.size > 0) {
                matchAmenity = [...activeAmenities].every(val => {
                    const field = amenityMap[val];
                    return room[field] === true;
                });
            }

            return matchPrice && matchAmenity;
        })
        // Sắp xếp theo điểm phù hợp giảm dần
        .sort((a, b) => b.score - a.score)
        .map(({ room }) => room);

    displayRooms(filtered);
    updateResultBar(filtered.length, raw);
}

/* ── HIỂN THỊ PHÒNG ── */
function displayRooms(data) {
    roomList.innerHTML = "";

    if (data.length === 0) {
        roomList.innerHTML = "<p class='no-result'>😕 Không tìm thấy phòng phù hợp. Hãy thử thay đổi bộ lọc.</p>";
        return;
    }

    data.forEach(room => {
        roomList.innerHTML += `
            <div class="room-card">
                <img src="${room.image}" alt="${room.name}"
                     onerror="this.src='https://via.placeholder.com/400x220?text=No+Image'">
                <div class="room-info">
                    <h3>${room.name}</h3>
                    <p class="address">📍 ${room.address}</p>
                    <p class="price">${room.price.toLocaleString()} VNĐ<span>/tháng</span></p>
                    <p class="deposit">
                        Đặt cọc: ${room.deposit} tháng &nbsp;|&nbsp;
                        SĐT: <a href="tel:${room.phone}">${room.phone}</a>
                    </p>
                    <p class="desc">${room.description}</p>
                    <div class="tags">
                        ${room.wifi           ? '<span class="tag">📶 Wifi</span>'       : ''}
                        ${room.airConditioner ? '<span class="tag">❄️ Máy lạnh</span>'   : ''}
                        ${room.parking        ? '<span class="tag">🛵 Giữ xe</span>'     : ''}
                        ${room.hotWater       ? '<span class="tag">🚿 Nóng lạnh</span>'  : ''}
                        ${room.ownToilet      ? '<span class="tag">🚽 WC riêng</span>'   : ''}
                        ${room.balcony        ? '<span class="tag">🌿 Ban công</span>'   : ''}
                        ${room.nearSchool     ? '<span class="tag">🏫 Gần trường</span>' : ''}
                    </div>
                </div>
            </div>
        `;
    });
}

/* ── THANH KẾT QUẢ ── */
function updateResultBar(count, keyword) {
    const bar = document.getElementById("resultBar");
    const hasFilter = keyword || activePrices.size > 0 || activeAmenities.size > 0;

    if (!hasFilter) {
        bar.style.display = "none";
        return;
    }

    bar.style.display = "flex";
    document.getElementById("resultCount").textContent = `Tìm thấy ${count} phòng`;

    const labels = [];
    if (keyword) labels.push(`"${keyword}"`);
    if (activePrices.size > 0)    labels.push(`${activePrices.size} mức giá`);
    if (activeAmenities.size > 0) labels.push(`${activeAmenities.size} tiện nghi`);
    document.getElementById("activeFiltersLabel").textContent =
        labels.length ? "· Bộ lọc: " + labels.join(", ") : "";
}

/* ── GỢI Ý THÔNG MINH ── */
function smartSuggest() {
    resetFilter();

    const suggestRooms = rooms
        .filter(room => room.price <= 3000000)
        .sort((a, b) => {
            const score = r =>
                (r.wifi ? 1 : 0) +
                (r.airConditioner ? 2 : 0) +
                (r.parking ? 1 : 0) +
                (r.hotWater ? 1 : 0) +
                (r.ownToilet ? 1 : 0) +
                (r.nearSchool ? 1 : 0);
            return score(b) - score(a);
        });

    displayRooms(suggestRooms);
}

/* ── XOÁ LỌC ── */
function resetFilter() {
    document.getElementById("search").value = "";
    activePrices.clear();
    activeAmenities.clear();

    // Reset tất cả chip, bật lại "Tất cả"
    document.querySelectorAll(".chip").forEach(c => c.classList.remove("active"));
    document.querySelectorAll(".chip-all").forEach(c => c.classList.add("active"));

    document.getElementById("resultBar").style.display = "none";
    displayRooms(rooms);
}

// Hiển thị khi load trang
displayRooms(rooms);
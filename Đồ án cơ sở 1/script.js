const rooms = [
    {
        id: 1,
        name: "Phòng Trọ Hải Châu",
        area: "Hải Châu",
        address: "15 Trần Phú, Hải Châu, Đà Nẵng",
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
        description: "Phòng thoáng mát, có cửa sổ, gần ĐH Kinh Tế Đà Nẵng. Điện 3.5k/số, nước 8k/khối. Không chung chủ."
    },
    {
        id: 2,
        name: "Phòng Trọ Ngũ Hành Sơn",
        area: "Ngũ Hành Sơn",
        address: "87 Lê Văn Hiến, Ngũ Hành Sơn, Đà Nẵng",
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
        description: "Gần ĐH FPT Đà Nẵng và Làng Đại học. Cửa tự do 24/7, khu vực yên tĩnh, không ngập lụt."
    },
    {
        id: 3,
        name: "Phòng Trọ Thanh Khê",
        area: "Thanh Khê",
        address: "204 Điện Biên Phủ, Thanh Khê, Đà Nẵng",
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
        description: "Phòng gác lửng đầy đủ nội thất. Có ban công, cửa sổ thoáng. Gần chợ Thanh Khê, tiện đi lại."
    },
    {
        id: 4,
        name: "Phòng Trọ Liên Chiểu",
        area: "Liên Chiểu",
        address: "33 Nguyễn Lương Bằng, Liên Chiểu, Đà Nẵng",
        price: 950000,
        deposit: 1,
        image: "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2",
        phone: "0934567890",
        nearSchool: true,
        wifi: true,
        airConditioner: false,
        parking: true,
        hotWater: false,
        ownToilet: false,
        balcony: false,
        description: "Phòng nhỏ gọn giá siêu rẻ, gần ĐH Bách Khoa Đà Nẵng. Điện nước giá nhà nước. Xung quanh có nhiều quán ăn bình dân."
    },
    {
        id: 5,
        name: "Phòng Trọ Sơn Trà",
        area: "Sơn Trà",
        address: "56 Ngô Quyền, Sơn Trà, Đà Nẵng",
        price: 4500000,
        deposit: 2,
        image: "https://images.unsplash.com/photo-1556909114-f6e7ad7d3136",
        phone: "0945678901",
        nearSchool: false,
        wifi: true,
        airConditioner: true,
        parking: true,
        hotWater: true,
        ownToilet: true,
        balcony: true,
        description: "Phòng studio cao cấp full nội thất: giường, tủ, bàn làm việc, tủ lạnh, máy giặt. View biển, gần Bãi Mỹ Khê, an ninh tốt."
    },
    {
        id: 6,
        name: "Phòng Trọ Cẩm Lệ",
        area: "Cẩm Lệ",
        address: "122 Cách Mạng Tháng 8, Cẩm Lệ, Đà Nẵng",
        price: 2200000,
        deposit: 1,
        image: "https://images.unsplash.com/photo-1536376072261-38c75010e6c9",
        phone: "0956789012",
        nearSchool: true,
        wifi: true,
        airConditioner: true,
        parking: true,
        hotWater: true,
        ownToilet: false,
        balcony: false,
        description: "Gần ĐH Duy Tân và ĐH Đông Á. Phòng mới xây, sạch sẽ, thoáng mát. Chủ nhà thân thiện, hỗ trợ sinh viên ngoại tỉnh."
    },
    {
        id: 7,
        name: "Phòng Trọ Ngũ Hành Sơn Cao Cấp",
        area: "Ngũ Hành Sơn",
        address: "38 Trường Sa, Ngũ Hành Sơn, Đà Nẵng",
        price: 6500000,
        deposit: 2,
        image: "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688",
        phone: "0967890123",
        nearSchool: false,
        wifi: true,
        airConditioner: true,
        parking: true,
        hotWater: true,
        ownToilet: true,
        balcony: true,
        description: "Căn hộ mini cao cấp gần biển Mỹ Khê, view đẹp. Đầy đủ nội thất hiện đại, thang máy, bảo vệ 24/7. Gần các resort và trung tâm thương mại."
    },
    {
        id: 8,
        name: "Phòng Trọ Hòa Vang",
        area: "Hòa Vang",
        address: "75 Quốc lộ 14B, Hòa Vang, Đà Nẵng",
        price: 1500000,
        deposit: 1,
        image: "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267",
        phone: "0978901234",
        nearSchool: true,
        wifi: false,
        airConditioner: false,
        parking: true,
        hotWater: false,
        ownToilet: true,
        balcony: false,
        description: "Phòng rộng rãi thoáng mát, WC riêng khép kín. Gần ĐH Sư Phạm Đà Nẵng, chợ, tiện lợi. Không chung chủ, ra vào tự do."
    },
    {
        id: 9,
        name: "Phòng Trọ Hải Châu Trung Tâm",
        area: "Hải Châu",
        address: "9 Bạch Đằng, Hải Châu, Đà Nẵng",
        price: 3800000,
        deposit: 2,
        image: "https://images.unsplash.com/photo-1493809842364-78817add7ffb",
        phone: "0989012345",
        nearSchool: true,
        wifi: true,
        airConditioner: true,
        parking: true,
        hotWater: true,
        ownToilet: true,
        balcony: false,
        description: "Ngay trung tâm Đà Nẵng, sát sông Hàn. Gần ĐH Kinh Tế và ĐH Ngoại Ngữ. Phòng yên tĩnh, đầy đủ tiện nghi, điện 2.5k/số."
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
    "trung tâm":     ["trung tâm", "hải châu", "bạch đằng"],
    "nội thất":      ["nội thất", "đầy đủ", "có tủ", "có giường", "có bàn"],
    "không chung chủ": ["không chung chủ", "tự do", "riêng tư"],
    "thoáng":        ["thoáng", "thoáng mát", "cửa sổ", "thoáng đãng"],
    "biển":          ["biển", "mỹ khê", "view biển", "sơn trà"],
};

const roomList = document.getElementById("roomList");

/* ══════════════════════════════════════════════
   FUZZY / BROAD SEARCH
   ══════════════════════════════════════════════ */

function normalize(str) {
    return str
        .toLowerCase()
        .normalize("NFD")
        .replace(/[\u0300-\u036f]/g, "")
        .replace(/đ/g, "d")
        .trim();
}

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

function tokenMatches(token, haystack) {
    const normHay = normalize(haystack);
    if (normHay.includes(token)) return true;
    const digitsToken = token.replace(/\D/g, "");
    if (digitsToken.length >= 3) {
        const digitsHay = haystack.replace(/\D/g, "");
        if (digitsHay.includes(digitsToken)) return true;
    }
    return false;
}

function scoreRoom(room, keyword) {
    if (!keyword) return 1;
    const searchFields = [
        room.name, room.area, room.address, room.description, room.phone,
    ].join(" ");
    const tokens  = keyword.toLowerCase().trim().split(/\s+/);
    const allTerms = tokens.flatMap(t => expandKeyword(t));
    let matchCount = 0;
    for (const term of allTerms) {
        if (tokenMatches(term, searchFields)) matchCount++;
    }
    return matchCount;
}

/* ══════════════════════════════════════════════
   TOGGLE CHIP
   ══════════════════════════════════════════════ */

function toggleAll(type, el) {
    const set     = type === "price" ? activePrices : activeAmenities;
    const groupId = type === "price" ? "priceChips" : "amenityChips";
    const group   = document.getElementById(groupId);
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
    if (allBtn) allBtn.classList.remove("active");
    if (set.has(value)) {
        set.delete(value);
        el.classList.remove("active");
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
    const keyword = document.getElementById("search").value.trim();
    const filtered = rooms
        .map(room => ({ room, score: scoreRoom(room, keyword) }))
        .filter(({ room, score }) => {
            if (score === 0) return false;
            let matchPrice = true;
            if (activePrices.size > 0) {
                matchPrice = [...activePrices].some(val => {
                    const range = priceRanges[val];
                    return room.price >= range.min && room.price <= range.max;
                });
            }
            let matchAmenity = true;
            if (activeAmenities.size > 0) {
                matchAmenity = [...activeAmenities].every(val => {
                    const field = amenityMap[val];
                    return room[field] === true;
                });
            }
            return matchPrice && matchAmenity;
        })
        .sort((a, b) => b.score - a.score)
        .map(({ room }) => room);

    displayRooms(filtered);
    updateResultBar(filtered.length, keyword);
}

/* ── HIỂN THỊ PHÒNG ── */
function displayRooms(data) {
    roomList.innerHTML = "";
    if (data.length === 0) {
        roomList.innerHTML = "<p class='no-result'>😕 Không tìm thấy phòng phù hợp tại Đà Nẵng. Hãy thử thay đổi bộ lọc.</p>";
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
    if (!hasFilter) { bar.style.display = "none"; return; }
    bar.style.display = "flex";
    document.getElementById("resultCount").textContent = `Tìm thấy ${count} phòng tại Đà Nẵng`;
    const labels = [];
    if (keyword) labels.push(`"${keyword}"`);
    if (activePrices.size > 0)    labels.push(`${activePrices.size} mức giá`);
    if (activeAmenities.size > 0) labels.push(`${activeAmenities.size} tiện nghi`);
    document.getElementById("activeFiltersLabel").textContent =
        labels.length ? "· Bộ lọc: " + labels.join(", ") : "";
}

/* ══════════════════════════════════════════════
   GEMINI AI CONFIG
   Dán API key của bạn vào đây
   Lấy miễn phí tại: aistudio.google.com
   ══════════════════════════════════════════════ */
const GEMINI_API_KEY = "AIzaSyDkA5FpEj29Gi2R5WBFD1C50xikF5it_kM";
const GEMINI_URL = `https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${GEMINI_API_KEY}`;

const GEMINI_SYSTEM_PROMPT = `Bạn là trợ lý tìm phòng trọ sinh viên tại Đà Nẵng tên là MR-FindRoom. Luôn trả lời thân thiện bằng tiếng Việt.

QUAN TRỌNG: Mọi phản hồi BẮT BUỘC kết thúc bằng đúng 1 thẻ <filter>...</filter>.
- friendlyReply KHÔNG ĐƯỢC để trống, luôn phải có nội dung.
- Nếu người dùng chào hỏi hoặc hỏi ngoài chủ đề phòng trọ → điền friendlyReply, còn lại để null/[].
- Nếu người dùng tìm phòng → điền đầy đủ tất cả các trường.

Định dạng (không thay đổi tên field):
<filter>{"maxPrice":null,"minPrice":null,"areas":[],"amenities":[],"friendlyReply":"nội dung trả lời"}</filter>

Quy tắc:
- areas: "Hải Châu","Thanh Khê","Ngũ Hành Sơn","Sơn Trà","Liên Chiểu","Cẩm Lệ","Hòa Vang"
- amenities: "wifi","ac","parking","hotwater","toilet","school"
- Giá tính bằng VNĐ ("2 triệu" = 2000000)

Ví dụ 1 — chào hỏi:
User: "hi"
<filter>{"maxPrice":null,"minPrice":null,"areas":[],"amenities":[],"friendlyReply":"Xin chào! Mình là MR-FindRoom 🏠 Bạn muốn tìm phòng trọ ở quận nào tại Đà Nẵng?"}</filter>

Ví dụ 2 — tìm phòng:
User: "tìm phòng dưới 2 triệu ở Ngũ Hành Sơn có wifi"
<filter>{"maxPrice":2000000,"minPrice":null,"areas":["Ngũ Hành Sơn"],"amenities":["wifi"],"friendlyReply":"Mình tìm thấy một số phòng ở Ngũ Hành Sơn nhé! 🏠"}</filter>`;

/* ── GỢI Ý THÔNG MINH — CHAT PANEL ── */

function toggleSmartChat() {
    const chat = document.getElementById("smartChat");
    chat.classList.toggle("open");
    if (chat.classList.contains("open")) {
        setTimeout(() => document.getElementById("smartInput").focus(), 100);
    }
}

document.addEventListener("click", function(e) {
    const wrapper = document.querySelector(".smart-wrapper");
    const chat    = document.getElementById("smartChat");
    if (chat && chat.classList.contains("open") && !wrapper.contains(e.target)) {
        chat.classList.remove("open");
    }
});

async function sendSmartMessage() {
    const input = document.getElementById("smartInput");
    const text  = input.value.trim();
    if (!text) return;

    addMsg(text, "msg-user");
    input.value = "";
    input.disabled = true;

    // Hiện loading
    const loadingId = "loading-" + Date.now();
    addMsg(`<span id="${loadingId}">⏳ Đang phân tích...</span>`, "msg-bot");

    // Nếu chưa điền API key thì fallback về parser cũ
    if (!GEMINI_API_KEY || GEMINI_API_KEY === "PASTE_YOUR_KEY_HERE") {
        document.getElementById(loadingId)?.parentElement?.remove();
        const result = parseSmartQuery(text);
        applySmartFilter(result);
        addMsg(buildReply(result), result.count > 0 ? "msg-result" : "msg-bot");
        input.disabled = false;
        input.focus();
        return;
    }

    try {
        const res = await fetch(GEMINI_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                contents: [{
                    parts: [{ text: GEMINI_SYSTEM_PROMPT + "\n\nUser: " + text }]
                }],
                generationConfig: {
                    temperature: 0.3,
                    maxOutputTokens: 512,
                }
            })
        });

        const data = await res.json();

        // Xoá loading
        document.getElementById(loadingId)?.parentElement?.remove();

        if (!res.ok || !data.candidates?.[0]) {
            throw new Error(data.error?.message || "Gemini không phản hồi");
        }

        const rawText = data.candidates[0].content.parts[0].text;

        // Trích filter JSON từ thẻ <filter>...</filter>
        const filterMatch = rawText.match(/<filter>([\s\S]*?)<\/filter>/);
        if (filterMatch) {
            try {
                const parsed = JSON.parse(filterMatch[1]);
                const friendlyReply = parsed.friendlyReply || "Mình có thể giúp gì thêm?";
                delete parsed.friendlyReply;
                parsed.count = 0;

                // Kiểm tra có tiêu chí lọc phòng không
                const hasFilter = parsed.maxPrice || parsed.minPrice
                    || (parsed.areas && parsed.areas.length > 0)
                    || (parsed.amenities && parsed.amenities.length > 0);

                if (hasFilter) {
                    // Có tiêu chí → lọc và hiện kết quả
                    applySmartFilter(parsed);
                    const resultNote = parsed.count === 0
                        ? "Không tìm thấy phòng phù hợp 😕"
                        : `Hiển thị <strong>${parsed.count}</strong> phòng bên dưới ↓`;
                    addMsg(`${friendlyReply}<br><small style="opacity:.7">→ ${resultNote}</small>`,
                        parsed.count > 0 ? "msg-result" : "msg-bot");
                } else {
                    // Hội thoại thường → chỉ hiện reply, KHÔNG lọc phòng
                    addMsg(friendlyReply, "msg-bot");
                }
            } catch (e) {
                // JSON parse lỗi → hiện text thô
                addMsg(rawText.replace(/<filter>[\s\S]*?<\/filter>/g, "").trim()
                    || "Bạn muốn tìm phòng như thế nào?", "msg-bot");
            }
        } else {
            // Không có thẻ <filter> → hiện text thô
            addMsg(rawText.trim() || "Bạn muốn tìm phòng như thế nào?", "msg-bot");
        }

    } catch (err) {
        document.getElementById(loadingId)?.parentElement?.remove();
        console.error("Gemini error:", err);

        // Fallback về parser cũ khi lỗi
        const result = parseSmartQuery(text);
        applySmartFilter(result);
        const fallbackMsg = result.count > 0
            ? buildReply(result)
            : `❌ Không kết nối được AI (${err.message}). Thử lại sau nhé!`;
        addMsg(fallbackMsg, result.count > 0 ? "msg-result" : "msg-bot");
    }

    input.disabled = false;
    input.focus();
}

function addMsg(text, cls) {
    const box = document.getElementById("smartMessages");
    const div = document.createElement("div");
    div.className = "msg " + cls;
    div.innerHTML = text;
    box.appendChild(div);
    box.scrollTop = box.scrollHeight;
}

/* ── PARSER ── */
function parseSmartQuery(text) {
    const t   = normalize(text);
    const out = { keyword: "", maxPrice: null, minPrice: null, amenities: [], areas: [], count: 0 };

    const underM = t.match(/duoi\s*([\d,.]+)\s*(trieu|tr\b|000000)/);
    if (underM) out.maxPrice = parseFloat(underM[1].replace(",",".")) * 1000000;

    const aboveM = t.match(/tren\s*([\d,.]+)\s*(trieu|tr\b)/);
    if (aboveM) out.minPrice = parseFloat(aboveM[1].replace(",",".")) * 1000000;

    const rangeM = t.match(/([\d,.]+)\s*[-–~]\s*([\d,.]+)\s*(trieu|tr\b)/);
    if (rangeM) {
        out.minPrice = parseFloat(rangeM[1].replace(",",".")) * 1000000;
        out.maxPrice = parseFloat(rangeM[2].replace(",",".")) * 1000000;
    }

    if (/wifi|mang|internet/.test(t))                    out.amenities.push("wifi");
    if (/may lanh|dieu hoa|lanh\b/.test(t))              out.amenities.push("ac");
    if (/giu xe|gui xe|dau xe|xe may|parking/.test(t))   out.amenities.push("parking");
    if (/nong lanh|binh nong|voi nong/.test(t))          out.amenities.push("hotwater");
    if (/wc rieng|toilet rieng|nha ve sinh rieng|phong tam rieng/.test(t)) out.amenities.push("toilet");
    if (/gan truong|gan dh|gan dai hoc|sinh vien/.test(t)) out.amenities.push("school");

    // Các quận/huyện tại Đà Nẵng
    const areaKeywords = [
        "hai chau", "thanh khe", "ngu hanh son", "son tra",
        "lien chieu", "cam le", "hoa vang", "hoang sa"
    ];
    areaKeywords.forEach(a => {
        if (t.includes(a)) out.areas.push(a);
    });

    return out;
}

/* ── ÁP DỤNG BỘ LỌC TỪ CHAT ── */
function applySmartFilter(parsed) {
    resetFilter();
    const filtered = rooms.filter(room => {
        if (parsed.maxPrice && room.price > parsed.maxPrice) return false;
        if (parsed.minPrice && room.price < parsed.minPrice) return false;
        if (parsed.areas.length > 0) {
            const normArea = normalize(room.area + " " + room.address);
            const match = parsed.areas.some(a => normArea.includes(normalize(a)));
            if (!match) return false;
        }
        for (const a of parsed.amenities) {
            const field = amenityMap[a];
            if (!room[field]) return false;
        }
        return true;
    });
    parsed.count = filtered.length;
    displayRooms(filtered);
    const bar = document.getElementById("resultBar");
    bar.style.display = "flex";
    document.getElementById("resultCount").textContent = `Tìm thấy ${filtered.length} phòng tại Đà Nẵng`;
    document.getElementById("activeFiltersLabel").textContent = "· Gợi ý thông minh";
}

/* ── TẠO PHẢN HỒI ── */
function buildReply(parsed) {
    if (parsed.count === 0) {
        return "😕 Mình không tìm được phòng nào khớp tại Đà Nẵng. Bạn thử mô tả lại hoặc bớt điều kiện nhé!";
    }
    const parts = [];
    if (parsed.maxPrice) parts.push(`giá dưới ${(parsed.maxPrice/1000000).toFixed(0)} triệu`);
    if (parsed.minPrice && parsed.maxPrice) parts[parts.length-1] = `giá ${(parsed.minPrice/1e6).toFixed(0)}–${(parsed.maxPrice/1e6).toFixed(0)} triệu`;
    else if (parsed.minPrice) parts.push(`giá trên ${(parsed.minPrice/1000000).toFixed(0)} triệu`);
    if (parsed.areas.length)     parts.push(`khu vực phù hợp tại Đà Nẵng`);
    if (parsed.amenities.length) parts.push(`có ${parsed.amenities.length} tiện nghi yêu cầu`);
    const criteria = parts.length ? ` (${parts.join(", ")})` : "";
    return `✅ Tìm được <strong>${parsed.count} phòng</strong>${criteria}. Kết quả đã được hiển thị bên dưới!`;
}

/* ── XOÁ LỌC ── */
function resetFilter() {
    document.getElementById("search").value = "";
    activePrices.clear();
    activeAmenities.clear();
    document.querySelectorAll(".chip").forEach(c => c.classList.remove("active"));
    document.querySelectorAll(".chip-all").forEach(c => c.classList.add("active"));
    document.getElementById("resultBar").style.display = "none";
    displayRooms(rooms);
}

// Hiển thị khi load trang — đọc ?q= từ URL nếu có
(function initFromURL() {
    const params = new URLSearchParams(window.location.search);
    const q = params.get("q");
    if (q) {
        const searchEl = document.getElementById("search");
        if (searchEl) {
            searchEl.value = q;
            filterRooms();
            return;
        }
    }
    displayRooms(rooms);
})();

/* ── BOTTOM NAV ── */
function setNav(el, tab) {
    document.querySelectorAll(".nav-item").forEach(b => b.classList.remove("active"));
    el.classList.add("active");
    if (tab === "home") {
        resetFilter();
        window.scrollTo({ top: 0, behavior: "smooth" });
    } else if (tab === "search") {
        document.getElementById("search").focus();
        window.scrollTo({ top: 80, behavior: "smooth" });
    } else if (tab === "post") {
        alert("Tính năng Đăng tin đang được phát triển! 🚀");
        el.classList.remove("active");
        document.querySelector(".nav-item").classList.add("active");
    } else if (tab === "message") {
        alert("Tính năng Tin nhắn đang được phát triển! 💬");
        el.classList.remove("active");
        document.querySelector(".nav-item").classList.add("active");
    } else if (tab === "profile") {
        alert("Tính năng hồ sơ & cài đặt đang được phát triển! 👤");
        el.classList.remove("active");
        document.querySelector(".nav-item").classList.add("active");
    }
}
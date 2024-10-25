-- Tabel carts
CREATE TABLE carts
(
    id          VARCHAR(255) PRIMARY KEY, -- ID unik untuk Cart
    user_id     VARCHAR(255) NOT NULL,    -- ID user pemilik Cart
    total_price DECIMAL(10, 2),           -- Total harga dari produk dalam Cart
    created_at  TIMESTAMP    NOT NULL,    -- Waktu pembuatan Cart
    updated_at  TIMESTAMP    NOT NULL,    -- Waktu terakhir kali Cart diubah
    status      VARCHAR(50)  NOT NULL     -- Status Cart (misalnya, 'active' atau 'checked out')
);

-- Tabel cart_items
CREATE TABLE cart_items
(
    id           VARCHAR(255) PRIMARY KEY, -- ID unik untuk setiap Cart Item
    cart_id      VARCHAR(255)   NOT NULL,  -- Foreign key yang menghubungkan ke tabel carts
    product_id   VARCHAR(255)   NOT NULL,  -- ID produk yang ditambahkan ke Cart
    product_name VARCHAR(255)   NOT NULL,  -- Nama produk
    quantity     INT            NOT NULL,  -- Jumlah produk dalam Cart
    price        DECIMAL(10, 2) NOT NULL,  -- Harga produk saat ditambahkan ke Cart
    CONSTRAINT fk_cart
        FOREIGN KEY (cart_id)
            REFERENCES carts (id)          -- Membuat foreign key yang mengacu ke tabel carts
            ON DELETE CASCADE              -- Jika Cart dihapus, item yang terkait juga akan dihapus
);

CREATE TABLE item
(
    id  INT AUTO_INCREMENT PRIMARY KEY,
    item_name TEXT NOT NULL,
    item_code  varchar(3) NOT NULL
);

CREATE TABLE store
(
    id    INT AUTO_INCREMENT PRIMARY KEY,
    store_name TEXT NOT NULL,
    store_code  varchar(3) NOT NULL
);

CREATE TABLE storeanditem
(
    i_id   INT   NOT NULL,
    s_id    INT   NOT NULL,
    FOREIGN KEY (i_id) REFERENCES item (id),
    FOREIGN KEY (s_id) REFERENCES store (id)
);
-- USERS
CREATE TABLE IF NOT EXISTS tbl_users_telegram_data
(
    id_user_telegram_data  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    telegram_id            VARCHAR(16) NOT NULL,
    first_name             VARCHAR(64) NOT NULL,
    last_name              VARCHAR(64),
    username               VARCHAR(32),
    phone_number           VARCHAR(16),
    language_code          VARCHAR(3) NOT NULL,
    is_premium             BOOLEAN NOT NULL,

    PRIMARY KEY (id_user_telegram_data),
    UNIQUE (telegram_id)
);



CREATE TABLE IF NOT EXISTS tbl_users
(
    id_user                INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_user_telegram_data  INTEGER UNSIGNED NOT NULL,
    call_sign              VARCHAR(20),
    xp_total               INTEGER UNSIGNED DEFAULT 0,
    balance                DECIMAL(10,2) DEFAULT 0,
    referral_code          VARCHAR(12) NOT NULL,
    save_data              BOOLEAN NOT NULL DEFAULT TRUE,

    PRIMARY KEY (id_user),
    UNIQUE (referral_code),

    FOREIGN KEY (id_user_telegram_data) REFERENCES tbl_users_telegram_data(id_user_telegram_data)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



-- EVENTS
CREATE TABLE IF NOT EXISTS tbl_events_locations
(
    id_location  INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    name         VARCHAR(70) NOT NULL,
    address      VARCHAR(200) NOT NULL,
    google_maps  VARCHAR(60),

    PRIMARY KEY (id_location),
    UNIQUE (name, address),
    UNIQUE (google_maps)
);



CREATE TABLE IF NOT EXISTS tbl_events
(
    id_event      INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    registration  BOOLEAN NOT NULL DEFAULT TRUE,
    event_date    DATE NOT NULL,
    start_time    TIME NOT NULL,
    end_time      TIME NOT NULL,
    max_members   INTEGER UNSIGNED NOT NULL CHECK (max_members > 0),
    id_location   INTEGER UNSIGNED NOT NULL,
    cost          DECIMAL(10,2) NOT NULL,

    PRIMARY KEY (id_event),

    FOREIGN KEY (id_location) REFERENCES tbl_events_locations(id_location)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_members
(
    id_event_member             INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event                    INTEGER UNSIGNED NOT NULL,
    id_user                     INTEGER UNSIGNED NOT NULL,
    full_name                   VARCHAR(150) NOT NULL,
    call_sign                   VARCHAR(20),
    phone_number                VARCHAR(16) NOT NULL,
    equipment                   VARCHAR(10) NOT NULL,
    registered                  BOOLEAN NOT NULL DEFAULT TRUE,
    registration_timestamp      DATETIME NOT NULL,
    update_timestamp            DATETIME DEFAULT NULL,

    PRIMARY KEY (id_event_member),
    UNIQUE (id_event, id_user),

    FOREIGN KEY (id_event) REFERENCES tbl_events(id_event)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,

    FOREIGN KEY (id_user) REFERENCES tbl_users(id_user)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_data
(
    id_event_data       INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event            INTEGER UNSIGNED NOT NULL,
    lang                VARCHAR(2) NOT NULL,
    name                VARCHAR(50) NOT NULL,
    short_description   TEXT,
    description         TEXT,

    PRIMARY KEY (id_event_data),
    UNIQUE (id_event, lang),

    FOREIGN KEY (id_event) REFERENCES tbl_events(id_event)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_programs
(
    id_event_program        INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event                INTEGER UNSIGNED NOT NULL,
    position                INTEGER UNSIGNED NOT NULL DEFAULT 0,

    PRIMARY KEY (id_event_program),

    FOREIGN KEY (id_event) REFERENCES tbl_events(id_event)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_programs_i18n
(
    id_event_program_i18n       INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event_program            INTEGER UNSIGNED NOT NULL,
    lang                        VARCHAR(2) NOT NULL,
    text                        VARCHAR(100) NOT NULL,

    PRIMARY KEY (id_event_program_i18n),
    UNIQUE (id_event_program, lang),

    FOREIGN KEY (id_event_program) REFERENCES tbl_events_programs(id_event_program)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_rules
(
    id_event_rule       INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event            INTEGER UNSIGNED NOT NULL,
    position            INTEGER UNSIGNED NOT NULL DEFAULT 0,

    PRIMARY KEY (id_event_rule),

    FOREIGN KEY (id_event) REFERENCES tbl_events(id_event)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



CREATE TABLE IF NOT EXISTS tbl_events_rules_i18n
(
    id_event_rule_i18n      INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_event_rule           INTEGER UNSIGNED NOT NULL,
    lang                    VARCHAR(2) NOT NULL,
    text                    VARCHAR(100) NOT NULL,

    PRIMARY KEY (id_event_rule_i18n),
    UNIQUE (id_event_rule, lang),

    FOREIGN KEY (id_event_rule) REFERENCES tbl_events_rules(id_event_rule)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



-- NOTIFICATIONS
CREATE TABLE IF NOT EXISTS tbl_notifications
(
    id_notification     INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    category            VARCHAR(30) NOT NULL,
    created_at          DATETIME NOT NULL,
    updated_at          DATETIME,

    PRIMARY KEY (id_notification)
);



CREATE TABLE IF NOT EXISTS tbl_notifications_i18n
(
    id_notification_i18n    INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
    id_notification         INTEGER UNSIGNED NOT NULL,
    lang                    VARCHAR(2) NOT NULL,
    title                   VARCHAR(50) NOT NULL,
    description             TEXT,

    PRIMARY KEY (id_notification_i18n),
    UNIQUE (id_notification, lang),

    FOREIGN KEY (id_notification) REFERENCES tbl_notifications(id_notification)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);



-- INDEXES
CREATE INDEX IF NOT EXISTS idx_users_telegram_data_id
    ON tbl_users_telegram_data(id_user_telegram_data);

CREATE INDEX IF NOT EXISTS idx_events_location
    ON tbl_events(id_location);

CREATE INDEX IF NOT EXISTS idx_events_date
    ON tbl_events(event_date);

CREATE INDEX IF NOT EXISTS idx_events_data_event
    ON tbl_events_data(id_event);

CREATE INDEX IF NOT EXISTS idx_events_programs_event
    ON tbl_events_programs(id_event);

CREATE INDEX IF NOT EXISTS idx_events_programs_position
    ON tbl_events_programs(id_event, position);

CREATE INDEX IF NOT EXISTS idx_events_rules_event
    ON tbl_events_rules(id_event);

CREATE INDEX IF NOT EXISTS idx_events_rules_position
    ON tbl_events_rules(id_event, position);
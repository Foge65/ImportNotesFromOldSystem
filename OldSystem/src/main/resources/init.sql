DROP TABLE IF EXISTS client, note, client_note;

CREATE TABLE client
(
    agency            varchar(255) NOT NULL,
    guid              varchar(255) NOT NULL,
    first_name        varchar(255) NOT NULL,
    last_name         varchar(255) NOT NULL,
    status            int2         NOT NULL,
    dop               timestamp    NOT NULL,
    created_date_time timestamp    NOT NULL,

    CONSTRAINT client_pkey PRIMARY KEY (guid)
);

CREATE TABLE note
(
    agency             varchar(255) NOT NULL,
    date_from          timestamp    NOT NULL,
    date_to            timestamp    NOT NULL,
    client_guid        varchar(255) NOT NULL,

    comments           varchar(255) NOT NULL,
    guid               varchar(255) NOT NULL,
    modified_date_time timestamp    NOT NULL,
    date_time          timestamp    NOT NULL,
    logged_user        varchar(255) NOT NULL,
    created_date_time  timestamp    NOT NULL,

    CONSTRAINT note_pkey PRIMARY KEY (guid)
);

CREATE TABLE client_note
(
    logged_user varchar(255) NOT NULL
);

INSERT INTO client(agency, guid, first_name, last_name, status, dop, created_date_time)
VALUES ('vhh4',
        '01588E84-D45A-EB98-F47F-716073A4F1EF',
        'Ne',
        'Abr',
        200,
        '10-15-1999',
        '2021-11-15 11:51:59');

INSERT INTO note(agency, date_from, date_to, client_guid, comments, guid, modified_date_time, date_time,
                 logged_user,
                 created_date_time)
VALUES ('vhh4',
        '2019-09-18',
        '2021-09-17',
        '01588E84-D45A-EB98-F47F-716073A4F1EF',
        'Patient Care Coordinator, reached out to patient caregiver is still in the hospital',
        '20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8',
        '2021-11-15 11:51:59',
        '2021-9-16 12:02:26',
        'p.vasya',
        '2021-11-15 11:51:59');

INSERT INTO client (agency, guid, first_name, last_name, status, dop, created_date_time)
SELECT 'vhh' || (i % 10),
       md5(random()::text || clock_timestamp()::text)::uuid,
       'FirstName' || i,
       'LastName' || i,
       (i % 3) * 100 + 200,
       timestamp '1999-10-15' + (i * interval '1 day'),
       timestamp '2021-11-15 11:51:59' + (i * interval '1 minute')
FROM generate_series(1, 100) AS s(i);

INSERT INTO note (agency, date_from, date_to, client_guid, comments, guid, modified_date_time, date_time, logged_user,
                  created_date_time)
SELECT 'vhh' || (i % 10),
       timestamp '2019-09-18' + (i * interval '1 day'),
       timestamp '2021-09-17' + (i * interval '1 day'),
       c.guid,
       'Patient Care Coordinator, note ' || i,
       md5(random()::text || clock_timestamp()::text)::uuid,
       timestamp '2021-11-15 11:51:59' + (i * interval '1 minute'),
       timestamp '2021-09-16 12:02:26' + (i * interval '1 hour'),
       'p.user' || (i % 10),
       timestamp '2021-11-15 11:51:59' + (i * interval '1 minute')
FROM generate_series(1, 100) AS s(i)
         JOIN client c ON c.agency = 'vhh' || (i % 10);

INSERT INTO client_note (logged_user)
SELECT 'p.user' || (i % 10)
FROM generate_series(1, 100) AS s(i);

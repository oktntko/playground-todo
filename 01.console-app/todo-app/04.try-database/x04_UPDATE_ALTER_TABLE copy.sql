-- https://www.postgresql.jp/document/14/html/tutorial-update.html
-- 2.8. 更新
UPDATE todo
SET
  datetime = '2023-04-01 00:00:00';

SELECT * FROM todo;

UPDATE todo
SET
  yarukoto = '';

SELECT * FROM todo;

-- ALTER TABLE
ALTER TABLE todo ADD COLUMN id serial;
SELECT * FROM todo;

ALTER TABLE todo DROP COLUMN id serial;
SELECT * FROM todo;

ALTER TABLE todo DROP COLUMN id serial;
ALTER TABLE todo ADD CONSTRAINT pkey_todo_id PRIMARY KEY (id);
SELECT * FROM todo;

-- 順番が気持ち悪いので削除する
DROP TABLE IF EXISTS todo;

CREATE TABLE IF NOT EXISTS todo(
  id serial PRIMARY KEY
  , yarukoto VARCHAR (100)
  , datetime TIMESTAMP WITH TIME ZONE
);

SELECT
    id
  , yarukoto
  , datetime
FROM
  todo;

-- x02_INSERT.sql を再度実行する

-- WHERE 句 & RETURNING 句
UPDATE todo
SET
  yarukoto = '電話をかけなおす'
WHERE
  id = 1 RETURNING *;

UPDATE todo
SET
  datetime = CURRENT_TIMESTAMP
WHERE
  datetime > '2023-04-02' RETURNING *;

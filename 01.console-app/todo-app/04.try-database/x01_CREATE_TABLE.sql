-- https://www.postgresql.jp/document/14/html/tutorial-table.html
-- 2.3. 新しいテーブルの作成

-- \dt; -- List tabels.

DROP TABLE IF EXISTS todo;

CREATE TABLE IF NOT EXISTS todo(
  yarukoto VARCHAR (100)
  , datetime TIMESTAMP WITH TIME ZONE
);

-- \dt; -- List tabels.

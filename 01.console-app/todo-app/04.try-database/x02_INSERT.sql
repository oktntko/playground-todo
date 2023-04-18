-- https://www.postgresql.jp/document/14/html/tutorial-populate.html
-- 2.4. テーブルに行を挿入
INSERT
INTO todo(yarukoto, datetime)
VALUES ('電話をかける', '2023-04-01 10:23:45.678');

INSERT
INTO todo(yarukoto, datetime)
VALUES ('カレンダーを見る', '2023-04-02 10:23:45.678');

INSERT
INTO todo(yarukoto, datetime)
VALUES ('テレビを見る', '2023-04-02 11:23:45.678');

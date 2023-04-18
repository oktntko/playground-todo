-- https://www.postgresql.jp/document/14/html/tutorial-select.html
-- 2.5. テーブルへの問い合わせ
SELECT
    *
FROM
  todo;

-- SELECT 句
SELECT
    yarukoto
  , datetime
FROM
  todo;

-- WHERE 句 （文字列）
SELECT
    yarukoto
  , datetime
FROM
  todo
WHERE
  yarukoto = '電話をかける';

-- WHERE 句 （日付時刻）
SELECT
    yarukoto
  , datetime
FROM
  todo
WHERE
  datetime <= '2023-04-02 10:00:00';

SELECT
    yarukoto
  , datetime
FROM
  todo
WHERE
  datetime <= '2023-04-02 11:00:00';

-- ORDER BY 句
SELECT
    yarukoto
  , datetime
FROM
  todo
ORDER BY
  datetime ASC;

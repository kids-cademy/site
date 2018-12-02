SELECT *, COUNT(*) AS count FROM audit WHERE event='FABLE_PLAY' GROUP BY parameter1 ORDER BY count DESC;

SELECT parameter1, COUNT(*) AS count FROM audit WHERE event='FABLE_PLAY' GROUP BY parameter1 ORDER BY count DESC;
SELECT deviceSerial,SUM(parameter1) AS uptime FROM kids_cademy.audit WHERE event='APP_ACTIVE' GROUP BY deviceSerial ORDER BY uptime DESC;
CREATE OR REPLACE FUNCTION count_messages( 
   ) 
   RETURNS bigint AS
$BODY$ 
    BEGIN
        RETURN (SELECT COUNT(*)
        FROM user_message); 
    END; 
$BODY$ 
LANGUAGE plpgsql;

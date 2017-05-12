CREATE OR REPLACE FUNCTION count_comments( 
   ) 
   RETURNS bigint AS
$BODY$ 
    BEGIN
        RETURN (SELECT COUNT(*)
        FROM comment); 
    END; 
$BODY$ 
LANGUAGE plpgsql;

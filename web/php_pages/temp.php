<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$unsafe_variable =  $_REQUEST['id'];
$safe_variable = mysql_real_escape_string($unsafe_variable);
$q=mysql_query("SELECT name, rating, img_loc FROM zfr_table WHERE id = " . $safe_variable);
while($e=mysql_fetch_assoc($q))
        $output[]=$e;

print(json_encode($output));

mysql_close();
?>
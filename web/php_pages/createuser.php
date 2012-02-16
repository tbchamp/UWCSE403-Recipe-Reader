<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$unsafe_username =  $_REQUEST['username'];
$safe_username = mysql_real_escape_string($unsafe_username);
$unsafe_password =  $_REQUEST['password'];
$safe_password = mysql_real_escape_string($unsafe_password);
$result = mysql_query("insert into user(username, password, unique_id) values( '" . $safe_username . "', '" . hash("sha256", $safe_password) . "', '" . hash("sha256", $safe_username . $safe_password . "salt") . "')");
if (!$result) {
	print("Create User Failed!");
} else {
	$q=mysql_query("select unique_id from user where username = '" . $safe_username . "' and password = '" . hash("sha256", $safe_password) . "'");
	while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
	
	print(json_encode($output));
}

mysql_close();
?>
<?php
//get mysql connection
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type = $_REQUEST['type'];
if (strcmp($type, 'byUniqueId') == 0){
	//signin by unique id
	$unsafe_id =  $_REQUEST['id'];
	$safe_id = mysql_real_escape_string($unsafe_id);
	$q = mysql_query("select id, username from user where unique_id = '" . $safe_id . "'");
	if (!$q) {
		print("Sign in failed");
	} else {
		while($e = mysql_fetch_assoc($q)){
			$output[] = $e;
		}
		print(json_encode($output));
	}
} elseif (strcmp($type, 'byUserPass') == 0){
	//sign in by username and password
	$unsafe_username =  $_REQUEST['username'];
	$safe_username = mysql_real_escape_string($unsafe_username);
	$unsafe_password =  $_REQUEST['password'];
	$safe_password = mysql_real_escape_string($unsafe_password);
	$q=mysql_query("select id, unique_id from user where username = '" . $safe_username . "' and password = '" . hash("sha256", $safe_password) . "'");
	if (!$q) {
		print("Sign in failed");
	} else {
		while($e=mysql_fetch_assoc($q)){
			$output[]=$e;
		}
		print(json_encode($output));
	}
}
mysql_close();
?>
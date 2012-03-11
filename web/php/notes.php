<?php
//get mysql connection
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type =  $_REQUEST['type'];

$unsafe_userid = $_REQUEST['userid']
$safe_userid = mysql_real_escape_string($unsafe_userid);

$unsafe_recipeid = $_REQUEST['recipeid']
$safe_recipeid = mysql_real_escape_string($unsafe_recipeid);

if (strcmp($type, 'add') == 0){
	//add notes for a user
	$unsafe_text = $_REQUEST['text']
	$safe_text = mysql_real_escape_string($unsafe_text);
	$q=mysql_query("insert into notes values(" . $safe_recipeid . ", "
			. $safe_userid . ", '" . $safe_text . "')");
	if (!$q){
		print("add note failed");
	} else {
		print("note added");
	}
}
mysql_close();
?>
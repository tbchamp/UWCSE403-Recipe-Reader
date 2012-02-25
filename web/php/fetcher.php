<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type =  $_REQUEST['type'];

$unsafe_recipeid = $_REQUEST['recipeid'];
$safe_recipeid = mysql_real_escape_string($unsafe_recipeid);
if (strcmp($type, 'getMain') == 0){
	$q=mysql_query("select r.prep_time, r.cook_time, r.yield,
		r.calories, r.fat, r.colesterol, m.name, r.ready_time, r.img_loc from recipe r, meals m
		where r.id = " . $safe_recipeid . " and m.id = r.meal_id");
	if (!$q){
		print("get main recipe failed");
	} else {
		while($e=mysql_fetch_assoc($q))
			        $output[]=$e;
		print(json_encode($output));
	}
} elseif (strcmp($type, 'ingredients') == 0){
	$q = mysql_query("select text from ingredients
		where recipe_id = " . $safe_recipeid);
	if (!$q){
		print("get ingredients failed");
	} else {
		while($e=mysql_fetch_assoc($q))
					        $output[]=$e;
		print(json_encode($output));
	}

} elseif (strcmp($type, 'directions') == 0){
	$q = mysql_query("select order_number, text from instructions
		where recipe_id = " . $safe_recipeid . " order by order_number asc");
	if (!$q){
		print("get directions failed");
	} else {
		while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
		print(json_encode($output));
	}
} elseif (strcmp($type, 'keywords') == 0){
	$q = mysql_query("select phrase from instructions where recipe_id = " . $safe_recipeid);
	if (!$q){
		print("get keywords failed");
	} else {
		while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
		print(json_encode($output));
	}
} elseif (strcmp($type, 'notes') == 0){
	$q = mysql_query("select text from notes where recipe_id = " . $safe_recipeid);
	if (!$q){
		print("get notes failed");
	} else {
		while($e=mysql_fetch_assoc($q))
	        $output[]=$e;
		print(json_encode($output));
	}
}
mysql_close();
?>
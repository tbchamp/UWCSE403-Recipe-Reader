<?php
mysql_connect("cubist.cs.washington.edu","zaphans","uvwSL97k");
mysql_select_db("zaphans_recipe_reader");

$type = $_REQUEST['type'];

if (strcmp($type, 'standard') == 0){
	$terms = $_REQUEST['terms'];
	$term_array = explode("_", $terms);
	$num_terms = sizeof($term_array);
	$query_text = "select recipe_id, count(phrase) from keywords where ";
	for ($i = 0; $i < $num_terms; $i++){
		$query_text = $query_text . "phrase = " . $term_array[$i];
		if ($i != ($num_terms - 1)) {
			$query_text = $query_text . " or ";
		}
	}
	$query_text = $query_text . " group by recipe_id order by count(phrase)";
	print ($query_text);
}

mysql_close();
?>
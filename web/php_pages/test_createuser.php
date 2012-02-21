<?php
require_once('simpletest/autorun.php');
require_once('simpletest/web_tester.php');

class TestOfLogging extends WebTestCase {
	function testCreateUserSuccessCase() {
		$this->get('http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/createuser.php?username=' . genRandomString() . '&password=' . genRandomString());
		$this->assertNoText('Create User Failed!');
	}

	function testCreateDuplicateUserNameFail() {
		$this->get('http://cubist.cs.washington.edu/projects/12wi/cse403/r/php/createuser.php?username=test&password=temp');
		$this->assertText('Create User Failed!');
	}
}

function genRandomString() {
    $length = 10;
    $characters = '0123456789abcdefghijklmnopqrstuvwxyz';
    $string = "";
    for ($p = 0; $p < $length; $p++) {
        $string .= $characters[mt_rand(0, strlen($characters))];
    }
    return $string;
}
?>
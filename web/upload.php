<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<title>Recipe Reader</title>
<link href='http://fonts.googleapis.com/css?family=The+Girl+Next+Door|Gloria+Hallelujah|Delius' rel='stylesheet' type='text/css'>
<link href="recipe_reader.css" rel="stylesheet" type="text/css">

<!-- google analytics -->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-29062294-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
<!-- google analytics -->

<!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
</head>

<body>

<div class="container">
  <header>
    <a href=""><img src="images/Cooking_Android.png" alt="Logo" id="logo"></a>
    <ul>
        <li><a href="help.html">Help</a></li>
        <li><a href="upload.php">Upload Recipe</a></li>
        <li><a href="install.html">Install</a></li>
        <li><a href="index.html">Home</a></li>
    </ul>
    <h1>Recipe Reader</h1>
    <h2>An Android cooking assistant to find and read recipies to you for hands-free cooking!</h2>
  </header>
  <div class="sidebar1">
    <nav>
      <ul>
        <li><a href="index.html">Home</a></li>
        <li><a href="install.html">Install</a></li>
        <li><a href="upload.php">Upload Recipe</a></li>
        <li><a href="help.html">Help</a></li>
      </ul>
    </nav>
    <aside>
      <p> This app was developed as a class project for UW CSE 403. </p>
      <p> By Tyler, Zach, Anton, Jon, Kristin, and Alisa </p>
    </aside>
  <!-- end .sidebar1 --></div>
  <article class="content">
    <h1>Upload Recipes:</h1>
    <section>
     <h2>Fill out the form below:</h2>
     
     <form action="http://cubist.cs.washington.edu/projects/12wi/cse403/r/cgi/upload.py" method="get" >
        <p>
          Title:
          <input type="text" size="30" name="title" />
          Image URL:
          <input type="text" size="25" name="image_url" />
        </p>
        <p>
          Description:
          <input type="text" size="65" name="description" />
        </p>
        <p>
          Meal:
          <select name="meal">
            <option value="Other">Other</option>
            <option value="Breakfast">Breakfast</option>
            <option value="Lunch">Lunch</option>
            <option value="Dinner">Dinner</option>
            <option value="Snack">Snack</option>
            <option value="Supper">Supper</option>
            <option value="Dessert">Dessert</option>
          </select>
          Category:
          <select name="category">
            <option value="Other">Other</option>
            <option value="Vegetarian">Vegetarian</option>
            <option value="Dairy">Dairy</option>
            <option value="Gluten Free">Gluten Free</option>
            <option value="Vegan">Vegan</option>
            <option value="Beverage">Beverage</option>
            <option value="Entree">Entree</option>
            <option value="Side Dish">Side Dish</option>
          </select>
          Keywords:
          <input type="text" size="19" name="keyword" value="allrecipes"/>
        </p>
        <p>
          Prep time:
          <input type="text" size="10" name="prep_time" />
          Cook time:
          <input type="text" size="10" name="cook_time" />
          Ready time:
          <input type="text" size="10" name="ready_time" />
        </p>
        <p>
          Yield:
          <input type="text" size="7" name="yield" />
          Calories:
          <input type="text" size="7" name="cal" />
          Fat:
          <input type="text" size="7" name="fat" />
          Cholesterol:
          <input type="text" size="7" name="cholest" />
        </p>
        <p>
          Ingredients: (one per line)
        </p>
        <p>
          <textarea rows="5" cols="70" name="ingredients"></textarea>
        </p>
        <p>
          Directions: (one per line, don't number)
        </p>
        <p>
          <textarea rows="5" cols="70" name="directions"></textarea>
        </p>
        <p>
          
          <input type="submit" value="Add Recipe" />
        </p>
      </form>
     
    </section>
    <br />
    <section>
      <h2>Import from allrecipes.com:</h2>
      <p> Enter the URL to an allrecipes.com recipe into the box below: </p>
      
      <form action="http://cubist.cs.washington.edu/projects/12wi/cse403/r/cgi/allrecipes_scraper.py" method="get" >
        <p>
          Meal:
          <select name="meal">
            <option value="Other">Other</option>
            <option value="Breakfast">Breakfast</option>
            <option value="Lunch">Lunch</option>
            <option value="Dinner">Dinner</option>
            <option value="Snack">Snack</option>
            <option value="Supper">Supper</option>
            <option value="Dessert">Dessert</option>
          </select>
          Category:
          <select name="category">
            <option value="Other">Other</option>
            <option value="Vegetarian">Vegetarian</option>
            <option value="Dairy">Dairy</option>
            <option value="Gluten Free">Gluten Free</option>
            <option value="Vegan">Vegan</option>
            <option value="Beverage">Beverage</option>
            <option value="Entree">Entree</option>
            <option value="Side Dish">Side Dish</option>
          </select>
          Keywords:
          <input type="text" size="19" name="keyword" value="allrecipes"/>
        </p>
        <p>
          URL:
          <input type="text" size="60" name="input" />
          <input type="submit" value="fetch recipe" />
        </p>
      </form>
      <br />
      <p> Example: <a href="http://allrecipes.com/recipe/apple-pie-by-grandma-ople/detail.aspx">http://allrecipes.com/recipe/apple-pie-by-grandma-ople/detail.aspx</a> </p>
      
    </section>
  <!-- end .content --></article>
  
  <footer>
    <img src="images/HuskySoft.png" alt="HuskySoft" id="HuskySoft" height="50">
    Recipe Reader by HuskySoft a CSE 403 winter quarter production.
  </footer>
  <!-- end .container --></div>
</body>
</html>

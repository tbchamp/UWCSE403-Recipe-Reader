import sys
if (len(sys.argv) != 2):
	sys.exit()
import urllib2
import httplib
import re
from urlparse import urlparse
from BeautifulSoup import BeautifulSoup

file = open('output.txt', 'w')

#make sure arg 1 is valid URL
def exists(site, path):
	conn = httplib.HTTPConnection(site)
	conn.request('HEAD', path)
	response = conn.getresponse()
	conn.close()
	return response.status == 200

url = urlparse(sys.argv[1])
if not exists(url.netloc, url.path):
	print "URL is invalid"
	sys.exit()

#beautiful soup the page
page = urllib2.urlopen(sys.argv[1])
soup = BeautifulSoup(page)

#pull out the parts of the page we want:
title = soup.find('span', attrs={"class":"itemreviewed"})
picture = soup.find('img', attrs={"class":"rec-image photo"})

temp = soup.findAll('div', attrs={"class":"author-info rounded-box"})
for t in temp:
	temp2 = t.findAll('div', attrs={"class":"plaincharacterwrap", "style":"clear:right;"})
	for i in temp2:
		description = i

prep_time = soup.findAll('span', attrs={"class":"prepTime"})
if prep_time:
	prep_time = prep_time[0].nextSibling.nextSibling
cook_time = soup.findAll('span', attrs={"class":"cookTime"})
if cook_time:
	cook_time = cook_time[0].nextSibling.nextSibling
ready_time = soup.findAll('span', attrs={"class":"totalTime"})
if ready_time:
	ready_time = ready_time[0].nextSibling.nextSibling
yield_info = soup.find('span', attrs={"class":"yield yieldform"})
ingredients = soup.findAll('li', attrs={"class":"plaincharacterwrap ingredient"})
directions = soup.findAll('span', attrs={"class":"plaincharacterwrap break"})
notes = soup.find('div', attrs={"id":"ctl00_CenterColumnPlaceHolder_recipe_rptNotes_ctl01_noteContainer"})
calories = soup.find('span', attrs={"class":"calories"})
fat = soup.find('span', attrs={"class":"fat"})
cholesterol = soup.find('span', attrs={"class":"cholesterol"})

#clean up html markup / extract text or link
title = title.text
picture = picture['src']
description = description.text
prep_time = prep_time.text
cook_time = cook_time.text
ready_time = ready_time.text
yield_info = yield_info.text

temp = ingredients
ingredients = []
for ingredient in temp:
	ingredients.append( ingredient.text ) #re.escape(ingredient.text).replace("\\", "") )

temp = directions
directions = []
for direction in temp:
	directions.append(direction.text)

temp = notes.contents
notes = ""
for note in temp:
	notes += ' '.join(note.string.split())

calories = calories.text
fat = fat.text
cholesterol = cholesterol.text











#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
print "title"
print title
print "picture"
print picture
print "description"
print description
print "prep_time"
print prep_time
print "cook_time"
print cook_time
print "ready_time"
print ready_time
print "yield_info"
print yield_info
print "ingredients"
print ingredients
print "directions"
print directions
print "notes"
print notes
print "calories"
print calories
print "fat"
print fat
print "cholesterol"
print cholesterol
#~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

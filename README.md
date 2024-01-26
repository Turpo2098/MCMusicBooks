Welcome to my MCMusicBook Plugin for Spigot. It's an easy way to write
music from Note Blocks into only one book and play it.

It's my first try for a Domain Specific Language with an Lazy Parser(
so it only Evaluates when it has to).

Tutorial:
1. Get a Book and Quill. This is your main access point for this plugin.
2. Now write your Script e.g.:
~~~
hrp f
wait 300
gui #G
~~~
4. Have the book in your main hand and execute the command /music
5. Enjoy


WARNING: \
If there is something it is unable to parse the Program stops. There are
also NO Comments

Explanation:
1. It is possible to play a Sound with "instrument note" for example:
~~~
hrp f   //Plays with a Harp f
gui #G  //Plays with a Guitar #G
~~~     
A List with all possibles instruments is down below.
           

2. You can wait between two different Sounds with "wait"
~~~
wait 314 //Waits 314 Milliseconds
~~~
3. The Uppercase Letters for Notes are one octave Higher than the lower ones:
~~~
hrp g
wait 400
hrp G   //one ocatve higher
~~~

5. If you want to play more than one note simultaneously you can specify
     more than one Note with "< Notes >" :
~~~
<hrp g hrp f> //Plays g and f on a harp
~~~

7. You can avoid writing something multiple times with the "def" keyword:
~~~
def w wait 500
hrp g
w	//Waits 500 Millis
hrp g
~~~  
  

~~~
Grammar:
start 		::= instruction*
instruction 	::= instrument sharp? note
		  | '<' sound* '>'
		  | 'wait' digit
		  | 'def' identifier instruction
		  | identifier
identifier	::= [a-zA-Z]+
sound		::= instrument sharp? note
~~~
~~~
instrument: 
  bass guitar		bsg
  bass drum		bsd
  snare 		sna
  hat			hat
  bell			bel
  flute			flu
  chime			chi
  guitar		gui
  xylophone		xyl
  iron_xylophone 	ixy
  cow_bell		cbl
  didgeridoo		ddg
  bit			bit
  banjo			bnj
  pling			plg
  harp			hrp

note:
 a b c d e f g A B C D E F G

Sharp:
 Úse a # before the Note
~~~

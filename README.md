Java 7 and Visual C++ 2010 runtime required. JSFML.jar is in lib
<a href="http://i.imgur.com/MUMPfOs.png"><img src="http://i.imgur.com/MUMPfOs.png" title="Hosted by imgur.com" /></a>
Posgima-1 is a turn based RPG written in Java, using JSFML. Graphics are represented as Unicode characters. This is an ongoing exercise for my personal learning, a platform for devising and testing concepts related to programming and making games in general.

This project utilizes JSFML as a library for graphics and event handing.<br>
Worlds are randomly generated, and cannot be saved or loaded at a later time yet.<br>


Objective:<br>
<h1>The following information is outdated</h1><br>
<h3>I will rewrite at a later date</h3><br>
You are stranded alone in an unknown land. Your goal is to survive and find the buried artifact.<br>

Health starts at 100, if below 0 you will die. It goes down when hunger is at it's maximum.<br>
Hunger increases with movement and activities.<br>
Food is your inventory, it is used to replenish your hunger.<br>

Digging<br>
  -D key digs up "food", and if the artifact is buried, digs that up to win.<br>
Mining<br>
  -M key will remove a mountain in the desired direction<br>
Building<br>
  -H key builds a house<br>
  -R key builds a road, walking on roads does not use hunger<br>
Survival<br>
  -E key eats food, replenishes hunger<br>
  -S key sleeps in a house to replenish health<br>
  
Debugging keys:<br>
  -- will decrease the next map's size<br>
  -= will increase the next map's size<br>
  -P will generate a new map and game<br>
  -K will enable keyRepeat<br>
  

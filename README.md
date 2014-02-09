Java 7 and Visual C++ 2010 runtime required. JSFML.jar is in lib

Posgima 1 is a "one page game", written in Java to explore and improve my programming skills. This version is basically final, with only refactoring and possibly improving existing features. I purposely kept it simple to just have something finished, as I had always tried to make a game, but was hindered by lack of skills and poor planning. In the future I hope to leverage this project into Posgima 2, which will improve upon the design.

This project utilizes JSFML as a library for graphics and event handing.
Worlds are randomly generated, and cannot be saved or loaded at a later time.


Objective:
You are stranded alone in an unknown land. Your goal is to survive and find the buried artifact.

Health starts at 100, if below 0 you will die. It goes down when hunger is at it's maximum.
Hunger increases with movement and activities.
Food is your inventory, it is used to replenish your hunger.

Digging
  -D key digs up "food", and if the artifact is buried, digs that up to win.
Mining
  -M key will remove a mountain in the desired direction
Building
  -H key builds a house
  -R key builds a road, walking on roads does not use hunger
Survival
  -E key eats food, replenishes hunger
  -S key sleeps in a house to replenish health
  
Debugging keys:
  -- will decrease the next map's size
  -= will increase the next map's size
  -P will generate a new map and game
  -K will enable keyRepeat
  

### Overview 
Ethnocentrism is a model introduced by Robert Axelrod and Ross A. Hammond. Ethnocentric behaviours indicate cooperation with members of the same group and no cooperation with members of different groups. The model suggests that ethnocentric behaviours can emerge from interactions between individuals and evolve under a wide variety of conditions. It treats interactions of agents as one-move Prisoner’s Dilemmas, in which everyone chooses whether or not to lose some potential of reproducing to help the other. It also considers the inheritance of strategies as genetic, cultural, or both. 

Each agent in the model has three traits: (a) colour, (b) whether it cooperates with agents of the same colour, (c) whether it cooperates with agents of different colour [1]. Specifically: For agents cooperating with the same color, they'll appear as circles (C). Among all “C”s, if they cooperate with both same-color and different-color agents (altruists), they'll be filled (CC); if they only cooperate with same-color agents (ethnocentrics), they remain unfilled, empty. For agents not cooperating with the same color, they'll appear as squares (D). Among all “D”s, if they cooperate only with different-color agents (cosmopolitans), they'll be filled (DC); if they don't cooperate with any color(egoists), they remain unfilled, empty (DD).
### Design
## Existing model
# States
There are five possible states: immigrate, offspring, interact, reproduce, and death.
- Immigrate: New agents can immigrate at random locations with random traits.
- Offspring: New agents can be reproduced by existing agents. 
- Interact: Adjacent agents can interact with each other, and decide whether or not to help each other.
- Reproduce: Each agent has a certain chance to reproduce offspring.
- Death: Each agent has a certain chance of death.
# Update rules
- Immigrate: When there are empty patches available and the number of current immigrants is smaller than the parameter immigrants-per-day, new agents can immigrate. Their traits depend on the immigrant-chance-cooperate-with-same and immigrant-chance-cooperate-with-different.
- Offspring: The parent agent reproduces a new agent. Offspring have the same traits as their parents, with mutation-rate for each trait mutating.
- Interact: When there are neighbours, the agent will interact with adjacent agents. Adjacent agents may cooperate with each other depending on their traits. If they cooperate, they will either lose or gain potential to reproduce with parameters cost-of-giving and gain-of-receiving.
- Reproduce: When there are empty patches around the agent, the agent has certain potential to reproduce, whose initial value is initial-PTR.
- Death: The agent has the probability of death-rate to die.
## Our Model
![04 A2 Design](https://github.com/Baye0110/Ethnocentrism-Model/assets/90529080/15d2220b-cf28-46ae-90d2-3c9729b25c7c)

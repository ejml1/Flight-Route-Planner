# Flight Route Planner

This is a simple flight route planner that implements depth-first search (DFS), breadth-first search (BFS), best-first search (BestF), and the A* algorithms to navigate a 2D polar coordinate system. The coordinates are in the form of a tuple 
(d:angle) where d s the distance from the pole and angle is the direction, which is one of {0, 45, 90, 135, 180, 225, 270, 315}

Aircrafts start at S=(dS:angleS) and G=(dG:angleG) (see Figure 1)

<div style="text-align: center;">
  <img width="483" alt="image" src="https://github.com/ejml1/Flight-Route-Planner/assets/98708105/db1cf0db-45b2-416b-8a4b-e1eebd39b3e1">
  <p style="font-style: italic;">Figure 1: Example of Flight Path</p>
</div>

Aircrafts can only move one point along the grid points

Coordinates at the pole such as (0:0), (0:45), (0:90), etc... are valid coordinates but aircrafts cannot reach or fly over them

Aircrafts cannot go beyond the last parallel

# Compiling & Running Instructions

Steps
1. cd into the src & src/main directory and compile all the files with the command:
   ```bash
   javac *.java
   ```
3. In the src directory, run:
   ```bash
   java P3main <DFS|BFS|AStar|BestF|SMAStar> <N> <dS:angleS> <dG:angleG>
   ```
   
Evaluation Exec:

```bash
./exec_prog.sh <DFS|BFS|AStar|BestF|SMAStar>
```
can be run to perform a search on the evaluation cases. Output is sent to the corresponding text file: <DFS|BFS|AStar|BestF|SMAStar>.txt

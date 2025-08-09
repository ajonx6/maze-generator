# Maze Generation 

### Features
- Currently has both generation and search algorithms
  - Generation algorithms
    - Iterative DFS
    - Prims
    - Kruskal
  - Search algorithms
    - BFS
    - Iterative DFS
    - Dijkstra
    - A*
- Each algorithm can be animated to show how it is working, with colours representing different cell states
- There's a settings panel with 2 tabs for each algorithm type
  - Algorithm type
  - Styling panel to change colors of states
  - Width/height of maze (generator only)
  - Delay between draws (0 is instant, then delay between each update)
  - Seed (generator only)
  - Start/stop/restart algorithm
- You can pan and zoom the maze with buttons to reset these transforms
- Uses https://www.formdev.com/flatlaf/ look and feel


### To-do
- Generation
  - Eller's algorithm
  - Recursive division
  - Hunt-and-kill
  - Aldous-Broder
  - Wilson's algorithm
- Search
  - Bidirectional A*
  - Jump point search
  - Best first search
  - Wall follower
  - Dead-end filling
- UI
  - Control delay dynamically while running
  - Show current best path
- Maze
  - Set start/end positions
  - Add walls or other obstacles with costs
  - Distance heatmap
- Data analysis
  - Show step count and visited cell count
  - Export maze as image or JSON
  - Maybe link together 2 instances of program to generate same maze and run different algorithms on them

### Screenshots
##### DFS in progress
![DFS in progress](/screenshots/dfs_in_progress.png)
###
##### Kruskal complete
![Kruskal complete](/screenshots/kruskal_done.png)
###
##### Styling for search algorithm
![Styling for search algorithm](/screenshots/show_styling.png)
###
##### A* in progress
![A* in progress](/screenshots/search_in_progress.png)
###
##### Zoom and pan
![Zoom and pan](/screenshots/zoom_pan.png)
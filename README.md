# SIIZR Simulation - Zombie Apocalypse Model

## Project Overview

The SIIZR Simulation is a computational model of a zombie apocalypse, developed as the final project for Computational Thinking II. The model simulates interactions between four population types—Susceptible (S), Infected (I), Intelligent Zombie (IZ), and Removed (R)—on a 2D grid. Entities can move, form groups, fight, scavenge, infect, or mutate, with every outcome determined probabilistically based on health. This project includes both a Python implementation for detailed simulation and visualization and a Greenfoot implementation for a game-like experience.

## Team Members
- Tolu Adeola
- Maloney Tutu
- Earl Pough
- Ralph Reyes

## Features

- **Entity Modeling**: Each entity (Susceptible, Infected, Intelligent Zombie, Removed) has attributes like health, (x, y) position, and grouping, with unique behaviors (e.g., scavenging, infecting, luring).
- **Dynamic Interactions**: Entities move randomly, form groups, fight, or infect other populations based on proximity and health-based probabilities.
- **Visualizations (Python)**: Matplotlib generates scatter plots of entity locations (every 5 hours) and line graphs of population trends over time.
- **Greenfoot Game**: An interactive version where users can observe entities moving and interacting in real time, with simple visual representations.
- **Randomized Mechanics**: Movement, damage, scavenging, and mutation (infected to intelligent zombie) are randomized for variability.

## Requirements

### To run the Python simulation:
- Python 3.8 or higher
- Required libraries:
  - matplotlib
  - random

### To run the Greenfoot implementation:
- Greenfoot installed (version 3.7 or higher)
- Java Development Kit (JDK) 8 or higher

## Installation

### Clone the Repository:
```
git clone https://github.com/RRR-Reyes/CT-2-Final.git
cd CT-2-Final
```

### Python Setup:
Install required libraries using pip:
```
pip install matplotlib
```

### Greenfoot Setup:
1. Download and install Greenfoot from [greenfoot.org](https://www.greenfoot.org)
2. Open the Greenfoot project in the Greenfoot IDE

## Usage

### Python Simulation
1. Navigate to the project folder `Python - Zombieola`, containing `Zombieola.py`
2. Simulate with default parameters (45 Susceptibles, 5 Infected, 0 Intelligent Zombies, 0 Removed, grid size 1-10):
   ```
   python Zombieola.py
   ```
3. The simulation runs until no Susceptibles or no Infected/Intelligent Zombies remain, displaying:
   - Population trend graphs every 5 hours
   - Final statistics (hours, days, final population counts)
4. To modify parameters, edit the Zombieola call in `Zombieola.py`, e.g.:
   ```python
   Zombieola(50, 10, 2, 0, 1, 15)  # 50 Susceptibles, 10 Infected, 2 Intelligent Zombie, 0 Removed, 1-15 Grid
   ```

### Greenfoot Game
1. Open the Greenfoot project in the Greenfoot IDE
2. Compile and run the scenario
3. Watch entities moving and interacting on the grid
4. Use Greenfoot's controls to pause, resume, or adjust the simulation speed
5. Check for on-screen text for population counts and events

## Code Structure

### Python Implementation:
- `Person`: Base class for all entities
- `Susceptible`, `Infected`, `IntelligentZombie`, `Removed`: Subclasses with specific behaviors
- `Zombieola`: Main simulation loop managing entity interactions and visualizations
- `plot_locations`, `plot_graph`: Functions for Matplotlib graphing

### Greenfoot Implementation:
- `ZombieWorld`: World-class managing the grid and entity populations
- `Susceptible`, `Infected`, `IntelligentZombie`, `Removed`: Actor classes for each type
- `Assets`: Images for visualizing entities

## How It Works

The simulation models a zombie apocalypse on a 10x10 grid (Changeable):

- **Susceptibles** move (-5 to 5 steps), scavenge for health, and fight with weapons (10-20 damage)
- **Infected** move (-1 to 1 steps), attack Susceptibles, and may infect them or mutate (2% chance) into Intelligent Zombies
- **Intelligent Zombies** move (-2 to 2 steps), use stronger weapons (20-30 damage), and can lure susceptible group members
- **Removed** represent dead entities, fixed at their final position

Entities form groups when sharing locations, impacting fight outcomes (e.g., larger groups resist luring). The Python version tracks populations and visualizes them, while the Greenfoot version provides a real-time, interactive view.

## Example Output

### Python
Running `Zombieola(45, 5, 0, 0, 1, 10)` might produce:
```
Total Hours: 72
Total Days: 3
Final Counts: S=0 | I=12 | IZ=3 | R=35
```
With graphs showing population trends and entity locations.

### Greenfoot
Entities appear as different characters on a grid, moving and interacting in real time.

## Future Improvements
- Add configurable parameters (e.g., mutation rate, grid size)
- Enhance Greenfoot with user controls (e.g., pause, adjust parameters)
- Add detailed event logging for analysis

## License
This project is licensed under the MIT License. See the LICENSE file for details.

## Acknowledgments
Developed for Computational Thinking II. Special thanks to our instructor, Dr. Smolinski, and classmates for feedback and support.

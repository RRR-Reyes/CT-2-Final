import random
import matplotlib.pyplot as plt

class Person:
    # ABSTRACT
    # Att: Name(ID), Age?(ranges 18-65; Older = Lower Health?), Health(100), Group(Counts members?), Status(Alive or Dead)?, pMin, pMax
    # Virtual Methods: isAlive?, Movement, attack
    # Methods: FormGroup, TakeDamage, AssessSituation(?)
    def __init__(self, pID, pMin, pMax):
        self.ID = pID
        self.Age = random.randint(18, 65)
        self.Health = 100 - self.Age #Updated
        self.Group = {self.ID}
        self.Status = True
        self.x = random.randint(pMin, pMax)
        self.y = random.randint(pMin, pMax)
        self.pMin = pMin
        self.pMax = pMax

    def move(self):
        pass

    def attack(self, target):
        pass

    def formGroup(self, other):
        combined = self.Group.union(other.Group)
        self.Group = combined
        other.Group = combined

    def takeDamage(self, dmg):
        self.Health -= dmg
        if self.Health <= 0:
            self.Status = False

    def isAlive(self):
        return self.Status

    def shareLocation(self, other):
        return self.x == other.x and self.y == other.y

    def checkGroupSize(self):
        return len(self.Group)

class Susceptible(Person):
    # Att: Weapon(increases damage, ranges (0-20) damage on health)
    # Override Methods: Movement(Widest Range - (-5,5)), Attack(also known as Hunt, Widest Range of Damage(0-50), increase with weapon)
    # Unique Methods: Scavenge(Increase Health(0-10)), RunAway(replaces Hide—adjusts X and Y position like Movement)
    def __init__(self, pID, pMin, pMax):
        super().__init__(pID, pMin, pMax)
        self.Weapon = random.randint(10, 20) 

    def move(self):
        # Susceptible moves randomly within widest range
        xstep = random.randint(-5, 5)
        ystep = random.randint(-5, 5)
        newx = self.x + xstep
        newy = self.y + ystep
        if self.pMin < newx < self.pMax:
            self.x = newx
        if self.pMin < newy < self.pMax:
            self.y = newy

    def attack(self, target):
        #  s.Fight(i or iz) - Susceptible fights with weapon-enhanced damage
        base_dmg = random.randint(10, 30) # Updated Smaller Base
        total_dmg = base_dmg + self.Weapon
        target.takeDamage(total_dmg)

    def scavenge(self):
        #  Susceptible gains or loses health by scavenging
        self.Health += random.randint(-5, 5)
        if self.Health > 99:
            self.Health = 99
        elif self.Health < 5: #Updated
            self.Health = 5

    def runAway(self):
        #  Susceptible attempts to escape by moving, loses health by force
        self.move()
        self.Health -= random.randint(5,10) #Updated Health

class Infected(Person):
    def __init__(self, pID, pMin, pMax):
        super().__init__(pID, pMin, pMax)
        self.mutated = False

    def move(self):
        # Infected moves randomly within a smaller range
        xstep = random.randint(-1, 1)
        ystep = random.randint(-1, 1)
        newx = self.x + xstep
        newy = self.y + ystep
        if self.pMin <= newx <= self.pMax:
            self.x = newx
        if self.pMin <= newy <= self.pMax:
            self.y = newy

    def attack(self, target):
        # Infected attacks susceptible
        base_dmg = random.randint(20, 40) # Updated Bigger base for Infected
        target.takeDamage(base_dmg)

    def infect(self, target):
        # Infected turns susceptible into infected, adds health
        target.Status = False
        self.Health += random.randint(5,20) #Updated

    def mutate(self):
        # Infected may mutate into IntelligentZombie
        chance = random.randint(0, 100)
        if chance > 98: # Updated 2% Chance
            self.mutated = True
        return self.mutated

class IntelligentZombie(Infected):
    def __init__(self, pID, pMin, pMax):
        super().__init__(pID, pMin, pMax)
        self.Weapon = random.randint(20, 30) #Update Bigger Damage Weapon
        self.mutated = True

    def move(self):
        # IntelligentZombie moves randomly within a bigger range than infected, but smaller than susceptible
        xstep = random.randint(-2, 2)
        ystep = random.randint(-2, 2)
        newx = self.x + xstep
        newy = self.y + ystep
        if self.pMin <= newx <= self.pMax:
            self.x = newx
        if self.pMin <= newy <= self.pMax:
            self.y = newy

    def attack(self, target):
        # IntelligentZombie attacks with weapon-based damage
        base_damage = random.randint(30, 50) #Updated Biggest Damage because of Hunger
        total_damage = base_damage + self.Weapon
        target.takeDamage(total_damage)

    def lure(self, group_sus, current_sus):
        for s in group_sus:
            
            #verify s is a Susceptible object
            if not isinstance(s, Susceptible):
                continue
                
            # Now check every member based on IZ's health
            if s.ID in current_sus.Group and s.ID != current_sus.ID:
                if random.randint(1, 100) <= self.Health:
                    s.x = self.x
                    s.y = self.y

class Removed(Person):
    def __init__(self, pID, pMin, pMax, x, y):
        super().__init__(pID, pMin, pMax)
        self.Status = False
        self.Health = 0
        self.x = x
        self.y = y
        self.location = (self.x, self.y)

def outcome(p):
    # Determines probabilistic outcomes based on health
    pick = random.randint(1, 100)
    return pick < p #Updated

def plot_locations(hour, active_sus, active_inf, active_inz, active_rmd):
    plt.figure(figsize=(10, 10))
    
    #Actual Plotting Points
    for r in active_rmd:
        plt.scatter(r.x, r.y, c='orange', s=160)    
    for s in active_sus:
        plt.scatter(s.x, s.y, c='blue', s=100)
    for i in active_inf:
        plt.scatter(i.x, i.y, c='red',s=80)
    for z in active_inz:
        plt.scatter(z.x, z.y, c='lime', s=60)
    
    #Just to Create Legend
    plt.scatter(0, 0, c='orange', s=160, label='Removed') 
    plt.scatter(0, 0, c='blue', s=100, label='Susceptible') 
    plt.scatter(0, 0, c='red', s=80, label='Infected') 
    plt.scatter(0, 0, c='lime', s=60, label='Intelligent Zombie')
    
    plt.title("Zombie Apocoplayse Simulation - Population Location - Hour " + str(hour))
    plt.xlabel("X Coordinate")
    plt.ylabel("Y Coordinate")
    plt.grid(True)
    plt.legend(loc='upper right')
    plt.xlim(0, 11)
    plt.ylim(0, 11)
    plt.show()
    plt.close
    
def plot_graph(sus_counts, inf_counts, inz_counts, rmd_counts):
    
    hour = range(len(sus_counts))
    plt.figure(figsize=(10, 10))
    plt.plot(hour, sus_counts, c='blue', label='Susceptible')
    plt.plot(hour, inf_counts, c='red', label='Infected')
    plt.plot(hour, inz_counts, c='lime', label='Intelligent Zombie')
    plt.plot(hour, rmd_counts, c='orange', label='Removed')
    plt.title("Zombie Apocalypse Simulation - Population Over Time")
    plt.xlabel("Hour")
    plt.ylabel("Population Size")
    plt.grid(True)
    plt.legend(loc='upper right')
    plt.show()
    plt.close()

def Zombieola(pS, pI, pIZ, pR, pMin, pMax):
    # Procedure apocalypse(S, I, IZ, R)

    # Initialize S, I, IZ, R objects
    active_sus = [Susceptible(s + 1, pMin, pMax) for s in range(pS)]
    active_inf = [Infected((i + 1)*10, pMin, pMax) for i in range(pI)]
    active_inz = [IntelligentZombie((z + 1)*100, pMin, pMax) for z in range(pIZ)]
    active_rmd = [Removed((r + 1)*1000, pMin, pMax, 0, 0) for r in range(pR)]

    # Track populations for graphing/visuals
    sus_counts = [len(active_sus)]
    inf_counts = [len(active_inf)]
    inz_counts = [len(active_inz)]
    rmd_counts = [len(active_rmd)]

    hour_count = 0  # Similar to generation count
    keep_going = True

    # While (S > 0)
    while keep_going:
        hour_count += 1

        # Everyone moves first
        # Susceptible movement
        for s in active_sus:
            s.move()
        # Infected movement
        for i in active_inf:
            i.move()
        # IntelligentZombie movement
        for z in active_inz:
            z.move()

        # Temporary lists for population changes
        new_infected = []
        new_intelligent = []
        new_removed = []

        # Process the Susceptibles
        for s in active_sus:
            # If s encounters s, s.Group()
            for os in active_sus:
                if s != os and s.shareLocation(os):
                    s.formGroup(os)

            # If s encounters i
            for i in active_inf:
                if s.shareLocation(i):
                    if outcome(s.Health):  # Probabilistic fight decision based on health
                        s.attack(i)
                        if i.Health <= 0:  # If s wins, I-- or IZ--, R++
                            active_inf.remove(i)
                            new_removed.append(Removed(i.ID, pMin, pMax, i.x, i.y))
                        elif i.Health > s.Health:  # If s loses, S--, I++
                            i.infect(s)
                            active_sus.remove(s)
                            new_infected.append(Infected(s.ID, pMin, pMax))
                            continue
                    else:
                        # s.Hide() -> s.RunAway()
                        s.runAway()

            # If s encounters iz
            for z in active_inz:
                if s.shareLocation(z):
                    if outcome(s.Health):  # Probabilistic fight decision
                        # s.Fight(iz)
                        s.attack(z)
                        if z.Health <= 0:  # If s wins, IZ--, R++
                            active_inz.remove(z)
                            new_removed.append(Removed(z.ID, pMin, pMax, z.x, z.y))
                        elif z.Health > s.Health:  # If s loses, S--, I++
                            z.infect(s)
                            active_sus.remove(s)
                            new_infected.append(Infected(s.ID, pMin, pMax))
                            continue
                    else:
                        # s.Hide() -> s.RunAway()
                        s.runAway()

            # s.Scavenge()  Increase/Decreases Health
            s.scavenge()

        # Process Infected
        for i in active_inf:
            if not i.isAlive():
                #  If i is dead, I--, R++
                active_inf.remove(i)
                new_removed.append(Removed(i.ID, pMin, pMax, i.x, i.y))
                continue

            # Check for mutation
            if i.mutate():
                # Infected becomes IntelligentZombie
                active_inf.remove(i)
                new_intelligent.append(IntelligentZombie(i.ID, pMin, pMax))
                continue

            # If i encounters r, i.Walk() and Removed are ignored

            # Pseudocode: If i encounters s
            for s in active_sus:
                if i.shareLocation(s):
                    # i.Attack(s)
                    i.attack(s)
                    if s.Health <= 0:  # If i wins, S--, R++
                        active_sus.remove(s)
                        new_removed.append(Removed(s.ID, pMin, pMax, s.x, s.y))
                    elif s.Health < i.Health:  # i.Infect(s), S--, I++
                        i.infect(s)
                        active_sus.remove(s)
                        new_infected.append(Infected(s.ID, pMin, pMax))
                    else:
                        # s escapes
                        s.runAway()

            # If i encounters iz, i.Group()
            for oz in active_inz:
                if i.shareLocation(oz):
                    i.formGroup(oz)

            # Group with other infected
            for oi in active_inf:
                if i != oi and i.shareLocation(oi):
                    i.formGroup(oi)

        # Process Intelligent Zombies
        for z in active_inz:
            if not z.isAlive():
                # If iz is dead, IZ--, R++
                active_inz.remove(z)
                new_removed.append(Removed(z.ID, pMin, pMax, z.x, z.y))
                continue

            # If iz encounters i, iz.Group()
            for i in active_inf:
                if z.shareLocation(i):
                    z.formGroup(i)

            # If iz encounters s
            for s in active_sus:
                if z.shareLocation(s):
                    # If s is in a group
                    if s.checkGroupSize() > z.checkGroupSize():
                        # If s group is bigger than iz group
                        if outcome(z.Health):  # Probabilistic decision
                            z.lure(s.Group, s)
                            if outcome(s.Health):  # Probabilistic escape on health
                                s.runAway()
                            else:
                                z.attack(s)
                                if s.Health <= 0:  #  If iz wins, S--, R++
                                    active_sus.remove(s)
                                    new_removed.append(Removed(s.ID, pMin, pMax, s.x, s.y))
                                elif s.Health < z.Health:  #  S--, I++
                                    z.infect(s)
                                    active_sus.remove(s)
                                    new_infected.append(Infected(s.ID, pMin, pMax))
                        else:
                            # susceptible escapes
                            s.runAway()
                    else:
                        #  iz.Attack(s) -- no group size advantage
                        z.attack(s)
                        if s.Health <= 0:  #  If iz wins, S--, R++
                            active_sus.remove(s)
                            new_removed.append(Removed(s.ID, pMin, pMax, s.x, s.y))
                        elif s.Health < z.Health:  # S--, I++
                            z.infect(s)
                            active_sus.remove(s)
                            new_infected.append(Infected(s.ID, pMin, pMax))
                        else:
                            s.runAway()

            # Group with other intelligent zombies
            for oz in active_inz:
                if z != oz and z.shareLocation(oz):
                    z.formGroup(oz)

        # Update populations
        active_inf.extend(new_infected)
        active_inz.extend(new_intelligent)
        active_rmd.extend(new_removed)


        # Track populations for graphing
        sus_counts.append(len(active_sus))
        inf_counts.append(len(active_inf))
        inz_counts.append(len(active_inz))
        rmd_counts.append(len(active_rmd))
        
        
        #Every 5 hours
       # if hour_count % 5 == 0:
            #plot_locations(hour_count, active_sus, active_inf, active_inz, active_rmd)
            #plot_graph(sus_counts, inf_counts, inz_counts, rmd_counts)
           

        # Stop if empty list of susceptibles or infected and intelligent zombies
        if len(active_sus) == 0 or (len(active_inf) == 0 and len(active_inz) == 0):
            #plot_graph(sus_counts, inf_counts, inz_counts, rmd_counts)
            keep_going = False

    print("\nTotal Hours: " + str(hour_count))
    print("Total Days: " + str(int(hour_count / 24)))
    print("Final Counts: S=" + str(len(active_sus)) + " | I=" + str(len(active_inf)) + " | IZ=" + str(len(active_inz)) + " | R=" + str(len(active_rmd)))

Zombieola(45, 5, 0, 0, 1, 10)
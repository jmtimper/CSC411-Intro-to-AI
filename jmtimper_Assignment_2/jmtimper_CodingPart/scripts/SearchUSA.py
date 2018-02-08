import sys, cityReader, citynode, heapq, math

#A* algorithm
def astar(cities, startName, goalName):
    #get node object for start city
    for i in range(0, len(cities)):
        if startName == cities[i].name:
            start = cities[i]
            break

    #list of explored
    explored = []
    #heap of frontier
    frontier = []

    #push start city to frontier
    heapq.heappush(frontier, (0, start))

    while len(frontier) > 0:
        #pops best frontier
        n = heapq.heappop(frontier)

        #if current node is the goal
        if n[1].name == goalName:
            explored.append(n[1])
            path = []
            temp = n[1]
            path.append(temp.name)
            #loops through parents to return list
            while temp.name != start.name:
                temp = temp.parent
                path.append(temp.name)
            output(explored, path, n[1])
            return 0;
        #add node to explored
        explored.append(n[1])

        #rank each road off of node
        for road in n[1].roads:
            for item in cities:
                if road == item.name:
                    roadObj = item
                    break
            if roadObj.parent is None:
                roadObj.parent = n[1]
            roadObj.pathCost = n[1].roads[road] + roadObj.parent.pathCost
            priority = roadObj.pathCost + roadObj.distanceToGoal
            if roadObj not in frontier and roadObj not in explored:
                for value in frontier:
                    if value[0] == priority:
                        priority += 1
                heapq.heappush(frontier, (priority, roadObj))
            elif roadObj in frontier:
                for old in frontier:
                    if old[1].name == road[1].name and old[0] > priority:
                        old[0] = priority
                        old[1] = road[1]
    return -1

#Greedy Algorithm
def greedy(cities, startName, goalName):
    # get node object for start city
    for i in range(0, len(cities)):
        if startName == cities[i].name:
            start = cities[i]
            break

    # list of explored
    explored = []
    # heap of frontier
    frontier = []

    # push start city to frontier
    heapq.heappush(frontier, (0, start))

    while len(frontier) > 0:
        # pops best frontier
        n = heapq.heappop(frontier)
        # if current node is the goal
        if n[1].name == goalName:
            explored.append(n[1])
            path = []
            temp = n[1]
            path.append(temp.name)
            # loops through parents to return list
            while temp.name != start.name:
                temp = temp.parent
                path.append(temp.name)
            output(explored, path, n[1])
            return 0
        # add node to explored
        explored.append(n[1])

        # rank each road off of node
        for road in n[1].roads:
            for item in cities:
                if road == item.name:
                    roadObj = item
                    break
            if roadObj.parent is None:
                roadObj.parent = n[1]
            roadObj.pathCost = n[1].roads[road] + roadObj.parent.pathCost
            priority = roadObj.distanceToGoal
            if roadObj not in frontier and roadObj not in explored:
                for value in frontier:
                    if value[0] == priority:
                        priority += 1
                heapq.heappush(frontier, (priority, roadObj))
            elif roadObj in frontier:
                for old in frontier:
                    if old[1].name == road[1].name and old[0] > priority:
                        old[0] = priority
                        old[1] = road[1]
    return -1

#Dynamic Programming Algorithm
def dp(cities, startName, goalName):
    # get node object for start city
    for i in range(0, len(cities)):
        if startName == cities[i].name:
            start = cities[i]
            break

    # list of explored
    explored = []
    # heap of frontier
    frontier = []

    # push start city to frontier
    heapq.heappush(frontier, (0, start))

    while len(frontier) > 0:
        # pops best frontier
        n = heapq.heappop(frontier)
        # if current node is the goal
        if n[1].name == goalName:
            explored.append(n[1])
            path = []
            temp = n[1]
            path.append(temp.name)
            # loops through parents to return list
            while temp.name != start.name:
                temp = temp.parent
                path.append(temp.name)
            output(explored, path, n[1])
            return 0
        # add node to explored
        explored.append(n[1])

        # rank each road off of node
        for road in n[1].roads:
            for item in cities:
                if road == item.name:
                    roadObj = item
                    break
            if roadObj.parent is None:
                roadObj.parent = n[1]
            roadObj.pathCost = n[1].roads[road] + roadObj.parent.pathCost
            priority = roadObj.pathCost
            if roadObj not in frontier and roadObj not in explored:
                for value in frontier:
                    if value[0] == priority:
                        priority += 1
                heapq.heappush(frontier, (priority, roadObj))
            elif roadObj in frontier:
                for old in frontier:
                    if old[1].name == road[1].name and old[0] > roadObj.pathCost:
                        old[0] = roadObj.pathCost
                        old[1] = road[1]
    return -1

def output(explored, path, goal):
    exp = []
    for city in explored:
        exp.append(city.name)
    print("Explored Nodes: ")
    print(exp)
    print("Explored length: " + str(len(explored)))
    print("Solution Path:")
    print(path[::-1])
    print("Solution Length: " + str(len(path)))
    print("Solution Distance: " + str(goal.pathCost))

#runs program taking in command line arguments
def main():
    if len(sys.argv) != 4:
        sys.exit("Incorrect Number of command line arguments must be 3")
    if sys.argv[1] != "dp" and sys.argv[1] != "astar" and sys.argv[1] != "greedy":
        sys.exit("search type must be: astar, greedy, or dp")

    converstion = 69
    # gets cities
    cities = cityReader.readCities("uscities.pl")
    for i in range(0, len(cities)):
        if sys.argv[3] == cities[i].name:
            goal = cities[i]
            break
    for citynode in cities:
        citynode.distanceToGoal = math.hypot(goal.lat - citynode.lat, goal.long - citynode.long)*converstion

    # gets roads for each city
    for i in range(0, len(cities)):
        cities[i].getRoads("usroads.pl")

    if sys.argv[1] == "astar":
        print("\nUsing A* Search Algorithm...")
        astar(cities, sys.argv[2], sys.argv[3])
    elif sys.argv[1] == "greedy":
        print("\nUsing Greedy Best-First Search Algorithm...")
        greedy(cities, sys.argv[2], sys.argv[3])
    elif sys.argv[1] == "dp":
        print("\nUsing Dynamic Programming Search Algorithm...")
        dp(cities, sys.argv[2], sys.argv[3])

if __name__ == "__main__":
    main();
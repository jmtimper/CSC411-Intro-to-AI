class citynode:
    def __init__(self, name, lat, long):
        self.name = name
        self.lat = float(lat)
        self.long = float(long)
        self.parent = None
        self.action = 'MOVE'
        self.roads = {}
        self.distanceToGoal = 0;
        self.pathCost = 0

    def getRoads(self, filename):
        with open(filename) as f:
            allRoads = f.readlines()
            for i in range(0, len(allRoads)):
                line = allRoads[i].split(',')
                if line[0].strip() == self.name:
                    self.roads[line[1].strip()] = int(line[2].split('\n')[0].strip())
                if line[1].strip() == self.name:
                    self.roads[line[0].strip()] = int(line[2].split('\n')[0].strip())
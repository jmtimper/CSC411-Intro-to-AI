import os, citynode

def readCities(filename):
    os.chdir("../inputs")
    cities = []
    with open(filename) as f:
        lines = f.readlines()
        for i in range(13, len(lines)):
            line = lines[i].split('(')
            cityname = line[1].split(',')[0].strip()
            lat = line[1].split(',')[1].strip()
            long = line[1].split(',')[2].split(')')[0].strip()
            cities.append(citynode.citynode(cityname, lat, long))
    return cities
